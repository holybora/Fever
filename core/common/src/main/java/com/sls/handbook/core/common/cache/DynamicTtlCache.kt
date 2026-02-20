package com.sls.handbook.core.common.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Thread-safe cache that accepts a dynamic TTL on each retrieval call.
 *
 * Unlike [CachedNetworkProperty], the TTL is not fixed at construction time but is
 * passed as a parameter to [get], allowing callers to vary freshness requirements.
 * Returns a [CacheResult] containing both the data and cache-hit metadata.
 *
 * @param T the type of the cached value
 * @param fetcher suspend function invoked to produce a fresh value on cache miss
 */
class DynamicTtlCache<T>(
    private val fetcher: suspend () -> T,
) {
    @Volatile
    private var cachedValue: T? = null

    @Volatile
    private var lastFetchTime: Long = 0L

    private val mutex = Mutex()

    suspend fun get(ttlMillis: Long): CacheResult<T> {
        val now = System.currentTimeMillis()
        cachedValue?.let { value ->
            if (now - lastFetchTime < ttlMillis) {
                return CacheResult(value, lastFetchTime, fromCache = true)
            }
        }
        return mutex.withLock {
            val nowInner = System.currentTimeMillis()
            cachedValue?.let { value ->
                if (nowInner - lastFetchTime < ttlMillis) {
                    return@withLock CacheResult(value, lastFetchTime, fromCache = true)
                }
            }
            val fresh = fetcher()
            cachedValue = fresh
            lastFetchTime = nowInner
            CacheResult(fresh, nowInner, fromCache = false)
        }
    }

    fun invalidate() {
        cachedValue = null
        lastFetchTime = 0L
    }
}

/**
 * Result of a [DynamicTtlCache.get] call, carrying both the data and cache metadata.
 *
 * @property data the cached or freshly fetched value
 * @property fetchTimeMillis epoch millisecond timestamp when [data] was originally fetched
 * @property fromCache `true` if [data] was served from cache; `false` if freshly fetched
 */
data class CacheResult<T>(
    val data: T,
    val fetchTimeMillis: Long,
    val fromCache: Boolean,
)
