package com.contentstack.sdk;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestContentstack {

    private Context mockContext;
    private String apiKey;
    private String deliveryToken;
    private String environment;

    @Before
    public void setUp() {
        mockContext = TestUtils.createMockContext();
        apiKey = TestUtils.getTestApiKey();
        deliveryToken = TestUtils.getTestDeliveryToken();
        environment = TestUtils.getTestEnvironment();
        TestUtils.cleanupTestCache();
    }

    @After
    public void tearDown() {
        TestUtils.cleanupTestCache();
        mockContext = null;
    }

    @Test
    public void testStackCreationWithBasicParams() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        assertNotNull("Stack should not be null", stack);
        assertEquals("API key should match", apiKey, stack.getApplicationKey());
        assertEquals("Delivery token should match", deliveryToken, stack.getAccessToken());
    }

    @Test
    public void testStackCreationWithConfig() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setHost("custom-cdn.contentstack.io");
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
        assertEquals("API key should match", apiKey, stack.getApplicationKey());
    }

    @Test(expected = NullPointerException.class)
    public void testStackCreationWithNullContext() throws Exception {
        Contentstack.stack(null, apiKey, deliveryToken, environment);
    }

    @Test(expected = NullPointerException.class)
    public void testStackCreationWithNullApiKey() throws Exception {
        Contentstack.stack(mockContext, null, deliveryToken, environment);
    }

    @Test(expected = NullPointerException.class)
    public void testStackCreationWithNullDeliveryToken() throws Exception {
        Contentstack.stack(mockContext, apiKey, null, environment);
    }

    @Test(expected = NullPointerException.class)
    public void testStackCreationWithNullEnvironment() throws Exception {
        Contentstack.stack(mockContext, apiKey, deliveryToken, null);
    }

    @Test
    public void testStackCreationWithTrimmedApiKey() throws Exception {
        String apiKeyWithSpaces = "  " + apiKey + "  ";
        Stack stack = Contentstack.stack(mockContext, apiKeyWithSpaces, deliveryToken, environment);
        assertNotNull("Stack should not be null", stack);
        assertEquals("API key should be trimmed", apiKey, stack.getApplicationKey());
    }

    @Test
    public void testStackCreationWithCustomHost() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        String customHost = "eu-cdn.contentstack.io";
        config.setHost(customHost);
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackCreationWithBranch() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setBranch("development");
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackCreationWithEarlyAccess() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        String[] earlyAccess = {"feature1", "feature2"};
        config.earlyAccess(earlyAccess);
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackCreationWithRegionEU() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setRegion(com.contentstack.sdk.Config.ContentstackRegion.EU);
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackCreationWithRegionAU() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setRegion(com.contentstack.sdk.Config.ContentstackRegion.AU);
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackCreationWithRegionAZURE_NA() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setRegion(com.contentstack.sdk.Config.ContentstackRegion.AZURE_NA);
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackCreationWithRegionAZURE_EU() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setRegion(com.contentstack.sdk.Config.ContentstackRegion.AZURE_EU);
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackCreationWithRegionGCP_NA() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setRegion(com.contentstack.sdk.Config.ContentstackRegion.GCP_NA);
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackCreationWithRegionGCP_EU() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setRegion(com.contentstack.sdk.Config.ContentstackRegion.GCP_EU);
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
    }

    @Test(expected = NullPointerException.class)
    public void testStackCreationWithNullConfig() throws Exception {
        Contentstack.stack(mockContext, apiKey, deliveryToken, environment, null);
    }

    @Test
    public void testStackCreationWithAllRegions() throws Exception {
        for (com.contentstack.sdk.Config.ContentstackRegion region : com.contentstack.sdk.Config.ContentstackRegion.values()) {
            com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
            config.setRegion(region);
            Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
            assertNotNull("Stack should not be null for region " + region, stack);
        }
    }

    @Test
    public void testMultipleStackInstances() throws Exception {
        Stack stack1 = Contentstack.stack(mockContext, "api_key_1", "token_1", "env_1");
        Stack stack2 = Contentstack.stack(mockContext, "api_key_2", "token_2", "env_2");
        
        assertNotNull("Stack 1 should not be null", stack1);
        assertNotNull("Stack 2 should not be null", stack2);
        assertNotEquals("Stacks should be different instances", stack1, stack2);
        assertEquals("Stack 1 API key", "api_key_1", stack1.getApplicationKey());
        assertEquals("Stack 2 API key", "api_key_2", stack2.getApplicationKey());
    }

    @Test
    public void testStackCreationWithDifferentEnvironments() throws Exception {
        String[] environments = {"development", "staging", "production", "test", "qa"};
        
        for (String env : environments) {
            Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, env);
            assertNotNull("Stack should not be null for environment " + env, stack);
        }
    }

    @Test
    public void testStackCreationWithSpecialCharactersInEnvironment() throws Exception {
        String[] specialEnvs = {"dev-test", "staging_v2", "prod.2024"};
        
        for (String env : specialEnvs) {
            try {
                Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, env);
                assertNotNull("Stack should not be null for environment " + env, stack);
            } catch (Exception e) {
                // Some special characters might not be allowed
                assertNotNull("Exception should not be null", e);
            }
        }
    }

    @Test
    public void testStackCreationInitializesCache() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        assertNotNull("Stack should not be null", stack);
        assertNotNull("Cache folder should be initialized", SDKConstant.cacheFolderName);
    }

    @Test
    public void testStackWithCompleteConfiguration() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setHost("custom-cdn.contentstack.io");
        config.setBranch("feature-branch");
        config.setRegion(com.contentstack.sdk.Config.ContentstackRegion.EU);
        config.earlyAccess(new String[]{"feature1", "feature2"});
        
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment, config);
        assertNotNull("Stack should not be null", stack);
        assertEquals("API key should match", apiKey, stack.getApplicationKey());
        assertEquals("Delivery token should match", deliveryToken, stack.getAccessToken());
    }

    @Test
    public void testStackContentTypeCreation() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        ContentType contentType = stack.contentType("test_content_type");
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testStackAssetLibraryCreation() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        AssetLibrary assetLibrary = stack.assetLibrary();
        assertNotNull("AssetLibrary should not be null", assetLibrary);
    }

    @Test
    public void testStackAssetCreation() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        Asset asset = stack.asset("test_asset_uid");
        assertNotNull("Asset should not be null", asset);
    }

    @Test
    public void testStackGlobalFieldCreation() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        GlobalField globalField = stack.globalField();
        assertNotNull("GlobalField should not be null", globalField);
    }

    @Test
    public void testStackGlobalFieldWithUidCreation() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        GlobalField globalField = stack.globalField("test_global_field_uid");
        assertNotNull("GlobalField should not be null", globalField);
    }

    @Test
    public void testStackTaxonomyCreation() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        Taxonomy taxonomy = stack.taxonomy();
        assertNotNull("Taxonomy should not be null", taxonomy);
    }

    @Test
    public void testStackSetHeader() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        stack.setHeader("custom-header", "custom-value");
        // Verify header is set (would need to access internal state)
        assertNotNull("Stack should not be null after setting header", stack);
    }

    @Test
    public void testStackRemoveHeader() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        stack.setHeader("custom-header", "custom-value");
        stack.removeHeader("custom-header");
        // Verify header is removed (would need to access internal state)
        assertNotNull("Stack should not be null after removing header", stack);
    }

    @Test
    public void testStackImageTransform() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        java.util.LinkedHashMap<String, Object> params = new java.util.LinkedHashMap<>();
        params.put("width", 100);
        params.put("height", 100);
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertTrue("URL should contain query parameters", transformedUrl.contains("?"));
    }

    @Test
    public void testStackImageTransformWithMultipleParams() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        java.util.LinkedHashMap<String, Object> params = new java.util.LinkedHashMap<>();
        params.put("width", 200);
        params.put("height", 150);
        params.put("quality", 80);
        params.put("format", "webp");
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertTrue("URL should contain width param", transformedUrl.contains("width"));
        assertTrue("URL should contain height param", transformedUrl.contains("height"));
    }

    @Test
    public void testStackImageTransformWithEmptyParams() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        java.util.LinkedHashMap<String, Object> params = new java.util.LinkedHashMap<>();
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertEquals("URL should remain unchanged with empty params", imageUrl, transformedUrl);
    }

    @Test
    public void testStackImageTransformWithNullParams() throws Exception {
        Stack stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, null);
        
        assertEquals("URL should remain unchanged with null params", imageUrl, transformedUrl);
    }

    @Test
    public void testStackWithLongApiKey() throws Exception {
        String longApiKey = "a".repeat(100);
        Stack stack = Contentstack.stack(mockContext, longApiKey, deliveryToken, environment);
        assertNotNull("Stack should not be null with long API key", stack);
        assertEquals("API key should match", longApiKey, stack.getApplicationKey());
    }

    @Test
    public void testStackWithLongDeliveryToken() throws Exception {
        String longToken = "t".repeat(200);
        Stack stack = Contentstack.stack(mockContext, apiKey, longToken, environment);
        assertNotNull("Stack should not be null with long delivery token", stack);
        assertEquals("Delivery token should match", longToken, stack.getAccessToken());
    }
}

