package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for Group class.
 * Tests all getter methods for different data types and nested structures.
 */
@RunWith(RobolectricTestRunner.class)
public class TestGroup {

    private Context context;
    private Stack stack;
    private JSONObject testJson;
    private Group group;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        
        // Create a test JSON with various data types
        testJson = new JSONObject();
        testJson.put("string_field", "test_string");
        testJson.put("boolean_field", true);
        testJson.put("number_field", 42);
        testJson.put("float_field", 3.14);
        testJson.put("double_field", 3.14159);
        testJson.put("long_field", 1234567890L);
        testJson.put("short_field", 100);
        testJson.put("date_field", "2023-11-06T10:30:00.000Z");
        
        // JSON Object
        JSONObject nestedObject = new JSONObject();
        nestedObject.put("nested_key", "nested_value");
        testJson.put("object_field", nestedObject);
        
        // JSON Array
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("item1");
        jsonArray.put("item2");
        testJson.put("array_field", jsonArray);
        
        // Asset object
        JSONObject assetObject = new JSONObject();
        assetObject.put("uid", "asset_uid_1");
        assetObject.put("url", "https://example.com/asset.jpg");
        testJson.put("asset_field", assetObject);
        
        // Assets array
        JSONArray assetsArray = new JSONArray();
        JSONObject asset1 = new JSONObject();
        asset1.put("uid", "asset_1");
        assetsArray.put(asset1);
        JSONObject asset2 = new JSONObject();
        asset2.put("uid", "asset_2");
        assetsArray.put(asset2);
        testJson.put("assets_field", assetsArray);
        
        // Nested group
        JSONObject groupObject = new JSONObject();
        groupObject.put("group_key", "group_value");
        testJson.put("group_field", groupObject);
        
        // Groups array
        JSONArray groupsArray = new JSONArray();
        JSONObject group1 = new JSONObject();
        group1.put("name", "Group 1");
        groupsArray.put(group1);
        JSONObject group2 = new JSONObject();
        group2.put("name", "Group 2");
        groupsArray.put(group2);
        testJson.put("groups_field", groupsArray);
        
        // Entry references
        JSONArray entriesArray = new JSONArray();
        JSONObject entry1 = new JSONObject();
        entry1.put("uid", "entry_1");
        entry1.put("title", "Entry 1");
        entriesArray.put(entry1);
        testJson.put("entries_field", entriesArray);
        
