package com.contentstack.sdk;

/**
 * Response Type.
 * <p>
 * In following categories.
 * Network, Cache.
 *
 * @author Contentstack.com, Inc
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
