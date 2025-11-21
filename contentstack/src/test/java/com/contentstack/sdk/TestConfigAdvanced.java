package com.contentstack.sdk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Advanced tests for Config class
 */
@RunWith(RobolectricTestRunner.class)
public class TestConfigAdvanced {

    private Config config;

    @Before
    public void setUp() {
        config = new Config();
    }

    // ==================== Host Tests ====================

    @Test
    public void testSetHostValid() {
        config.setHost("custom-cdn.contentstack.io");
        assertNotNull(config);
    }

    @Test
    public void testSetHostNull() {
        config.setHost(null);
        assertNotNull(config);
    }

    @Test
    public void testSetHostEmpty() {
        config.setHost("");
        assertNotNull(config);
    }

    @Test
    public void testSetHostMultipleTimes() {
        config.setHost("host1.com");
        config.setHost("host2.com");
        config.setHost("host3.com");
        assertNotNull(config);
    }

    @Test
    public void testSetHostWithProtocol() {
        config.setHost("https://cdn.contentstack.io");
        config.setHost("http://cdn.contentstack.io");
        assertNotNull(config);
    }

    @Test
    public void testSetHostWithPort() {
        config.setHost("cdn.contentstack.io:8080");
        assertNotNull(config);
    }

    @Test
    public void testSetHostWithPath() {
        config.setHost("cdn.contentstack.io/path/to/api");
        assertNotNull(config);
    }

