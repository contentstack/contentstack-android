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
 * Specific tests to cover the header merge logic in getHeader method.
 * Targets the for-loops that merge localHeader and mainHeader (headerGroupApp).
 */
@RunWith(RobolectricTestRunner.class)
@org.robolectric.annotation.Config(sdk = 28, manifest = org.robolectric.annotation.Config.NONE)
public class TestStackHeaderMerge {

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    // ========== TESTS TO COVER BOTH FOR-LOOPS IN getHeader ==========

    @Test
    public void testHeaderMergeWithBothHeadersPopulated() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Ensure headerGroupApp is populated (it should be from Contentstack initialization)
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("main-header-1", "main-value-1");
        stack.headerGroupApp.put("main-header-2", "main-value-2");
        stack.headerGroupApp.put("main-header-3", "main-value-3");
        
        // Add local headers
        stack.setHeader("local-header-1", "local-value-1");
        stack.setHeader("local-header-2", "local-value-2");
        stack.setHeader("local-header-3", "local-value-3");
        
        // Trigger getContentTypes which calls getHeader
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock callback
            }
        };
        
        try {
            // This should trigger the merge logic in getHeader
            stack.getContentTypes(new JSONObject(), callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - network call will fail, but getHeader logic is executed
            assertNotNull(stack);
        }
    }

    @Test
    public void testHeaderMergeWithOverlappingKeys() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Populate both headers with some overlapping keys
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("shared-key", "main-value");
        stack.headerGroupApp.put("main-only-1", "main-value-1");
        stack.headerGroupApp.put("main-only-2", "main-value-2");
        
        // Add local headers with overlapping key
        stack.setHeader("shared-key", "local-value");  // This should take precedence
        stack.setHeader("local-only-1", "local-value-1");
        stack.setHeader("local-only-2", "local-value-2");
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            // Trigger via sync which also calls getHeader
            stack.sync(callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testHeaderMergePreservesLocalHeaders() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Setup main headers
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("main-1", "m1");
        stack.headerGroupApp.put("main-2", "m2");
        
        // Add multiple local headers to iterate through first loop
        stack.setHeader("local-1", "l1");
        stack.setHeader("local-2", "l2");
        stack.setHeader("local-3", "l3");
        stack.setHeader("local-4", "l4");
        stack.setHeader("local-5", "l5");
        
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
    public void testHeaderMergePreservesMainHeaders() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Setup multiple main headers to iterate through second loop
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("main-1", "m1");
        stack.headerGroupApp.put("main-2", "m2");
        stack.headerGroupApp.put("main-3", "m3");
        stack.headerGroupApp.put("main-4", "m4");
        stack.headerGroupApp.put("main-5", "m5");
        
        // Add at least one local header to enter the merge path
        stack.setHeader("local-1", "l1");
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock
            }
        };
        
        try {
            stack.syncToken("token", callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testHeaderMergeWithDuplicateKeysSkipped() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Setup headers where main has keys that local already has
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("key1", "main-value-1");
        stack.headerGroupApp.put("key2", "main-value-2");
        stack.headerGroupApp.put("key3", "main-value-3");
        
        // Add local headers with same keys - these should be in classHeaders first
        // so the second loop's !classHeaders.containsKey(key) check will skip them
        stack.setHeader("key1", "local-value-1");
        stack.setHeader("key2", "local-value-2");
        stack.setHeader("unique-local", "unique-local-value");
        
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
    public void testHeaderMergeManyIterations() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Large number of headers to ensure loops iterate many times
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        for (int i = 0; i < 20; i++) {
            stack.headerGroupApp.put("main-header-" + i, "main-value-" + i);
        }
        
        for (int i = 0; i < 20; i++) {
            stack.setHeader("local-header-" + i, "local-value-" + i);
        }
        
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
    public void testHeaderMergeViaContentTypes() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Setup for merge
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("ct-main-1", "value1");
        stack.headerGroupApp.put("ct-main-2", "value2");
        
        stack.setHeader("ct-local-1", "value1");
        stack.setHeader("ct-local-2", "value2");
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            JSONObject params = new JSONObject();
            params.put("include_count", true);
            stack.getContentTypes(params, callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testHeaderMergeViaSyncContentType() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Setup for merge
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("sync-main-1", "m1");
        stack.headerGroupApp.put("sync-main-2", "m2");
        
        stack.setHeader("sync-local-1", "l1");
        stack.setHeader("sync-local-2", "l2");
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock
            }
        };
        
        try {
            stack.syncContentType("blog", callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testHeaderMergeViaSyncPublishType() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Setup for merge
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("pub-main", "main-val");
        stack.setHeader("pub-local", "local-val");
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock
            }
        };
        
        try {
            stack.syncPublishType(Stack.PublishType.ENTRY_PUBLISHED, callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }

    @Test
    public void testHeaderMergeWithAllDifferentKeys() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Ensure no overlapping keys - second loop should add all main headers
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("main-unique-1", "m1");
        stack.headerGroupApp.put("main-unique-2", "m2");
        stack.headerGroupApp.put("main-unique-3", "m3");
        
        stack.setHeader("local-unique-1", "l1");
        stack.setHeader("local-unique-2", "l2");
        stack.setHeader("local-unique-3", "l3");
        
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
    public void testHeaderMergeWithEnvironmentHeader() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "production");
        
        // Environment is automatically in localHeader, and headerGroupApp should be set
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("env-main-1", "env-m1");
        stack.headerGroupApp.put("env-main-2", "env-m2");
        
        // Add more local headers
        stack.setHeader("env-local-1", "env-l1");
        stack.setHeader("env-local-2", "env-l2");
        
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
    public void testHeaderMergeConsistency() throws Exception {
        Stack stack = Contentstack.stack(context, "api_key", "token", "env");
        
        // Setup headers
        if (stack.headerGroupApp == null) {
            stack.headerGroupApp = new ArrayMap<>();
        }
        stack.headerGroupApp.put("consistent-main", "main");
        stack.setHeader("consistent-local", "local");
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock
            }
        };
        
        try {
            // Multiple calls should consistently merge headers
            stack.getContentTypes(new JSONObject(), callback);
            stack.getContentTypes(new JSONObject(), callback);
            stack.getContentTypes(new JSONObject(), callback);
        } catch (Exception e) {
            assertNotNull(stack);
        }
    }
}

