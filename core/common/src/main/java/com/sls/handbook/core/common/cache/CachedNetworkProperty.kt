package com.sls.handbook.core.common.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Thread-safe, suspend-based cache with a fixed time-to-live.
 *
 * On each call to [get], returns the cached value if it was fetched within [ttlMillis]
 * milliseconds; otherwise invokes [fetcher] to retrieve a fresh value. Concurrent callers
 * are serialized with a [Mutex] to avoid redundant fetches.
 *
 * @param T the type of the cached value
 * @param ttlMillis cache lifetime in milliseconds
 * @param fetcher suspend function invoked to produce a fresh value on cache miss
 */
class CachedNetworkProperty<T>(
    private val ttlMillis: Long,
    private val fetcher: suspend () -> T,
) {

    @Volatile
    private var cachedValue: T? = null

    @Volatile
    private var lastFetchTime: Long = 0L

    private val mutex = Mutex()

    @Suppress("ReturnCount")
    suspend fun get(): T {
        val now = System.currentTimeMillis()
        cachedValue?.let { value ->
            if (now - lastFetchTime < ttlMillis) return value
        }
        return mutex.withLock {
            // Double-check after acquiring lock
            val nowInner = System.currentTimeMillis()
            cachedValue?.let { value ->
                if (nowInner - lastFetchTime < ttlMillis) return value
            }
            val fresh = fetcher()
            cachedValue = fresh
            lastFetchTime = nowInner
            fresh
        }
    }
}
