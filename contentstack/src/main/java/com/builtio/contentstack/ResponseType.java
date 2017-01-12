package com.builtio.contentstack;

/**
 * Response Type.
 *
 * In following categories.
 * Network, Cache.
 *
 * @author  built.io, Inc
 */
public enum ResponseType {

    /**
     * Response from network.
     */
    NETWORK,

    /**
     * Response from Cache.
     */
    CACHE,

    /**
     * Request not reach up to network and cache.
     */
    UNKNOWN,

}
