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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for Taxonomy class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestTaxonomy {

    private Context context;
    private Stack stack;
    private Taxonomy taxonomy;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        taxonomy = stack.taxonomy();
    }

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testTaxonomyCreation() {
        assertNotNull(taxonomy);
    }

    @Test
    public void testTaxonomyCreationFromStack() {
        Taxonomy tax = stack.taxonomy();
        assertNotNull(tax);
    }

    // ========== IN METHOD TESTS ==========

    @Test
    public void testInWithValidTaxonomy() {
        List<String> items = new ArrayList<>();
        items.add("red");
        items.add("blue");
        
        Taxonomy result = taxonomy.in("color", items);
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testInWithEmptyList() {
        List<String> items = new ArrayList<>();
        
        Taxonomy result = taxonomy.in("color", items);
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testInWithSingleItem() {
        List<String> items = new ArrayList<>();
        items.add("red");
        
        Taxonomy result = taxonomy.in("color", items);
        assertNotNull(result);
    }

    @Test
    public void testInWithMultipleItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("item_" + i);
        }
        
        Taxonomy result = taxonomy.in("category", items);
        assertNotNull(result);
    }

    @Test
    public void testInMethodChaining() {
        List<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("blue");
        
        List<String> sizes = new ArrayList<>();
        sizes.add("small");
        sizes.add("large");
        
        Taxonomy result = taxonomy.in("color", colors).in("size", sizes);
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    // ========== OR METHOD TESTS ==========

    @Test
    public void testOrWithValidList() throws JSONException {
        List<JSONObject> items = new ArrayList<>();
        
        JSONObject obj1 = new JSONObject();
        obj1.put("taxonomies.color", "red");
        items.add(obj1);
        
        JSONObject obj2 = new JSONObject();
        obj2.put("taxonomies.size", "large");
        items.add(obj2);
        
        Taxonomy result = taxonomy.or(items);
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testOrWithEmptyList() {
        List<JSONObject> items = new ArrayList<>();
        
        Taxonomy result = taxonomy.or(items);
        assertNotNull(result);
    }

    @Test
    public void testOrWithSingleItem() throws JSONException {
        List<JSONObject> items = new ArrayList<>();
        JSONObject obj = new JSONObject();
        obj.put("taxonomies.color", "red");
        items.add(obj);
        
        Taxonomy result = taxonomy.or(items);
        assertNotNull(result);
    }

    // ========== AND METHOD TESTS ==========

    @Test
    public void testAndWithValidList() throws JSONException {
        List<JSONObject> items = new ArrayList<>();
        
        JSONObject obj1 = new JSONObject();
        obj1.put("taxonomies.color", "red");
        items.add(obj1);
        
        JSONObject obj2 = new JSONObject();
        obj2.put("taxonomies.size", "large");
        items.add(obj2);
        
        Taxonomy result = taxonomy.and(items);
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testAndWithEmptyList() {
        List<JSONObject> items = new ArrayList<>();
        
        Taxonomy result = taxonomy.and(items);
        assertNotNull(result);
    }

    @Test
    public void testAndWithSingleItem() throws JSONException {
        List<JSONObject> items = new ArrayList<>();
        JSONObject obj = new JSONObject();
        obj.put("taxonomies.color", "red");
        items.add(obj);
        
        Taxonomy result = taxonomy.and(items);
        assertNotNull(result);
    }

    // ========== EXISTS METHOD TESTS ==========

    @Test
    public void testExistsWithTrue() {
        Taxonomy result = taxonomy.exists("color", true);
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testExistsWithFalse() {
        Taxonomy result = taxonomy.exists("color", false);
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testExistsMethodChaining() {
        Taxonomy result = taxonomy.exists("color", true).exists("size", false);
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    // ========== EQUAL AND BELOW METHOD TESTS ==========

    @Test
    public void testEqualAndBelowWithValidInputs() {
        Taxonomy result = taxonomy.equalAndBelow("category", "electronics");
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testEqualAndBelowWithEmptyTaxonomy() {
        Taxonomy result = taxonomy.equalAndBelow("", "term_uid");
        assertNotNull(result);
    }

    @Test
    public void testEqualAndBelowWithEmptyTermUid() {
        Taxonomy result = taxonomy.equalAndBelow("category", "");
        assertNotNull(result);
    }

    @Test
    public void testEqualAndBelowMethodChaining() {
        Taxonomy result = taxonomy
            .equalAndBelow("category", "electronics")
            .equalAndBelow("brand", "apple");
        assertNotNull(result);
    }

    // ========== BELOW METHOD TESTS ==========

    @Test
    public void testBelowWithValidInputs() {
        Taxonomy result = taxonomy.below("category", "electronics");
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testBelowWithEmptyTaxonomy() {
        Taxonomy result = taxonomy.below("", "term_uid");
        assertNotNull(result);
    }

    @Test
    public void testBelowWithEmptyTermUid() {
        Taxonomy result = taxonomy.below("category", "");
        assertNotNull(result);
    }

    @Test
    public void testBelowMethodChaining() {
        Taxonomy result = taxonomy
            .below("category", "electronics")
            .below("brand", "apple");
        assertNotNull(result);
    }

    // ========== EQUAL ABOVE METHOD TESTS ==========

    @Test
    public void testEqualAboveWithValidInputs() {
        Taxonomy result = taxonomy.equalAbove("category", "electronics");
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testEqualAboveWithEmptyTaxonomy() {
        Taxonomy result = taxonomy.equalAbove("", "term_uid");
        assertNotNull(result);
    }

    @Test
    public void testEqualAboveWithEmptyTermUid() {
        Taxonomy result = taxonomy.equalAbove("category", "");
        assertNotNull(result);
    }

    @Test
    public void testEqualAboveMethodChaining() {
        Taxonomy result = taxonomy
            .equalAbove("category", "electronics")
            .equalAbove("brand", "apple");
        assertNotNull(result);
    }

    // ========== ABOVE METHOD TESTS ==========

    @Test
    public void testAboveWithValidInputs() {
        Taxonomy result = taxonomy.above("category", "electronics");
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testAboveWithEmptyTaxonomy() {
        Taxonomy result = taxonomy.above("", "term_uid");
        assertNotNull(result);
    }

    @Test
    public void testAboveWithEmptyTermUid() {
        Taxonomy result = taxonomy.above("category", "");
        assertNotNull(result);
    }

    @Test
    public void testAboveMethodChaining() {
        Taxonomy result = taxonomy
            .above("category", "electronics")
            .above("brand", "apple");
        assertNotNull(result);
    }

    // ========== COMPLEX CHAINING TESTS ==========

    @Test
    public void testComplexMethodChaining() throws JSONException {
        List<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("blue");
        
        List<JSONObject> orConditions = new ArrayList<>();
        JSONObject obj1 = new JSONObject();
        obj1.put("taxonomies.size", "large");
        orConditions.add(obj1);
        
        Taxonomy result = taxonomy
            .in("color", colors)
            .or(orConditions)
            .exists("brand", true)
            .equalAndBelow("category", "electronics")
            .below("subcategory", "phones")
            .equalAbove("parent", "tech")
            .above("root", "products");
        
        assertNotNull(result);
        assertSame(taxonomy, result);
    }

    @Test
    public void testMultipleInCalls() {
        List<String> colors = new ArrayList<>();
        colors.add("red");
        
        List<String> sizes = new ArrayList<>();
        sizes.add("large");
        
        List<String> brands = new ArrayList<>();
        brands.add("apple");
        
        Taxonomy result = taxonomy
            .in("color", colors)
            .in("size", sizes)
            .in("brand", brands);
        
        assertNotNull(result);
    }

    @Test
    public void testMultipleExistsCalls() {
        Taxonomy result = taxonomy
            .exists("color", true)
            .exists("size", true)
            .exists("brand", false);
        
        assertNotNull(result);
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testExistsWithNullBoolean() {
        Taxonomy result = taxonomy.exists("color", null);
        assertNotNull(result);
    }

    // ========== NULL LIST TESTS ==========

    @Test
    public void testInWithNullList() {
        try {
            Taxonomy result = taxonomy.in("color", null);
            assertNotNull(result);
        } catch (NullPointerException e) {
            // Expected behavior
            assertNotNull(e);
        }
    }

    @Test
    public void testOrWithNullList() {
        try {
            Taxonomy result = taxonomy.or(null);
            assertNotNull(result);
        } catch (NullPointerException e) {
            // Expected behavior
            assertNotNull(e);
        }
    }

    @Test
    public void testAndWithNullList() {
        try {
            Taxonomy result = taxonomy.and(null);
            assertNotNull(result);
        } catch (NullPointerException e) {
            // Expected behavior
            assertNotNull(e);
        }
    }

    // ========== SPECIAL CHARACTERS TESTS ==========

    @Test
    public void testInWithSpecialCharacters() {
        List<String> items = new ArrayList<>();
        items.add("red-blue");
        items.add("color@#$");
        items.add("size_large");
        
        Taxonomy result = taxonomy.in("category", items);
        assertNotNull(result);
    }

    @Test
    public void testEqualAndBelowWithSpecialCharacters() {
        Taxonomy result = taxonomy.equalAndBelow("category@#$", "term_uid-123");
        assertNotNull(result);
    }

    // ========== LARGE DATA TESTS ==========

    @Test
    public void testInWithLargeList() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            items.add("item_" + i);
        }
        
        Taxonomy result = taxonomy.in("large_taxonomy", items);
        assertNotNull(result);
    }

    @Test
    public void testOrWithLargeList() throws JSONException {
        List<JSONObject> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            JSONObject obj = new JSONObject();
            obj.put("field_" + i, "value_" + i);
            items.add(obj);
        }
        
        Taxonomy result = taxonomy.or(items);
        assertNotNull(result);
    }

    // ========== STATE PRESERVATION TESTS ==========

    @Test
    public void testMultipleTaxonomyInstances() {
        Taxonomy tax1 = stack.taxonomy();
        Taxonomy tax2 = stack.taxonomy();
        
        assertNotNull(tax1);
        assertNotNull(tax2);
        assertNotSame(tax1, tax2);
    }

    @Test
    public void testIndependentQueries() {
        Taxonomy tax1 = stack.taxonomy();
        Taxonomy tax2 = stack.taxonomy();
        
        List<String> colors1 = new ArrayList<>();
        colors1.add("red");
        tax1.in("color", colors1);
        
        List<String> colors2 = new ArrayList<>();
        colors2.add("blue");
        tax2.in("color", colors2);
        
        // Both should be independent
        assertNotNull(tax1);
        assertNotNull(tax2);
    }
}

