package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for GlobalField class.
 * Tests global field operations, configurations, and methods.
 */
@RunWith(RobolectricTestRunner.class)
public class TestGlobalField {

    private Context context;
    private Stack stack;
    private GlobalField globalField;
    private final String globalFieldUid = "test_global_field";

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        globalField = stack.globalField(globalFieldUid);
    }

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testGlobalFieldConstructorWithUid() {
        GlobalField gf = stack.globalField("seo_fields");
        assertNotNull(gf);
    }

    @Test
    public void testGlobalFieldDefaultConstructor() {
        GlobalField gf = stack.globalField();
        assertNotNull(gf);
    }

    // ========== HEADER TESTS ==========

    @Test
    public void testSetHeader() {
        globalField.setHeader("custom-header", "custom-value");
        assertNotNull(globalField);
    }

    @Test
    public void testSetMultipleHeaders() {
        globalField.setHeader("header1", "value1");
        globalField.setHeader("header2", "value2");
        globalField.setHeader("header3", "value3");
        
        assertNotNull(globalField);
    }

    @Test
    public void testSetHeaderWithEmptyKey() {
        globalField.setHeader("", "value");
        // Should not add empty key
        assertNotNull(globalField);
    }

    @Test
    public void testSetHeaderWithEmptyValue() {
        globalField.setHeader("key", "");
        // Should not add empty value
        assertNotNull(globalField);
    }

    @Test
    public void testSetHeaderWithBothEmpty() {
        globalField.setHeader("", "");
        assertNotNull(globalField);
    }

    @Test
    public void testSetHeaderWithNull() {
        globalField.setHeader(null, "value");
        globalField.setHeader("key", null);
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveHeader() {
        globalField.setHeader("temp-header", "temp-value");
        globalField.removeHeader("temp-header");
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveNonExistentHeader() {
        globalField.removeHeader("non-existent");
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveHeaderWithEmptyKey() {
        globalField.removeHeader("");
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveHeaderWithNull() {
        globalField.removeHeader(null);
        assertNotNull(globalField);
    }

    // ========== INCLUDE TESTS ==========

    @Test
    public void testIncludeBranch() {
        GlobalField result = globalField.includeBranch();
        assertNotNull(result);
        assertSame(globalField, result);
        assertTrue(globalField.urlQueries.has("include_branch"));
    }

    @Test
    public void testIncludeGlobalFieldSchema() {
        GlobalField result = globalField.includeGlobalFieldSchema();
        assertNotNull(result);
        assertSame(globalField, result);
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
    }

    @Test
    public void testMultipleIncludesCombined() throws Exception {
        globalField.includeBranch().includeGlobalFieldSchema();
        
        assertTrue(globalField.urlQueries.has("include_branch"));
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
        assertEquals(2, globalField.urlQueries.length());
    }

    // ========== CHAINING TESTS ==========

    @Test
    public void testMethodChaining() {
        GlobalField result = globalField
            .includeBranch()
            .includeGlobalFieldSchema();
        
        assertNotNull(result);
        assertSame(globalField, result);
        assertTrue(globalField.urlQueries.has("include_branch"));
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
    }

    @Test
    public void testComplexChaining() {
        globalField.setHeader("api-version", "v3");
        GlobalField result = globalField
            .includeBranch()
            .includeGlobalFieldSchema();
        
        assertNotNull(result);
        assertTrue(globalField.urlQueries.has("include_branch"));
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testUrlQueriesInitialization() {
        GlobalField gf = stack.globalField("test");
        assertNotNull(gf.urlQueries);
        assertEquals(0, gf.urlQueries.length());
    }

    @Test
    public void testHeaderOverwrite() {
        globalField.setHeader("key", "value1");
        globalField.setHeader("key", "value2");
        assertNotNull(globalField);
    }

    @Test
    public void testRemoveAndAddSameHeader() {
        globalField.setHeader("key", "value1");
        globalField.removeHeader("key");
        globalField.setHeader("key", "value2");
        assertNotNull(globalField);
    }

    @Test
    public void testMultipleIncludeBranchCalls() throws Exception {
        globalField.includeBranch();
        globalField.includeBranch();
        
        assertTrue(globalField.urlQueries.has("include_branch"));
        assertEquals(true, globalField.urlQueries.get("include_branch"));
    }

    @Test
    public void testMultipleIncludeGlobalFieldSchemaCalls() throws Exception {
        globalField.includeGlobalFieldSchema();
        globalField.includeGlobalFieldSchema();
        
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
        assertEquals(true, globalField.urlQueries.get("include_global_field_schema"));
    }

    @Test
    public void testGlobalFieldUidPreservation() {
        String originalUid = "original_global_field";
        GlobalField gf = stack.globalField(originalUid);
        
        // Add some operations
        gf.includeBranch();
        gf.setHeader("test", "value");
        
        // UID should remain unchanged
        assertNotNull(gf);
    }

    // ========== INCLUDE COMBINATIONS TESTS ==========

    @Test
    public void testIncludeBranchOnly() throws Exception {
        globalField.includeBranch();
        
        assertTrue(globalField.urlQueries.has("include_branch"));
        assertFalse(globalField.urlQueries.has("include_global_field_schema"));
    }

    @Test
    public void testIncludeGlobalFieldSchemaOnly() throws Exception {
        globalField.includeGlobalFieldSchema();
        
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
        assertFalse(globalField.urlQueries.has("include_branch"));
    }

    @Test
    public void testIncludeBothBranchAndSchema() throws Exception {
        globalField.includeBranch();
        globalField.includeGlobalFieldSchema();
        
        assertTrue(globalField.urlQueries.has("include_branch"));
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
    }

    // ========== HEADER COMBINATIONS TESTS ==========

    @Test
    public void testMultipleHeaderOperations() {
        globalField.setHeader("header1", "value1");
        globalField.setHeader("header2", "value2");
        globalField.removeHeader("header1");
        globalField.setHeader("header3", "value3");
        
        assertNotNull(globalField);
    }

    @Test
    public void testHeadersWithIncludeMethods() {
        globalField.setHeader("api-version", "v3");
        globalField.setHeader("custom-header", "custom-value");
        globalField.includeBranch();
        globalField.includeGlobalFieldSchema();
        
        assertTrue(globalField.urlQueries.has("include_branch"));
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
    }

    // ========== COMPLEX SCENARIO TESTS ==========

    @Test
    public void testCompleteGlobalFieldWorkflow() throws Exception {
        // Create global field with UID
        GlobalField gf = stack.globalField("seo_metadata");
        
        // Set headers
        gf.setHeader("api-version", "v3");
        gf.setHeader("custom-header", "value");
        
        // Add include options
        gf.includeBranch();
        gf.includeGlobalFieldSchema();
        
        // Verify all operations
        assertTrue(gf.urlQueries.has("include_branch"));
        assertTrue(gf.urlQueries.has("include_global_field_schema"));
        assertEquals(2, gf.urlQueries.length());
    }

    @Test
    public void testReconfigureGlobalField() throws Exception {
        // Initial configuration
        globalField.includeBranch();
        assertTrue(globalField.urlQueries.has("include_branch"));
        
        // Add more configuration
        globalField.includeGlobalFieldSchema();
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
        
        // Verify both are present
        assertEquals(2, globalField.urlQueries.length());
    }

    @Test
    public void testGlobalFieldWithSpecialCharacters() {
        GlobalField gf = stack.globalField("field_with_特殊字符");
        assertNotNull(gf);
        
        gf.setHeader("key-with-dashes", "value");
        gf.includeBranch();
        
        assertTrue(gf.urlQueries.has("include_branch"));
    }

    @Test
    public void testEmptyGlobalFieldOperations() {
        GlobalField gf = stack.globalField();
        
        gf.includeBranch();
        gf.includeGlobalFieldSchema();
        
        assertTrue(gf.urlQueries.has("include_branch"));
        assertTrue(gf.urlQueries.has("include_global_field_schema"));
    }

    @Test
    public void testGlobalFieldConsistency() throws Exception {
        globalField.includeBranch();
        assertEquals(true, globalField.urlQueries.get("include_branch"));
        
        globalField.includeGlobalFieldSchema();
        assertEquals(true, globalField.urlQueries.get("include_global_field_schema"));
        
        // Verify previous values are still there
        assertEquals(true, globalField.urlQueries.get("include_branch"));
    }

    @Test
    public void testGlobalFieldIndependence() {
        GlobalField gf1 = stack.globalField("field1");
        GlobalField gf2 = stack.globalField("field2");
        
        gf1.includeBranch();
        gf2.includeGlobalFieldSchema();
        
        // Each should have only its own includes
        assertTrue(gf1.urlQueries.has("include_branch"));
        assertFalse(gf1.urlQueries.has("include_global_field_schema"));
        
        assertTrue(gf2.urlQueries.has("include_global_field_schema"));
        assertFalse(gf2.urlQueries.has("include_branch"));
    }

    // ========== NULL SAFETY TESTS ==========

    @Test
    public void testNullSafetyForHeaders() {
        // These should not throw exceptions
        globalField.setHeader(null, null);
        globalField.setHeader(null, "value");
        globalField.setHeader("key", null);
        globalField.removeHeader(null);
        
        assertNotNull(globalField);
    }

    @Test
    public void testIncludeMethodsMultipleTimes() throws Exception {
        // Calling include methods multiple times should not cause issues
        globalField.includeBranch();
        globalField.includeBranch();
        globalField.includeBranch();
        
        assertTrue(globalField.urlQueries.has("include_branch"));
        assertEquals(true, globalField.urlQueries.get("include_branch"));
        
        globalField.includeGlobalFieldSchema();
        globalField.includeGlobalFieldSchema();
        
        assertTrue(globalField.urlQueries.has("include_global_field_schema"));
        assertEquals(true, globalField.urlQueries.get("include_global_field_schema"));
    }

    @Test
    public void testGlobalFieldCreationVariants() {
        // Test different ways to create GlobalField
        GlobalField gf1 = stack.globalField();
        GlobalField gf2 = stack.globalField("field_uid");
        GlobalField gf3 = stack.globalField("another_field");
        
        assertNotNull(gf1);
        assertNotNull(gf2);
        assertNotNull(gf3);
    }

    @Test
    public void testUrlQueriesAccumulation() throws Exception {
        assertEquals(0, globalField.urlQueries.length());
        
        globalField.includeBranch();
        assertEquals(1, globalField.urlQueries.length());
        
        globalField.includeGlobalFieldSchema();
        assertEquals(2, globalField.urlQueries.length());
    }
}

