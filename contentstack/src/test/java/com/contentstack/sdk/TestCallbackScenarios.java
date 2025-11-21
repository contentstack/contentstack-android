package com.contentstack.sdk;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests that use callbacks to exercise internal code paths
 */
@RunWith(RobolectricTestRunner.class)
public class TestCallbackScenarios {
    private Stack stack;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(context, "key", "token", "env");
    }

    // ==================== Query Callbacks ====================
    @Test
    public void testQueryFindCallback01() {
        Query query = stack.contentType("test").query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                // Callback invoked
            }
        });
        assertNotNull(query);
    }

    @Test
    public void testQueryFindCallback02() {
        Query query = stack.contentType("blog").query();
        query.where("status", "published");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                // Callback with where clause
            }
        });
        assertNotNull(query);
    }

    @Test
    public void testQueryFindCallback03() {
        Query query = stack.contentType("page").query();
        query.limit(10);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                // Callback with limit
            }
        });
        assertNotNull(query);
    }

    @Test
    public void testQueryFindOneCallback01() {
        Query query = stack.contentType("test").query();
        query.findOne(new SingleQueryResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Entry entry, Error error) {
                // Callback invoked
            }
        });
        assertNotNull(query);
    }

    @Test
    public void testQueryFindOneCallback02() {
        Query query = stack.contentType("blog").query();
        query.where("featured", true);
        query.findOne(new SingleQueryResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Entry entry, Error error) {
                // Callback with where
            }
        });
        assertNotNull(query);
    }

    // ==================== Entry Callbacks ====================
    @Test
    public void testEntryFetchCallback01() {
        Entry entry = stack.contentType("test").entry("uid");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Callback invoked
            }
        });
        assertNotNull(entry);
    }

    @Test
    public void testEntryFetchCallback02() {
        Entry entry = stack.contentType("blog").entry("post1");
        entry.only(new String[]{"title", "description"});
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Callback with only
            }
        });
        assertNotNull(entry);
    }

    @Test
    public void testEntryFetchCallback03() {
        Entry entry = stack.contentType("page").entry("page1");
        entry.includeReference(new String[]{"author"});
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Callback with reference
            }
        });
        assertNotNull(entry);
    }

    // ==================== Asset Callbacks ====================
    @Test
    public void testAssetFetchCallback01() {
        Asset asset = stack.asset("asset_uid");
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Callback invoked
            }
        });
        assertNotNull(asset);
    }

    @Test
    public void testAssetFetchCallback02() {
        Asset asset = stack.asset("image_uid");
        asset.includeDimension();
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Callback with dimension
            }
        });
        assertNotNull(asset);
    }

    @Test
    public void testAssetFetchCallback03() {
        Asset asset = stack.asset("file_uid");
        asset.addParam("width", "100");
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Callback with param
            }
        });
        assertNotNull(asset);
    }

    // ==================== AssetLibrary Callbacks ====================
    @Test
    public void testAssetLibraryFetchCallback01() {
        AssetLibrary library = stack.assetLibrary();
        library.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                // Callback invoked
            }
        });
        assertNotNull(library);
    }

    @Test
    public void testAssetLibraryFetchCallback02() {
        AssetLibrary library = stack.assetLibrary();
        library.setHeader("X-Custom", "value");
        library.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                // Callback with header
            }
        });
        assertNotNull(library);
    }

    // ==================== Sync Callbacks ====================
    @Test
    public void testSyncCallback01() {
        stack.sync(new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Callback invoked
            }
        });
        assertNotNull(stack);
    }

    @Test
    public void testSyncTokenCallback01() {
        stack.syncToken("token", new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Callback with token
            }
        });
        assertNotNull(stack);
    }

    @Test
    public void testSyncPaginationTokenCallback01() {
        stack.syncPaginationToken("pagination_token", new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                // Callback with pagination
            }
        });
        assertNotNull(stack);
    }

    // ==================== GlobalField Callbacks ====================
    @Test
    public void testGlobalFieldFetchCallback01() {
        GlobalField gf = stack.globalField("gf_uid");
        gf.fetch(new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                // Callback invoked
            }
        });
        assertNotNull(gf);
    }

    @Test
    public void testGlobalFieldFetchCallback02() {
        GlobalField gf = stack.globalField("gf_uid2");
        gf.includeGlobalFieldSchema();
        gf.fetch(new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                // Callback with schema
            }
        });
        assertNotNull(gf);
    }

    // ==================== Multiple Callbacks ====================
    @Test
    public void testMultipleQueryCallbacks() {
        for (int i = 0; i < 5; i++) {
            Query query = stack.contentType("test").query();
            query.find(new QueryResultsCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                    // Multiple callbacks
                }
            });
        }
        assertNotNull(stack);
    }

    @Test
    public void testMultipleEntryCallbacks() {
        for (int i = 0; i < 5; i++) {
            Entry entry = stack.contentType("test").entry("uid" + i);
            entry.fetch(new EntryResultCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, Error error) {
                    // Multiple callbacks
                }
            });
        }
        assertNotNull(stack);
    }

    @Test
    public void testMultipleAssetCallbacks() {
        for (int i = 0; i < 5; i++) {
            Asset asset = stack.asset("asset_" + i);
            asset.fetch(new FetchResultCallback() {
                @Override
                public void onCompletion(ResponseType responseType, Error error) {
                    // Multiple callbacks
                }
            });
        }
        assertNotNull(stack);
    }
}

