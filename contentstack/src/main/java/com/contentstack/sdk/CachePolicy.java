package com.contentstack.sdk;

/**
 * cache policy for {@link Query}.
 *
 * @author contentstack.com, Inc
 */
public enum CachePolicy {

    /**
     * To fetch data from cache.
     */
    CACHE_ONLY,

    /**
     * To fetch data from network and response will be saved in cache.
     */
    NETWORK_ONLY,

    /**
     * To fetch data from cache if data not available in cache then it will send a network call and response will be saved in cache.
     */
    CACHE_ELSE_NETWORK,

    /**
     * To fetch data from network and response will be saved in cache ; if network not available then it will fetch data from cache.
     */
    NETWORK_ELSE_CACHE,

    /**
     * To fetch data from cache and send a network call and response will be saved in cache.
     */
    CACHE_THEN_NETWORK,

    /**
     * To fetch data from network call and response will not be saved cache.
     */
    IGNORE_CACHE;

}
