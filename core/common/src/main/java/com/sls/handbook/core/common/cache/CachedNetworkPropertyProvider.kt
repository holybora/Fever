package com.sls.handbook.core.common.cache

/**
 * Factory for creating [CachedNetworkProperty] instances with validated TTL.
 *
 * @param T the type of the cached value
 * @param ttlMillis cache lifetime in milliseconds; validated to be positive on [create]
 * @param fetcher suspend function used by the created cache to fetch fresh values
 */
class CachedNetworkPropertyProvider<T>(
    private val ttlMillis: Long,
    private val fetcher: suspend () -> T,
) {
    fun create(): CachedNetworkProperty<T> {
        require(ttlMillis > 0) {
            "TTL must be positive, was $ttlMillis"
        }
        return CachedNetworkProperty(ttlMillis, fetcher)
    }
}

fun <T> cachedNetwork(ttlMillis: Long, fetcher: suspend () -> T): CachedNetworkProperty<T> {
    require(ttlMillis > 0) {
        "TTL must be positive, was $ttlMillis"
    }
    return CachedNetworkProperty(ttlMillis, fetcher)
}
