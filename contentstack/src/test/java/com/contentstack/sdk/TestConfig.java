package com.contentstack.sdk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestConfig {

    private com.contentstack.sdk.Config config;

    @Before
    public void setUp() {
        config = new com.contentstack.sdk.Config();
    }

    @After
    public void tearDown() {
        config = null;
    }

    @Test
    public void testConfigCreation() {
        assertNotNull("Config should not be null", config);
        assertEquals("Default host should be cdn.contentstack.io", "cdn.contentstack.io", config.getHost());
        assertEquals("Default version should be v3", "v3", config.getVersion());
        assertEquals("Default protocol should be https://", "https://", config.PROTOCOL);
    }

    @Test
    public void testSetHost() {
        String customHost = "custom-cdn.contentstack.io";
        config.setHost(customHost);
        assertEquals("Host should be set correctly", customHost, config.getHost());
    }

    @Test
    public void testSetHostWithEmpty() {
        String originalHost = config.getHost();
        config.setHost("");
        assertEquals("Host should remain unchanged with empty string", originalHost, config.getHost());
    }

    @Test
    public void testSetHostWithNull() {
        String originalHost = config.getHost();
        config.setHost(null);
        assertEquals("Host should remain unchanged with null", originalHost, config.getHost());
    }

    @Test
    public void testSetEnvironment() {
        String environment = "production";
        config.setEnvironment(environment);
        assertEquals("Environment should be set correctly", environment, config.getEnvironment());
    }

    @Test
    public void testSetEnvironmentWithEmpty() {
        config.setEnvironment("");
        assertNull("Environment should be null with empty string", config.getEnvironment());
    }

    @Test
    public void testSetEnvironmentWithNull() {
        config.setEnvironment(null);
        assertNull("Environment should be null", config.getEnvironment());
    }

    @Test
    public void testSetBranch() {
        String branch = "development";
        config.setBranch(branch);
        assertEquals("Branch should be set correctly", branch, config.getBranch());
    }

    @Test
    public void testGetBranch() {
        String branch = "feature-branch";
        config.setBranch(branch);
        assertEquals("getBranch should return set branch", branch, config.getBranch());
    }

    @Test
    public void testSetRegionUS() {
        com.contentstack.sdk.Config.ContentstackRegion region = com.contentstack.sdk.Config.ContentstackRegion.US;
        config.setRegion(region);
        assertEquals("Region should be US", region, config.getRegion());
    }

    @Test
    public void testSetRegionEU() {
        com.contentstack.sdk.Config.ContentstackRegion region = com.contentstack.sdk.Config.ContentstackRegion.EU;
        config.setRegion(region);
        assertEquals("Region should be EU", region, config.getRegion());
    }

    @Test
    public void testSetRegionAU() {
        com.contentstack.sdk.Config.ContentstackRegion region = com.contentstack.sdk.Config.ContentstackRegion.AU;
        config.setRegion(region);
        assertEquals("Region should be AU", region, config.getRegion());
    }

    @Test
    public void testSetRegionAzureNA() {
        com.contentstack.sdk.Config.ContentstackRegion region = com.contentstack.sdk.Config.ContentstackRegion.AZURE_NA;
        config.setRegion(region);
        assertEquals("Region should be AZURE_NA", region, config.getRegion());
    }

    @Test
    public void testSetRegionAzureEU() {
        com.contentstack.sdk.Config.ContentstackRegion region = com.contentstack.sdk.Config.ContentstackRegion.AZURE_EU;
        config.setRegion(region);
        assertEquals("Region should be AZURE_EU", region, config.getRegion());
    }

    @Test
    public void testSetRegionGcpNA() {
        com.contentstack.sdk.Config.ContentstackRegion region = com.contentstack.sdk.Config.ContentstackRegion.GCP_NA;
        config.setRegion(region);
        assertEquals("Region should be GCP_NA", region, config.getRegion());
    }

    @Test
    public void testSetRegionGcpEU() {
        com.contentstack.sdk.Config.ContentstackRegion region = com.contentstack.sdk.Config.ContentstackRegion.GCP_EU;
        config.setRegion(region);
        assertEquals("Region should be GCP_EU", region, config.getRegion());
    }

    @Test
    public void testGetRegion() {
        assertEquals("Default region should be US", com.contentstack.sdk.Config.ContentstackRegion.US, config.getRegion());
    }

    @Test
    public void testSetProxy() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.example.com", 8080));
        config.setProxy(proxy);
        assertEquals("Proxy should be set correctly", proxy, config.getProxy());
    }

    @Test
    public void testGetProxy() {
        assertNull("Default proxy should be null", config.getProxy());
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.example.com", 8080));
        config.setProxy(proxy);
        assertNotNull("Proxy should not be null after setting", config.getProxy());
    }

    @Test
    public void testConnectionPool() {
        int maxIdleConnections = 10;
        long keepAliveDuration = 5;
        TimeUnit timeUnit = TimeUnit.MINUTES;
        
        ConnectionPool pool = config.connectionPool(maxIdleConnections, keepAliveDuration, timeUnit);
        assertNotNull("Connection pool should not be null", pool);
        assertEquals("Connection pool should be set", pool, config.connectionPool);
    }

    @Test
    public void testConnectionPoolWithDifferentTimeUnit() {
        ConnectionPool pool = config.connectionPool(5, 300, TimeUnit.SECONDS);
        assertNotNull("Connection pool should not be null", pool);
    }

    @Test
    public void testEarlyAccess() {
        String[] earlyAccess = {"feature1", "feature2"};
        config.earlyAccess(earlyAccess);
        assertArrayEquals("Early access should be set correctly", earlyAccess, config.getEarlyAccess());
    }

    @Test
    public void testGetEarlyAccess() {
        assertNull("Default early access should be null", config.getEarlyAccess());
        String[] earlyAccess = {"feature1"};
        config.earlyAccess(earlyAccess);
        assertNotNull("Early access should not be null after setting", config.getEarlyAccess());
    }

    @Test
    public void testGetHost() {
        assertEquals("Default host", "cdn.contentstack.io", config.getHost());
        config.setHost("new-host.com");
        assertEquals("Updated host", "new-host.com", config.getHost());
    }

    @Test
    public void testGetVersion() {
        assertEquals("Version should be v3", "v3", config.getVersion());
    }

    @Test
    public void testGetEnvironment() {
        assertNull("Default environment should be null", config.getEnvironment());
        config.setEnvironment("test-env");
        assertEquals("Environment should be test-env", "test-env", config.getEnvironment());
    }

    @Test
    public void testGetEndpoint() {
        config.setEndpoint("https://cdn.contentstack.io");
        String endpoint = config.getEndpoint();
        assertNotNull("Endpoint should not be null", endpoint);
        assertTrue("Endpoint should contain version", endpoint.contains("/v3/"));
    }

    @Test
    public void testSetEndpoint() {
        String endpoint = "https://custom-endpoint.com";
        config.setEndpoint(endpoint);
        String retrievedEndpoint = config.getEndpoint();
        assertTrue("Endpoint should start with custom endpoint", retrievedEndpoint.startsWith(endpoint));
    }

    @Test
    public void testMultipleConfigInstances() {
        com.contentstack.sdk.Config config1 = new com.contentstack.sdk.Config();
        com.contentstack.sdk.Config config2 = new com.contentstack.sdk.Config();
        
        config1.setHost("host1.com");
        config2.setHost("host2.com");
        
        assertEquals("Config1 host", "host1.com", config1.getHost());
        assertEquals("Config2 host", "host2.com", config2.getHost());
        assertNotEquals("Configs should be independent", config1.getHost(), config2.getHost());
    }

    @Test
    public void testConfigWithAllSettings() {
        config.setHost("custom-host.com");
        config.setEnvironment("production");
        config.setBranch("main");
        config.setRegion(com.contentstack.sdk.Config.ContentstackRegion.EU);
        config.earlyAccess(new String[]{"feature1", "feature2"});
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.com", 8080));
        config.setProxy(proxy);
        config.connectionPool(10, 5, TimeUnit.MINUTES);
        
        assertEquals("custom-host.com", config.getHost());
        assertEquals("production", config.getEnvironment());
        assertEquals("main", config.getBranch());
        assertEquals(com.contentstack.sdk.Config.ContentstackRegion.EU, config.getRegion());
        assertNotNull(config.getEarlyAccess());
        assertEquals(2, config.getEarlyAccess().length);
        assertNotNull(config.getProxy());
        assertNotNull(config.connectionPool);
    }
}

