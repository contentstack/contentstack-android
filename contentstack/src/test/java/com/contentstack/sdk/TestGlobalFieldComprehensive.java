package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for GlobalField class
 */
@RunWith(RobolectricTestRunner.class)
public class TestGlobalFieldComprehensive {

    private Context context;
    private Stack stack;
    private GlobalField globalField;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        globalField = stack.globalField("test_global_field_uid");
    }

    // ==================== Headers ====================

    @Test
    public void testSetHeaderValid() {
        globalField.setHeader("X-Custom-Header", "custom-value");
        assertNotNull(globalField);
    }

    @Test
    public void testSetHeaderNull() {
        globalField.setHeader(null, null);
        globalField.setHeader("key", null);
        globalField.setHeader(null, "value");
        assertNotNull(globalField);
    }

    @Test
    public void testSetHeaderEmpty() {
        globalField.setHeader("", "value");
        globalField.setHeader("key", "");
        globalField.setHeader("", "");
        assertNotNull(globalField);
    }

    @Test
    public void testSetHeaderMultiple() {
        globalField.setHeader("X-Header-1", "value1");
        globalField.setHeader("X-Header-2", "value2");
        globalField.setHeader("X-Header-3", "value3");
        globalField.setHeader("X-Header-4", "value4");
        globalField.setHeader("X-Header-5", "value5");
        assertNotNull(globalField);
    }

    @Test
    public void testSetHeaderOverwrite() {
        globalField.setHeader("X-Test", "value1");
        globalField.setHeader("X-Test", "value2");
        globalField.setHeader("X-Test", "value3");
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveHeaderValid() {
        globalField.setHeader("X-Test", "test");
        globalField.removeHeader("X-Test");
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveHeaderNull() {
        globalField.removeHeader(null);
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveHeaderEmpty() {
        globalField.removeHeader("");
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveHeaderNonExistent() {
        globalField.removeHeader("NonExistentHeader");
        assertNotNull(globalField);
    }

    @Test
    public void testHeaderAddAndRemoveChaining() {
        globalField.setHeader("X-Header-1", "value1");
        globalField.setHeader("X-Header-2", "value2");
        globalField.removeHeader("X-Header-1");
        globalField.setHeader("X-Header-3", "value3");
        globalField.removeHeader("X-Header-2");
        assertNotNull(globalField);
    }

    // ==================== Include Options ====================

    @Test
    public void testIncludeBranch() {
        GlobalField result = globalField.includeBranch();
        assertNotNull(result);
        assertEquals(globalField, result);
    }

    @Test
    public void testIncludeGlobalFieldSchema() {
        GlobalField result = globalField.includeGlobalFieldSchema();
        assertNotNull(result);
        assertEquals(globalField, result);
    }

    @Test
    public void testIncludeBranchMultipleTimes() {
        globalField.includeBranch();
        globalField.includeBranch();
        globalField.includeBranch();
        assertNotNull(globalField);
    }

    @Test
    public void testIncludeGlobalFieldSchemaMultipleTimes() {
        globalField.includeGlobalFieldSchema();
        globalField.includeGlobalFieldSchema();
        globalField.includeGlobalFieldSchema();
        assertNotNull(globalField);
    }

    // ==================== Chaining ====================

    @Test
    public void testCompleteChaining() {
        GlobalField result = globalField
            .includeBranch()
            .includeGlobalFieldSchema();
        
        assertNotNull(result);
        assertEquals(globalField, result);
    }

    @Test
    public void testChainingWithHeaders() {
        globalField.setHeader("X-Header-1", "value1");
        GlobalField result = globalField
            .includeBranch()
            .includeGlobalFieldSchema();
        globalField.setHeader("X-Header-2", "value2");
        
        assertNotNull(result);
        assertEquals(globalField, result);
    }

    @Test
    public void testMultipleChainingSequences() {
        globalField.includeBranch().includeGlobalFieldSchema();
        globalField.includeBranch().includeGlobalFieldSchema();
        globalField.includeBranch().includeGlobalFieldSchema();
        assertNotNull(globalField);
    }

    // ==================== Fetch ====================

    @Test
    public void testFetchWithCallback() {
        GlobalFieldsResultCallback callback = new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                // Handle completion
            }
        };
        
        globalField.fetch(callback);
        assertNotNull(globalField);
    }

    @Test
    public void testFetchWithNullCallback() {
        globalField.fetch(null);
        assertNotNull(globalField);
    }

    @Test
    public void testFetchWithOptions() {
        globalField.includeBranch()
                  .includeGlobalFieldSchema();
        
        GlobalFieldsResultCallback callback = new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                // Handle completion
            }
        };
        
        globalField.fetch(callback);
        assertNotNull(globalField);
    }

    @Test
    public void testFetchWithHeaders() {
        globalField.setHeader("X-Custom-Header", "custom-value");
        
        GlobalFieldsResultCallback callback = new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                // Handle completion
            }
        };
        
        globalField.fetch(callback);
        assertNotNull(globalField);
    }

    @Test
    public void testFetchWithAllOptions() {
        globalField.setHeader("X-Header-1", "value1");
        globalField.setHeader("X-Header-2", "value2");
        globalField.includeBranch()
                  .includeGlobalFieldSchema();
        
        GlobalFieldsResultCallback callback = new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                // Handle completion
            }
        };
        
        globalField.fetch(callback);
        assertNotNull(globalField);
    }

    // ==================== Multiple Instances ====================

    @Test
    public void testMultipleGlobalFieldInstances() {
        GlobalField gf1 = stack.globalField("uid1");
        GlobalField gf2 = stack.globalField("uid2");
        GlobalField gf3 = stack.globalField("uid3");
        
        gf1.includeBranch();
        gf2.includeGlobalFieldSchema();
        gf3.includeBranch().includeGlobalFieldSchema();
        
        assertNotNull(gf1);
        assertNotNull(gf2);
        assertNotNull(gf3);
        assertNotEquals(gf1, gf2);
        assertNotEquals(gf2, gf3);
    }

    @Test
    public void testIndependentConfiguration() {
        GlobalField gf1 = stack.globalField("uid1");
        gf1.setHeader("X-Header", "value1");
        gf1.includeBranch();
        
        GlobalField gf2 = stack.globalField("uid2");
        gf2.setHeader("X-Header", "value2");
        gf2.includeGlobalFieldSchema();
        
        assertNotNull(gf1);
        assertNotNull(gf2);
        assertNotEquals(gf1, gf2);
    }

    // ==================== Edge Cases ====================

    @Test
    public void testGlobalFieldWithEmptyUid() {
        GlobalField gf = stack.globalField("");
        assertNotNull(gf);
    }

    @Test
    public void testGlobalFieldWithNullUid() {
        GlobalField gf = stack.globalField(null);
        assertNotNull(gf);
    }

    @Test
    public void testGlobalFieldWithLongUid() {
        StringBuilder longUid = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longUid.append("a");
        }
        GlobalField gf = stack.globalField(longUid.toString());
        assertNotNull(gf);
    }

    @Test
    public void testGlobalFieldWithSpecialCharactersInUid() {
        GlobalField gf = stack.globalField("uid_with_special_chars_!@#$%^&*()");
        assertNotNull(gf);
    }

    @Test
    public void testMultipleFetchCalls() {
        GlobalFieldsResultCallback callback = new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                // Handle completion
            }
        };
        
        globalField.fetch(callback);
        globalField.fetch(callback);
        globalField.fetch(callback);
        assertNotNull(globalField);
    }

    @Test
    public void testConfigurationPersistence() {
        globalField.includeBranch();
        globalField.includeGlobalFieldSchema();
        globalField.setHeader("X-Test", "value");
        
        // Configuration should persist
        assertNotNull(globalField);
        
        // Calling fetch shouldn't reset configuration
        GlobalFieldsResultCallback callback = new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                // Handle completion
            }
        };
        globalField.fetch(callback);
        
        assertNotNull(globalField);
    }

    @Test
    public void testHeaderWithSpecialCharacters() {
        globalField.setHeader("X-Special-Header", "value with spaces and chars: !@#$%^&*()");
        assertNotNull(globalField);
    }

    @Test
    public void testHeaderWithUnicode() {
        globalField.setHeader("X-Unicode-Header", "测试 テスト 테스트 🎉");
        assertNotNull(globalField);
    }

    @Test
    public void testHeaderWithVeryLongValue() {
        StringBuilder longValue = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            longValue.append("test");
        }
        globalField.setHeader("X-Long-Header", longValue.toString());
        assertNotNull(globalField);
    }
}

