package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SDKControllerTest {

    @Test
    public void testConstructorCoverage() {
        SDKController controller = new SDKController();
        assertNotNull(controller);
    }

    @Test
    public void testConstantsValues() {
        assertEquals("getQueryEntries", SDKController.GET_QUERY_ENTRIES);
        assertEquals("getSingleQueryEntries", SDKController.SINGLE_QUERY_ENTRIES);
        assertEquals("getEntry", SDKController.GET_ENTRY);
        assertEquals("getAllAssets", SDKController.GET_ALL_ASSETS);
        assertEquals("getAssets", SDKController.GET_ASSETS);
        assertEquals("getSync", SDKController.GET_SYNC);
        assertEquals("getContentTypes", SDKController.GET_CONTENT_TYPES);
        assertEquals("getGlobalFields", SDKController.GET_GLOBAL_FIELDS);
    }
}
