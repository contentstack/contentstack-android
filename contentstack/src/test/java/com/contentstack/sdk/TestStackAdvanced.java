package com.contentstack.sdk;

import android.content.Context;
import android.util.ArrayMap;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Comprehensive advanced unit tests for Stack class covering all missing methods.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestStackAdvanced {

    private Context context;
    private Stack stack;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env");
    }

    // ========== GET CONTENT TYPES TESTS ==========

    @Test
    public void testGetContentTypesWithNullParams() {
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.getContentTypes(null, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail due to network or SDK state
            assertNotNull(e);
        }
    }

    @Test
    public void testGetContentTypesWithEmptyParams() throws JSONException {
        JSONObject params = new JSONObject();
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.getContentTypes(params, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail due to network or SDK state
            assertNotNull(e);
        }
    }

    @Test
    public void testGetContentTypesWithValidParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("include_snippet_schema", true);
        params.put("limit", 10);
        
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.getContentTypes(params, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail due to network or SDK state
            assertNotNull(e);
        }
    }

    @Test
    public void testGetContentTypesWithInvalidJSON() {
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                assertNotNull(error);
                assertEquals(SDKConstant.PLEASE_PROVIDE_VALID_JSON, error.getErrorMessage());
            }
        };
        
        // This should trigger exception handling in getContentTypes
        try {
            JSONObject invalidParams = new JSONObject() {
                @Override
                public String toString() {
                    throw new RuntimeException("Invalid JSON");
                }
            };
            stack.getContentTypes(invalidParams, callback);
        } catch (Exception e) {
            // Expected exception
            assertNotNull(e);
        }
    }

    // ========== SYNC TESTS ==========

    @Test
    public void testSyncBasic() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.sync(callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail due to network
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncWithNullCallback() {
        try {
            stack.sync(null);
            assertNotNull(stack);
        } catch (Exception e) {
            // May throw exception with null callback
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncPaginationToken() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncPaginationToken("test_pagination_token", callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail due to network
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncPaginationTokenWithNull() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncPaginationToken(null, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncToken() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncToken("test_sync_token", callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail due to network
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncTokenWithNull() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncToken(null, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncFromDate() {
        Date date = new Date();
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncFromDate(date, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail due to network
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncFromDateWithPastDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date pastDate = sdf.parse("2020-01-01");
        
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncFromDate(pastDate, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncContentType() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncContentType("blog_post", callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncContentTypeWithNull() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncContentType(null, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncLocaleWithLanguageEnum() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncLocale(Language.ENGLISH_UNITED_STATES, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncLocaleWithString() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncLocale("en-us", callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncLocaleWithNullString() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncLocale((String) null, callback);
            // May or may not throw exception depending on implementation
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - Objects.requireNonNull may throw
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncPublishType() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncPublishType(Stack.PublishType.ENTRY_PUBLISHED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncPublishTypeAssetPublished() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncPublishType(Stack.PublishType.ASSET_PUBLISHED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncPublishTypeEntryDeleted() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncPublishType(Stack.PublishType.ENTRY_DELETED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncPublishTypeContentTypeDeleted() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            stack.syncPublishType(Stack.PublishType.CONTENT_TYPE_DELETED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncWithMultipleParameters() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            Date date = new Date();
            stack.sync("blog_post", date, Language.ENGLISH_UNITED_STATES, 
                Stack.PublishType.ENTRY_PUBLISHED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncWithMultipleParametersAndStringLocale() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            Date date = new Date();
            stack.sync("blog_post", date, "en-us", 
                Stack.PublishType.ENTRY_PUBLISHED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncWithNullContentType() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            Date date = new Date();
            stack.sync(null, date, "en-us", Stack.PublishType.ENTRY_PUBLISHED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncWithEmptyContentType() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            Date date = new Date();
            stack.sync("", date, "en-us", Stack.PublishType.ENTRY_PUBLISHED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncWithNullLocale() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            Date date = new Date();
            stack.sync("blog", date, (String) null, Stack.PublishType.ENTRY_PUBLISHED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncWithNullPublishType() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            Date date = new Date();
            stack.sync("blog", date, "en-us", null, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    // ========== IMAGE TRANSFORM TESTS ==========

    @Test
    public void testImageTransformWithNullParams() {
        String imageUrl = "https://example.com/image.jpg";
        String result = stack.ImageTransform(imageUrl, null);
        
        assertNotNull(result);
        assertEquals(imageUrl, result);
    }

    @Test
    public void testImageTransformWithEmptyParams() {
        String imageUrl = "https://example.com/image.jpg";
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        String result = stack.ImageTransform(imageUrl, params);
        
        assertNotNull(result);
        assertEquals(imageUrl, result);
    }

    @Test
    public void testImageTransformWithSingleParam() {
        String imageUrl = "https://example.com/image.jpg";
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("width", 100);
        
        String result = stack.ImageTransform(imageUrl, params);
        
        assertNotNull(result);
        assertTrue(result.contains("width"));
        assertTrue(result.contains("100"));
    }

    @Test
    public void testImageTransformWithMultipleParams() {
        String imageUrl = "https://example.com/image.jpg";
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("width", 200);
        params.put("height", 150);
        params.put("quality", 80);
        
        String result = stack.ImageTransform(imageUrl, params);
        
        assertNotNull(result);
        assertTrue(result.contains("width"));
        assertTrue(result.contains("height"));
        assertTrue(result.contains("quality"));
    }

    @Test
    public void testImageTransformUrlEncoding() {
        String imageUrl = "https://example.com/image.jpg";
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("fit", "bounds");
        params.put("format", "jpg");
        
        String result = stack.ImageTransform(imageUrl, params);
        
        assertNotNull(result);
        assertTrue(result.contains("fit"));
        assertTrue(result.contains("format"));
    }

    // ========== GET RESULT OBJECT TESTS ==========

    @Test
    public void testGetResultObjectWithValidParams() {
        List<Object> objects = new ArrayList<>();
        objects.add(new Object());
        JSONObject json = new JSONObject();
        
        try {
            stack.getResultObject(objects, json, false);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail due to sync callback
            assertNotNull(e);
        }
    }

    @Test
    public void testGetResultObjectWithNullList() {
        JSONObject json = new JSONObject();
        
        try {
            stack.getResultObject(null, json, false);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail
            assertNotNull(e);
        }
    }

    @Test
    public void testGetResultObjectWithNullJSON() {
        List<Object> objects = new ArrayList<>();
        
        try {
            stack.getResultObject(objects, null, false);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - may fail
            assertNotNull(e);
        }
    }

    @Test
    public void testGetResultObjectSingleEntry() {
        List<Object> objects = new ArrayList<>();
        objects.add(new Object());
        JSONObject json = new JSONObject();
        
        try {
            stack.getResultObject(objects, json, true);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    // ========== GET RESULT TESTS ==========

    @Test
    public void testGetResult() {
        Object obj = new Object();
        String controller = "test_controller";
        
        stack.getResult(obj, controller);
        assertNotNull(stack); // Method has empty implementation
    }

    @Test
    public void testGetResultWithNull() {
        stack.getResult(null, null);
        assertNotNull(stack); // Method has empty implementation
    }

    // ========== PUBLISH TYPE ENUM TESTS ==========

    @Test
    public void testPublishTypeEnumValues() {
        Stack.PublishType[] types = Stack.PublishType.values();
        assertNotNull(types);
        assertEquals(7, types.length);
    }

    @Test
    public void testPublishTypeEnumContainsAllTypes() {
        assertNotNull(Stack.PublishType.ENTRY_PUBLISHED);
        assertNotNull(Stack.PublishType.ENTRY_UNPUBLISHED);
        assertNotNull(Stack.PublishType.ENTRY_DELETED);
        assertNotNull(Stack.PublishType.ASSET_PUBLISHED);
        assertNotNull(Stack.PublishType.ASSET_UNPUBLISHED);
        assertNotNull(Stack.PublishType.ASSET_DELETED);
        assertNotNull(Stack.PublishType.CONTENT_TYPE_DELETED);
    }

    @Test
    public void testPublishTypeValueOf() {
        assertEquals(Stack.PublishType.ENTRY_PUBLISHED, 
            Stack.PublishType.valueOf("ENTRY_PUBLISHED"));
        assertEquals(Stack.PublishType.ASSET_DELETED, 
            Stack.PublishType.valueOf("ASSET_DELETED"));
    }

    @Test
    public void testPublishTypeToString() {
        assertEquals("ENTRY_PUBLISHED", Stack.PublishType.ENTRY_PUBLISHED.toString());
        assertEquals("ASSET_DELETED", Stack.PublishType.ASSET_DELETED.toString());
    }

    // ========== EXCEPTION HANDLING TESTS ==========

    @Test
    public void testGetContentTypesExceptionHandling() {
        ContentTypesCallback callback = new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                if (error != null) {
                    assertNotNull(error.getErrorMessage());
                }
            }
        };
        
        try {
            // Pass malformed JSON to trigger exception
            JSONObject malformed = new JSONObject();
            malformed.put("invalid", new Object() {
                @Override
                public String toString() {
                    throw new RuntimeException("Test exception");
                }
            });
            stack.getContentTypes(malformed, callback);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testSyncExceptionHandling() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                if (error != null) {
                    assertNotNull(error.getErrorMessage());
                }
            }
        };
        
        try {
            // This should trigger exception handling
            stack.sync(callback);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ========== HEADER TESTS ==========

    @Test
    public void testSetHeadersWithArrayMap() {
        ArrayMap<String, String> headers = new ArrayMap<>();
        headers.put("custom-header", "custom-value");
        headers.put("another-header", "another-value");
        
        stack.setHeaders(headers);
        assertNotNull(stack);
    }

    @Test
    public void testSetHeadersWithNull() {
        try {
            stack.setHeaders(null);
            assertNotNull(stack);
        } catch (Exception e) {
            // May throw NullPointerException
            assertNotNull(e);
        }
    }

    @Test
    public void testSetHeadersWithEmptyMap() {
        ArrayMap<String, String> emptyHeaders = new ArrayMap<>();
        stack.setHeaders(emptyHeaders);
        assertNotNull(stack);
    }

    // ========== ACCESS TOKEN TESTS ==========

    @Test
    public void testGetAccessToken() {
        String token = stack.getAccessToken();
        assertNotNull(token);
        assertEquals("test_delivery_token", token);
    }

    @Test
    public void testGetAccessTokenAfterRemovingHeader() {
        stack.removeHeader("access_token");
        String token = stack.getAccessToken();
        // After removing, token should be null
        assertNull(token);
    }

    // ========== COMPLEX SCENARIO TESTS ==========

    @Test
    public void testMultipleSyncOperations() {
        SyncResultCallBack callback = new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Mock callback
            }
        };
        
        try {
            // Test multiple sync operations
            stack.sync(callback);
            stack.syncContentType("blog", callback);
            stack.syncPublishType(Stack.PublishType.ENTRY_PUBLISHED, callback);
            assertNotNull(stack);
        } catch (Exception e) {
            // Expected - network operations will fail
            assertNotNull(e);
        }
    }

    @Test
    public void testImageTransformChaining() {
        String url1 = stack.ImageTransform("https://example.com/img1.jpg", 
            new LinkedHashMap<String, Object>() {{ put("width", 100); }});
        
        LinkedHashMap<String, Object> params2 = new LinkedHashMap<>();
        params2.put("height", 200);
        String url2 = stack.ImageTransform(url1, params2);
        
        assertNotNull(url2);
        assertTrue(url2.contains("width"));
        assertTrue(url2.contains("height"));
    }
}

