package com.contentstack.sdk;

import okhttp3.Request;
import okhttp3.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TaxonomyTestCase {

    private final Stack stack;

    {
        try {
            stack = TestCred.stack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testInstance() {
        Assertions.assertNotNull(stack);
    }

    @Test
    void operationIn() {
        Taxonomy taxonomy = stack.taxonomy();
        List<String> listOfItems = new ArrayList<>();
        listOfItems.add("red");
        listOfItems.add("yellow");
        Request req = taxonomy.in("taxonomies.color", listOfItems).makeRequest().request();
        //Assertions.assertEquals(3, req.headers().size());
        Assertions.assertEquals("GET", req.method());
        Assertions.assertEquals("cdn.contentstack.io", req.url().host());
        Assertions.assertEquals("/v3/taxonomies/entries", req.url().encodedPath());
        Assertions.assertEquals("query={\"taxonomies.color\":{\"$in\":[\"red\",\"yellow\"]}}", req.url().query());
    }


    @Test
    void operationOr() throws JSONException {

//        query={ $or: [
//        { "taxonomies.taxonomy_uid_1" : "term_uid1" },
//        { "taxonomies.taxonomy_uid_2" : "term_uid2" }
//        ]}

        Taxonomy taxonomy = stack.taxonomy();

        List<JSONObject> listOfItems = new ArrayList<>();
        JSONObject item1 = new JSONObject();
        item1.put("taxonomies.color", "yellow");
        JSONObject item2 = new JSONObject();
        item2.put("taxonomies.size", "small");
        listOfItems.add(item1);
        listOfItems.add(item2);
        taxonomy.or(listOfItems);
        Request req = taxonomy.makeRequest().request();

        Assertions.assertEquals("query={\"$or\":[{\"taxonomies.color\":\"yellow\"},{\"taxonomies.size\":\"small\"}]}", req.url().query());

    }

    @Test
    void operatorAnd() throws JSONException {
        Taxonomy taxonomy = stack.taxonomy();
        List<JSONObject> listOfItems = new ArrayList<>();
        JSONObject items1 = new JSONObject();
        items1.put("taxonomies.color", "green");
        JSONObject items2 = new JSONObject();
        items2.put("taxonomies.computers", "laptop");
        listOfItems.add(items1);
        listOfItems.add(items2);
        taxonomy.and(listOfItems);

        // {$and: [{"taxonomies.color" : "green" }, { "taxonomies.computers" : "laptop" }]}
        Request req = taxonomy.makeRequest().request();
        Assertions.assertEquals("query={\"$and\":\"[{\\\"taxonomies.color\\\":\\\"green\\\"}, {\\\"taxonomies.computers\\\":\\\"laptop\\\"}]\"}", req.url().query());
    }


    @Test
    void operationExists() {
        // create instance of taxonomy
        Taxonomy taxonomy = stack.taxonomy().exists("taxonomies.color", true);
        Request req = taxonomy.makeRequest().request();
        //{"taxonomies.color" : { "$exists": true }}
        //{"taxonomies.color":{"$exists":true}}
        Assertions.assertEquals("query={\"taxonomies.color\":{\"$exists\":true}}", req.url().query());
    }


    @Test
    void operationEqualAndBelow() {
        // create instance of taxonomy
        Taxonomy taxonomy = stack.taxonomy().equalAndBelow("taxonomies.color", "blue");
        Request req = taxonomy.makeRequest().request();
        // {"taxonomies.color" : { "$eq_below": "blue" }}
        Assertions.assertEquals("query={\"taxonomies.color\":{\"$eq_below\":\"blue\"}}", req.url().query());
    }


    @Test
    void operationBelowWithLevel() {
        Taxonomy taxonomy = stack.taxonomy().equalAndBelowWithLevel("taxonomies.color", "blue", 3);
        Request req = taxonomy.makeRequest().request();
        Assertions.assertEquals("query={\"taxonomies.color\":{\"$eq_below\":\"blue, level: 3\"}}", req.url().query());

    }


    @Test
    void operationEqualAbove() {
        Taxonomy taxonomy = stack.taxonomy().equalAbove("taxonomies.appliances", "led");
        Request req = taxonomy.makeRequest().request();
        Assertions.assertEquals("query={\"taxonomies.appliances\":{\"$eq_above\":\"led\"}}", req.url().query());

    }


    @Test
    void above() {
        Taxonomy taxonomy = stack.taxonomy().above("taxonomies.appliances", "led");
        Request req = taxonomy.makeRequest().request();
        Assertions.assertEquals("query={\"taxonomies.appliances\":{\"$above\":\"led\"}}", req.url().query());
    }

    @Test
    void aboveAPI() {
        Taxonomy taxonomy = stack.taxonomy().above("taxonomies.appliances", "led");
        taxonomy.find((response, error) -> {
            System.out.println("Successful: " + response);
            System.out.println("Error: " + error.errorMessage);
        });
        //Assertions.assertEquals("query={\"taxonomies.appliances\":{\"$above\":\"led\"}}", req.url().query());
    }


}

