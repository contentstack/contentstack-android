package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runners.MethodSorters;
import org.junit.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.*;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class TaxonomyTestCase {

    private static Stack stack;
    private final static String TAG = TaxonomyTestCase.class.getSimpleName();


    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, BuildConfig.APIKey, BuildConfig.deliveryToken, BuildConfig.environment, config);
    }

    @Test
    public void testInstance() {
        assertNotNull(stack);
    }

    @Test
    public void operationIn() {
        Taxonomy taxonomy = stack.taxonomy();
        List<String> listOfItems = new ArrayList<>();
        listOfItems.add("maroon");
        listOfItems.add("red");
        Request req = taxonomy.in("taxonomies.color", listOfItems).makeRequest().request();
        assertEquals("GET", req.method());
        assertEquals("cdn.contentstack.io", req.url().host());
        assertEquals("/v3/taxonomies/entries", req.url().encodedPath());
        assertEquals("query={\"taxonomies.color\":{\"$in\":[\"maroon\",\"red\"]}}", req.url().query());
    }

    @Test
    public void operationOr() throws JSONException, IOException {
//        query={ $or: [
//        { "taxonomies.taxonomy_uid_1" : "term_uid1" },
//        { "taxonomies.taxonomy_uid_2" : "term_uid2" }
//        ]}
        Taxonomy taxonomy = stack.taxonomy();
        List<JSONObject> listOfItems = new ArrayList<>();
        JSONObject item1 = new JSONObject();
        item1.put("taxonomies.color", "orange");
        JSONObject item2 = new JSONObject();
        item2.put("taxonomies.country", "zambia");
        listOfItems.add(item1);
        listOfItems.add(item2);
        taxonomy.or(listOfItems);
        Request req = taxonomy.makeRequest().request();
        assertEquals("query={\"$or\":[{\"taxonomies.color\":\"orange\"},{\"taxonomies.country\":\"zambia\"}]}", req.url().query());

    }

    @Test
    public void operatorAnd() throws JSONException {
        Taxonomy taxonomy = stack.taxonomy();
        List<JSONObject> listOfItems = new ArrayList<>();
        JSONObject items1 = new JSONObject();
        items1.put("taxonomies.color", "green");
        JSONObject items2 = new JSONObject();
        items2.put("taxonomies.country", "india");
        listOfItems.add(items1);
        listOfItems.add(items2);
        taxonomy.and(listOfItems);
        Request req = taxonomy.makeRequest().request();
        assertEquals("query={\"$and\":[{\"taxonomies.color\":\"green\"},{\"taxonomies.country\":\"india\"}]}", req.url().query());
    }


    @Test
    public void operationExists() throws IOException {
        Taxonomy taxonomy = stack.taxonomy().exists("taxonomies.color", true);
        Request req = taxonomy.makeRequest().request();
        assertEquals("query={\"taxonomies.color\":{\"$exists\":true}}", req.url().query());
    }


    @Test
    public void operationEqualAndBelow() throws IOException {
        Taxonomy taxonomy = stack.taxonomy().equalAndBelow("taxonomies.color", "red");
        Request req = taxonomy.makeRequest().request();
        assertEquals("query={\"taxonomies.color\":{\"$eq_below\":\"red\"}}", req.url().query());
    }


    @Test
    public void operationEqualAbove() {
        Taxonomy taxonomy = stack.taxonomy().equalAbove("taxonomies.appliances", "led");
        Request req = taxonomy.makeRequest().request();
        assertEquals("query={\"taxonomies.appliances\":{\"$eq_above\":\"led\"}}", req.url().query());

    }

    @Test
    public void above() {
        Taxonomy taxonomy = stack.taxonomy().above("taxonomies.appliances", "led");
        Request req = taxonomy.makeRequest().request();
        assertEquals("query={\"taxonomies.appliances\":{\"$above\":\"led\"}}", req.url().query());
    }

    @Test
    public void below() {
        Taxonomy taxonomy = stack.taxonomy().below("taxonomies.appliances", "TV");
        Request req = taxonomy.makeRequest().request();
        assertEquals("query={\"taxonomies.appliances\":{\"$below\":\"TV\"}}", req.url().query());
    }

    @Test
    public void aboveAPI() {
        Taxonomy taxonomy = stack.taxonomy().below("taxonomies.color", "red");
        Request req = taxonomy.makeRequest().request();
        taxonomy.find(new TaxonomyCallback() {
            @Override
            public void onResponse(JSONObject response, Error error) {
            }
        });
        assertEquals("query={\"taxonomies.color\":{\"$below\":\"red\"}}", req.url().query());
    }


}

