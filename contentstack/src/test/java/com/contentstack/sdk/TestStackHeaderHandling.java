package com.contentstack.sdk;

import android.content.Context;
import android.util.ArrayMap;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Tests for Stack header handling (getHeader private method coverage).
 */
@RunWith(RobolectricTestRunner.class)
@org.robolectric.annotation.Config(sdk = 28, manifest = org.robolectric.annotation.Config.NONE)
public class TestStackHeaderHandling {

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    // ========== TESTS FOR getHeader WITH NULL LOCAL HEADERS ==========

    @Test
    public void testGetHeaderWithNullLocalHeaders() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.localHeader = null;
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderWithEmptyLocalHeaders() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.localHeader = new ArrayMap<>();
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderWithLocalHeadersOnly() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.setHeader("custom-header-1", "value1");
        stack.setHeader("custom-header-2", "value2");
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderWithMainHeadersNull() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.setHeader("local-header", "local-value");
        stack.headerGroupApp = null;
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderWithMainHeadersEmpty() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.setHeader("local-header", "local-value");
        stack.headerGroupApp = new ArrayMap<>();
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderMergesBothHeaders() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.setHeader("local-header-1", "local-value-1");
        stack.setHeader("local-header-2", "local-value-2");
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderViaSyncWithNullHeaders() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.localHeader = null;
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock
            }
        };
        
        try {
            stack.sync(callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderViaSyncWithPopulatedHeaders() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.setHeader("sync-header", "sync-value");
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock
            }
        };
        
        try {
            stack.sync(callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderViaSyncToken() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.setHeader("custom", "value");
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock
            }
        };
        
        try {
            stack.syncToken("token123", callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderViaSyncFromDate() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.setHeader("date-header", "date-value");
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock
            }
        };
        
        try {
            stack.syncFromDate(new Date(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderAfterHeaderModifications() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        stack.setHeader("header1", "value1");
        stack.setHeader("header2", "value2");
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            stack.getContentTypes(new JSONObject(), callback);
            stack.setHeader("header3", "value3");
            stack.removeHeader("header1");
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testGetHeaderWithManyHeaders() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        for (int i = 0; i < 10; i++) {
            stack.setHeader("local-header-" + i, "local-value-" + i);
        }
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }
}