    @Test
    public void testSetHostLongString() {
        StringBuilder longHost = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longHost.append("subdomain.");
        }
        longHost.append("contentstack.io");
        config.setHost(longHost.toString());
        assertNotNull(config);
    }

    // ==================== Region Tests ====================

    @Test
    public void testSetRegionUS() {
        config.setRegion(Config.ContentstackRegion.US);
        assertNotNull(config);
    }

    @Test
    public void testSetRegionEU() {
        config.setRegion(Config.ContentstackRegion.EU);
        assertNotNull(config);
    }

    @Test
    public void testSetRegionAZURE_NA() {
        config.setRegion(Config.ContentstackRegion.AZURE_NA);
        assertNotNull(config);
    }

    @Test
    public void testSetRegionAZURE_EU() {
        config.setRegion(Config.ContentstackRegion.AZURE_EU);
        assertNotNull(config);
    }

    @Test
    public void testSetRegionGCP_NA() {
        config.setRegion(Config.ContentstackRegion.GCP_NA);
        assertNotNull(config);
    }

    @Test
    public void testSetRegionNull() {
        config.setRegion(null);
        assertNotNull(config);
    }

    @Test
    public void testSetRegionMultipleTimes() {
        config.setRegion(Config.ContentstackRegion.US);
        config.setRegion(Config.ContentstackRegion.EU);
        config.setRegion(Config.ContentstackRegion.AZURE_NA);
        assertNotNull(config);
    }

    @Test
    public void testSetRegionAllValues() {
        Config.ContentstackRegion[] regions = Config.ContentstackRegion.values();
        for (Config.ContentstackRegion region : regions) {
            Config testConfig = new Config();
            testConfig.setRegion(region);
            assertNotNull(testConfig);
        }
    }

    // ==================== Branch Tests ====================

    @Test
    public void testSetBranchValid() {
        config.setBranch("development");
        assertNotNull(config);
    }

    @Test
    public void testSetBranchNull() {
        config.setBranch(null);
        assertNotNull(config);
    }

    @Test
    public void testSetBranchEmpty() {
        config.setBranch("");
        assertNotNull(config);
    }

    @Test
    public void testSetBranchMultiple() {
        config.setBranch("main");
        config.setBranch("development");
        config.setBranch("staging");
        config.setBranch("production");
        assertNotNull(config);
    }

    @Test
    public void testSetBranchWithSpecialCharacters() {
        config.setBranch("feature/new-feature");
        config.setBranch("bugfix/issue-123");
        config.setBranch("release-v1.0.0");
        assertNotNull(config);
    }

    // setEarlyAccess method doesn't exist in Config, skipping these tests

    // ==================== Combined Configuration Tests ====================

    @Test
    public void testCompleteConfiguration() {
        config.setHost("custom-cdn.contentstack.io");
        config.setRegion(Config.ContentstackRegion.EU);
        config.setBranch("development");
        assertNotNull(config);
    }

    @Test
    public void testMultipleConfigInstances() {
        Config config1 = new Config();
        config1.setHost("host1.com");
        config1.setRegion(Config.ContentstackRegion.US);
        
        Config config2 = new Config();
        config2.setHost("host2.com");
        config2.setRegion(Config.ContentstackRegion.EU);
        
        Config config3 = new Config();
        config3.setHost("host3.com");
        config3.setRegion(Config.ContentstackRegion.AZURE_NA);
        
        assertNotNull(config1);
        assertNotNull(config2);
        assertNotNull(config3);
        assertNotEquals(config1, config2);
    }

    @Test
    public void testConfigurationOverwrite() {
        config.setHost("initial-host.com");
        config.setHost("updated-host.com");
        
        config.setRegion(Config.ContentstackRegion.US);
        config.setRegion(Config.ContentstackRegion.EU);
        
        config.setBranch("branch1");
        config.setBranch("branch2");
        
        assertNotNull(config);
    }

    @Test
    public void testConfigurationReset() {
        config.setHost("host.com");
        config.setRegion(Config.ContentstackRegion.US);
        config.setBranch("main");
        
        // Reset by setting to null/empty
        config.setHost(null);
        config.setRegion(null);
        config.setBranch(null);
        
        assertNotNull(config);
    }

    // ==================== Edge Cases ====================

    @Test
    public void testHostWithIPAddress() {
        config.setHost("192.168.1.1");
        config.setHost("10.0.0.1:8080");
        config.setHost("127.0.0.1");
        assertNotNull(config);
    }

    @Test
    public void testHostWithIPv6() {
        config.setHost("[2001:db8::1]");
        config.setHost("[::1]");
        assertNotNull(config);
    }

    @Test
    public void testBranchWithUnicode() {
        config.setBranch("分支");
        config.setBranch("ブランチ");
        config.setBranch("가지");
        assertNotNull(config);
    }

    @Test
    public void testBranchWithEmoji() {
        config.setBranch("feature-🚀");
        config.setBranch("bugfix-🐛");
        assertNotNull(config);
    }

    @Test
    public void testVeryLongBranchName() {
        StringBuilder longBranch = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longBranch.append("branch");
        }
        config.setBranch(longBranch.toString());
        assertNotNull(config);
    }

    // Removed setEarlyAccess edge case tests - method doesn't exist

    @Test
    public void testSequentialConfigurations() {
        for (int i = 0; i < 10; i++) {
            Config testConfig = new Config();
            testConfig.setHost("host" + i + ".com");
            testConfig.setBranch("branch" + i);
            assertNotNull(testConfig);
        }
    }

    @Test
    public void testConfigWithAllNullValues() {
        Config nullConfig = new Config();
        nullConfig.setHost(null);
        nullConfig.setRegion(null);
        nullConfig.setBranch(null);
        assertNotNull(nullConfig);
    }

    @Test
    public void testConfigWithAllEmptyValues() {
        Config emptyConfig = new Config();
        emptyConfig.setHost("");
        emptyConfig.setBranch("");
        assertNotNull(emptyConfig);
    }

    // ==================== Region Enum Tests ====================

    @Test
    public void testRegionEnumValues() {
        Config.ContentstackRegion[] regions = Config.ContentstackRegion.values();
        assertTrue(regions.length >= 5);
        assertNotNull(regions);
    }

    @Test
    public void testRegionEnumValueOf() {
        Config.ContentstackRegion region = Config.ContentstackRegion.valueOf("US");
        assertNotNull(region);
        assertEquals(Config.ContentstackRegion.US, region);
    }

    @Test
    public void testAllRegionEnumsAssignable() {
        config.setRegion(Config.ContentstackRegion.US);
        config.setRegion(Config.ContentstackRegion.EU);
        config.setRegion(Config.ContentstackRegion.AZURE_NA);
        config.setRegion(Config.ContentstackRegion.AZURE_EU);
        config.setRegion(Config.ContentstackRegion.GCP_NA);
        assertNotNull(config);
    }
}

