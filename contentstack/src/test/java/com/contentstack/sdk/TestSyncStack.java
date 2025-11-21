package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestSyncStack {

    @Test
    public void testSetJSON_withAllFields() throws Exception {
        // Build JSON with all properties
        JSONObject json = new JSONObject();
        json.put("skip", 5);
        json.put("total_count", 100);
        json.put("limit", 20);
        json.put("pagination_token", "page_token_value");
        json.put("sync_token", "sync_token_value");

        JSONArray items = new JSONArray();
        JSONObject item1 = new JSONObject();
        item1.put("uid", "1");
        items.put(item1);
        JSONObject item2 = new JSONObject();
        item2.put("uid", "2");
        items.put(item2);
        json.put("items", items);

        SyncStack syncStack = new SyncStack();
        syncStack.setJSON(json);

        // URL should be set to empty string in setJSON
        assertEquals("", syncStack.getURL());

        // verify basic numeric fields
        assertEquals(5, syncStack.getSkip());
        assertEquals(100, syncStack.getCount());
        assertEquals(20, syncStack.getLimit());

        // verify tokens
        assertEquals("page_token_value", syncStack.getPaginationToken());
        assertEquals("sync_token_value", syncStack.getSyncToken());

        // verify items
        ArrayList<JSONObject> resultItems = syncStack.getItems();
        assertNotNull(resultItems);
        assertEquals(2, resultItems.size());
        assertEquals("1", resultItems.get(0).optString("uid"));
        assertEquals("2", resultItems.get(1).optString("uid"));

        // verify JSON response stored
        assertNotNull(syncStack.getJSONResponse());
        assertEquals(json.toString(), syncStack.getJSONResponse().toString());
    }

    @Test
    public void testSetJSON_onlyPaginationToken() throws Exception {
        JSONObject json = new JSONObject();
        json.put("skip", 0);
        json.put("total_count", 10);
        json.put("limit", 10);
        json.put("pagination_token", "only_pagination");

        SyncStack syncStack = new SyncStack();
        syncStack.setJSON(json);

        assertEquals(0, syncStack.getSkip());
        assertEquals(10, syncStack.getCount());
        assertEquals(10, syncStack.getLimit());
        assertEquals("only_pagination", syncStack.getPaginationToken());
        // because has("sync_token") == false, sync_token should be null
        assertNull(syncStack.getSyncToken());
    }

    @Test
    public void testSetJSON_onlySyncToken() throws Exception {
        JSONObject json = new JSONObject();
        json.put("skip", 1);
        json.put("total_count", 5);
        json.put("limit", 5);
        json.put("sync_token", "only_sync");

        SyncStack syncStack = new SyncStack();
        syncStack.setJSON(json);

        assertEquals(1, syncStack.getSkip());
        assertEquals(5, syncStack.getCount());
        assertEquals(5, syncStack.getLimit());

        // no pagination_token present
        assertNull(syncStack.getPaginationToken());
        assertEquals("only_sync", syncStack.getSyncToken());
    }

    @Test
    public void testSetJSON_nullDoesNothing() {
        SyncStack syncStack = new SyncStack();
        // should simply not throw and not change fields
        syncStack.setJSON(null);

        // all getters should remain default (null / 0)
        assertNull(syncStack.getJSONResponse());
        assertNull(syncStack.getURL());
        assertEquals(0, syncStack.getSkip());
        assertEquals(0, syncStack.getCount());
        assertEquals(0, syncStack.getLimit());
        assertNull(syncStack.getPaginationToken());
        assertNull(syncStack.getSyncToken());
        assertNull(syncStack.getItems());
    }
}