        // Create Group instance using reflection
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        group = constructor.newInstance(stack, testJson);
    }

    // ========== TO JSON TESTS ==========

    @Test
    public void testToJSON() {
        JSONObject result = group.toJSON();
        assertNotNull(result);
        assertEquals(testJson.toString(), result.toString());
        assertTrue(result.has("string_field"));
    }

    // ========== GET METHOD TESTS ==========

    @Test
    public void testGetWithValidKey() {
        Object result = group.get("string_field");
        assertNotNull(result);
        assertEquals("test_string", result);
    }

    @Test
    public void testGetWithNullKey() {
        Object result = group.get(null);
        assertNull(result);
    }

    @Test
    public void testGetWithNonExistentKey() {
        Object result = group.get("non_existent_key");
        // Android Group returns null for non-existent keys (doesn't throw)
        assertNull(result);
    }

    @Test
    public void testGetWithNullResultJson() throws Exception {
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group nullGroup = constructor.newInstance(stack, null);
        
        Object result = nullGroup.get("any_key");
        assertNull(result);
    }

    // ========== GET STRING TESTS ==========

    @Test
    public void testGetStringWithValidKey() {
        String result = group.getString("string_field");
        assertNotNull(result);
        assertEquals("test_string", result);
    }

    @Test
    public void testGetStringWithNullValue() {
        String result = group.getString("non_existent_key");
        assertNull(result);
    }

    @Test
    public void testGetStringWithNullKey() {
        String result = group.getString(null);
        assertNull(result);
    }

    // ========== GET BOOLEAN TESTS ==========

    @Test
    public void testGetBooleanWithValidKey() {
        Boolean result = group.getBoolean("boolean_field");
        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    public void testGetBooleanWithNullValue() {
        Boolean result = group.getBoolean("non_existent_key");
        // Should return false for non-existent key
        assertFalse(result);
    }

    @Test
    public void testGetBooleanWithFalseValue() throws Exception {
        testJson.put("false_field", false);
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group newGroup = constructor.newInstance(stack, testJson);
        
        Boolean result = newGroup.getBoolean("false_field");
        assertFalse(result);
    }

    // ========== GET JSON ARRAY TESTS ==========

    @Test
    public void testGetJSONArrayWithValidKey() {
        JSONArray result = group.getJSONArray("array_field");
        assertNotNull(result);
        assertEquals(2, result.length());
        assertEquals("item1", result.opt(0));
    }

    @Test
    public void testGetJSONArrayWithNullValue() {
        JSONArray result = group.getJSONArray("non_existent_key");
        assertNull(result);
    }

    // ========== GET JSON OBJECT TESTS ==========

    @Test
    public void testGetJSONObjectWithValidKey() {
        JSONObject result = group.getJSONObject("object_field");
        assertNotNull(result);
        assertTrue(result.has("nested_key"));
        assertEquals("nested_value", result.opt("nested_key"));
    }

    @Test
    public void testGetJSONObjectWithNullValue() {
        JSONObject result = group.getJSONObject("non_existent_key");
        assertNull(result);
    }

    // ========== GET NUMBER TESTS ==========

    @Test
    public void testGetNumberWithValidKey() {
        Number result = group.getNumber("number_field");
        assertNotNull(result);
        assertEquals(42, result.intValue());
    }

    @Test
    public void testGetNumberWithNullValue() {
        Number result = group.getNumber("non_existent_key");
        assertNull(result);
    }

    // ========== GET INT TESTS ==========

    @Test
    public void testGetIntWithValidKey() {
        int result = group.getInt("number_field");
        assertEquals(42, result);
    }

    @Test
    public void testGetIntWithNullValue() {
        int result = group.getInt("non_existent_key");
        assertEquals(0, result);
    }

    // ========== GET FLOAT TESTS ==========

    @Test
    public void testGetFloatWithValidKey() {
        float result = group.getFloat("float_field");
        assertEquals(3.14f, result, 0.01);
    }

    @Test
    public void testGetFloatWithNullValue() {
        float result = group.getFloat("non_existent_key");
        assertEquals(0f, result, 0.01);
    }

    // ========== GET DOUBLE TESTS ==========

    @Test
    public void testGetDoubleWithValidKey() {
        double result = group.getDouble("double_field");
        assertEquals(3.14159, result, 0.00001);
    }

    @Test
    public void testGetDoubleWithNullValue() {
        double result = group.getDouble("non_existent_key");
        assertEquals(0.0, result, 0.00001);
    }

    // ========== GET LONG TESTS ==========

    @Test
    public void testGetLongWithValidKey() {
        long result = group.getLong("long_field");
        assertEquals(1234567890L, result);
    }

    @Test
    public void testGetLongWithNullValue() {
        long result = group.getLong("non_existent_key");
        assertEquals(0L, result);
    }

    // ========== GET SHORT TESTS ==========

    @Test
    public void testGetShortWithValidKey() {
        short result = group.getShort("short_field");
        assertEquals((short) 100, result);
    }

    @Test
    public void testGetShortWithNullValue() {
        short result = group.getShort("non_existent_key");
        assertEquals((short) 0, result);
    }

    // ========== GET DATE TESTS ==========

    @Test
    public void testGetDateWithValidKey() {
        Calendar result = group.getDate("date_field");
        assertNotNull(result);
    }

    @Test
    public void testGetDateWithNullValue() {
        Calendar result = group.getDate("non_existent_key");
        assertNull(result);
    }

    @Test
    public void testGetDateWithInvalidFormat() throws Exception {
        testJson.put("invalid_date", "not_a_date");
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group newGroup = constructor.newInstance(stack, testJson);
        
        Calendar result = newGroup.getDate("invalid_date");
        // Should return null on exception
        assertNull(result);
    }

    // ========== GET ASSET TESTS ==========

    @Test
    public void testGetAssetWithValidKey() {
        Asset result = group.getAsset("asset_field");
        assertNotNull(result);
    }

    @Test
    public void testGetAssetWithNullValue() {
        try {
            Asset result = group.getAsset("non_existent_key");
            // If no exception is thrown, result should be null
            assertNull(result);
        } catch (NullPointerException e) {
            // Expected behavior - getAsset may throw NPE for non-existent key
            assertNotNull(e);
        }
    }

    // ========== GET ASSETS TESTS ==========

    @Test
    public void testGetAssetsWithValidKey() {
        List<Asset> result = group.getAssets("assets_field");
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAssetsWithEmptyArray() throws Exception {
        testJson.put("empty_assets", new JSONArray());
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group newGroup = constructor.newInstance(stack, testJson);
        
        List<Asset> result = newGroup.getAssets("empty_assets");
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAssetsWithNonJSONObjectItems() throws Exception {
        JSONArray mixedArray = new JSONArray();
        mixedArray.put("not_an_object");
        JSONObject validAsset = new JSONObject();
        validAsset.put("uid", "valid_asset");
        mixedArray.put(validAsset);
        
        testJson.put("mixed_assets", mixedArray);
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group newGroup = constructor.newInstance(stack, testJson);
        
        List<Asset> result = newGroup.getAssets("mixed_assets");
        assertNotNull(result);
        assertEquals(1, result.size());  // Only the valid JSONObject is processed
    }

    // ========== GET GROUP TESTS ==========

    @Test
    public void testGetGroupWithValidKey() {
        Group result = group.getGroup("group_field");
        assertNotNull(result);
        assertEquals("group_value", result.get("group_key"));
    }

    @Test
    public void testGetGroupWithEmptyKey() {
        Group result = group.getGroup("");
        assertNull(result);
    }

    @Test
    public void testGetGroupWithNonExistentKey() {
        Group result = group.getGroup("non_existent_key");
        assertNull(result);
    }

    @Test
    public void testGetGroupWithNonJSONObjectValue() {
        Group result = group.getGroup("string_field");
        assertNull(result);
    }

    // ========== GET GROUPS TESTS ==========

    @Test
    public void testGetGroupsWithValidKey() {
        List<Group> result = group.getGroups("groups_field");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Group 1", result.get(0).get("name"));
        assertEquals("Group 2", result.get(1).get("name"));
    }

    @Test
    public void testGetGroupsWithEmptyKey() {
        List<Group> result = group.getGroups("");
        assertNull(result);
    }

    @Test
    public void testGetGroupsWithNonExistentKey() {
        List<Group> result = group.getGroups("non_existent_key");
        assertNull(result);
    }

    @Test
    public void testGetGroupsWithNonArrayValue() {
        List<Group> result = group.getGroups("string_field");
        assertNull(result);
    }

    @Test
    public void testGetGroupsWithEmptyArray() throws Exception {
        testJson.put("empty_groups", new JSONArray());
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group newGroup = constructor.newInstance(stack, testJson);
        
        List<Group> result = newGroup.getGroups("empty_groups");
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ========== GET HTML TEXT TESTS ==========

    @Test
    public void testGetHtmlTextWithMarkdown() throws Exception {
        testJson.put("markdown_field", "**bold** text");
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group newGroup = constructor.newInstance(stack, testJson);
        
        String result = newGroup.getHtmlText("markdown_field");
        assertNotNull(result);
        assertTrue(result.contains("bold"));
    }

    @Test
    public void testGetHtmlTextWithNullKey() {
        String result = group.getHtmlText(null);
        assertNull(result);
    }

    @Test
    public void testGetHtmlTextWithNonExistentKey() {
        String result = group.getHtmlText("non_existent_key");
        assertNull(result);
    }

    // ========== GET MULTIPLE HTML TEXT TESTS ==========

    @Test
    public void testGetMultipleHtmlTextWithArray() throws Exception {
        JSONArray markdownArray = new JSONArray();
        markdownArray.put("**First** item");
        markdownArray.put("*Second* item");
        testJson.put("markdown_array", markdownArray);
        
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group newGroup = constructor.newInstance(stack, testJson);
        
        java.util.ArrayList<String> result = newGroup.getMultipleHtmlText("markdown_array");
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetMultipleHtmlTextWithNullKey() {
        java.util.ArrayList<String> result = group.getMultipleHtmlText(null);
        assertNull(result);
    }

    @Test
    public void testGetMultipleHtmlTextWithNonExistentKey() {
        java.util.ArrayList<String> result = group.getMultipleHtmlText("non_existent_key");
        assertNull(result);
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testMultipleDataTypes() {
        // Verify all data types can be accessed
        assertNotNull(group.getString("string_field"));
        assertNotNull(group.getBoolean("boolean_field"));
        assertNotNull(group.getNumber("number_field"));
        assertNotNull(group.getJSONObject("object_field"));
        assertNotNull(group.getJSONArray("array_field"));
    }

    @Test
    public void testNullSafety() {
        // Verify null safety for all getter methods
        assertNull(group.get(null));
        assertNull(group.getString(null));
        assertFalse(group.getBoolean(null));
        assertNull(group.getJSONArray(null));
        assertNull(group.getJSONObject(null));
        assertNull(group.getNumber(null));
        // Number getter methods return 0 for null keys
        assertEquals(0, group.getInt(null));
        assertEquals(0f, group.getFloat(null), 0.01);
        assertEquals(0.0, group.getDouble(null), 0.001);
        assertEquals(0L, group.getLong(null));
        assertEquals((short) 0, group.getShort(null));
        assertNull(group.getDate(null));
    }

    @Test
    public void testConstructorWithStackAndJSON() throws Exception {
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        
        JSONObject json = new JSONObject();
        json.put("test_key", "test_value");
        
        Group testGroup = constructor.newInstance(stack, json);
        assertNotNull(testGroup);
        assertEquals("test_value", testGroup.get("test_key"));
    }

    @Test
    public void testGetWithWrongDataType() {
        // Test getting string field as number
        Number result = group.getNumber("string_field");
        assertNull(result);
    }

    @Test
    public void testGetBooleanWithNonBooleanValue() {
        Boolean result = group.getBoolean("string_field");
        // Should return false for non-boolean value
        assertFalse(result);
    }

    @Test
    public void testNestedGroupAccess() {
        Group nestedGroup = group.getGroup("group_field");
        assertNotNull(nestedGroup);
        
        String nestedValue = nestedGroup.getString("group_key");
        assertEquals("group_value", nestedValue);
    }

    @Test
    public void testMultipleGroupsIteration() {
        List<Group> groups = group.getGroups("groups_field");
        assertNotNull(groups);
        
        for (int i = 0; i < groups.size(); i++) {
            Group g = groups.get(i);
            assertNotNull(g);
            assertNotNull(g.get("name"));
        }
    }

    @Test
    public void testComplexNestedStructure() throws Exception {
        JSONObject complex = new JSONObject();
        
        JSONObject level1 = new JSONObject();
        JSONObject level2 = new JSONObject();
        level2.put("deep_key", "deep_value");
        level1.put("level2", level2);
        complex.put("level1", level1);
        
        Constructor<Group> constructor = Group.class.getDeclaredConstructor(Stack.class, JSONObject.class);
        constructor.setAccessible(true);
        Group complexGroup = constructor.newInstance(stack, complex);
        
        Group level1Group = complexGroup.getGroup("level1");
        assertNotNull(level1Group);
        
        Group level2Group = level1Group.getGroup("level2");
        assertNotNull(level2Group);
        
        assertEquals("deep_value", level2Group.get("deep_key"));
    }
}

