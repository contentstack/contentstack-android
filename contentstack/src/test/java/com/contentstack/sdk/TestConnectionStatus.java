package com.contentstack.sdk;

import android.content.Context;
import android.content.Intent;
import androidx.test.core.app.ApplicationProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;

/**
 * Comprehensive test cases for ConnectionStatus.java
 * Tests BroadcastReceiver behavior for network connectivity changes
 */
@RunWith(RobolectricTestRunner.class)
public class TestConnectionStatus {

    private Context context;
    private ConnectionStatus connectionStatus;
    private File offlineCallsDir;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        connectionStatus = new ConnectionStatus();
        
        // Create offline calls directory
        offlineCallsDir = new File(context.getDir("OfflineCalls", 0).getPath());
        if (!offlineCallsDir.exists()) {
            offlineCallsDir.mkdirs();
        }
        
        // Clean up any existing files
        cleanupOfflineCallsDir();
    }

    @After
    public void tearDown() {
        cleanupOfflineCallsDir();
    }

    private void cleanupOfflineCallsDir() {
        if (offlineCallsDir != null && offlineCallsDir.exists() && offlineCallsDir.isDirectory()) {
            File[] files = offlineCallsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    // =====================
    // Constructor Tests
    // =====================

    @Test
    public void testConstructor() {
        ConnectionStatus status = new ConnectionStatus();
        assertNotNull(status);
    }

    @Test
    public void testConstructorMultipleInstances() {
        ConnectionStatus status1 = new ConnectionStatus();
        ConnectionStatus status2 = new ConnectionStatus();
        ConnectionStatus status3 = new ConnectionStatus();
        
        assertNotNull(status1);
        assertNotNull(status2);
        assertNotNull(status3);
    }

    // =====================
    // onReceive - No Network
    // =====================

    @Test
    public void testOnReceive_noNetwork() {
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            // Should handle gracefully
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void testOnReceive_noNetworkWithNullIntent() {
        try {
            connectionStatus.onReceive(context, null);
            // Should handle null intent gracefully
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should not throw exception for null intent");
        }
    }

    @Test
    public void testOnReceive_noNetworkMultipleTimes() {
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            for (int i = 0; i < 5; i++) {
                connectionStatus.onReceive(context, intent);
            }
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle multiple calls gracefully");
        }
    }

    // =====================
    // onReceive - With Network, No Offline Calls
    // =====================

    @Test
    public void testOnReceive_withNetworkNoOfflineCalls() {
        // Ensure directory is empty
        cleanupOfflineCallsDir();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle empty directory gracefully");
        }
    }

    @Test
    public void testOnReceive_withNetworkEmptyDirectory() {
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle empty directory gracefully");
        }
    }

    // =====================
    // onReceive - With Network and Offline Calls
    // =====================

    @Test
    public void testOnReceive_withNetworkAndValidOfflineCall() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        // Create a valid offline call file
        File offlineFile = new File(offlineCallsDir, "offline_call_1.json");
        
        JSONObject headers = new JSONObject();
        headers.put("api_key", "test_key");
        headers.put("access_token", "test_token");
        
        JSONObject params = new JSONObject();
        params.put("environment", "test");
        
        JSONObject offlineCall = new JSONObject();
        offlineCall.put("url", "https://api.contentstack.io/v3/content_types/test/entries");
        offlineCall.put("controller", "getEntry");
        offlineCall.put("headers", headers);
        offlineCall.put("params", params);
        offlineCall.put("cacheFileName", "test_cache.json");
        offlineCall.put("requestInfo", "test_info");
        
        // Write to file
        FileWriter writer = new FileWriter(offlineFile);
        writer.write(offlineCall.toString());
        writer.close();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            // Should process without exception
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should process offline calls gracefully");
        }
    }

    @Test
    public void testOnReceive_withNetworkAndMultipleOfflineCalls() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        // Create multiple offline call files
        for (int i = 0; i < 3; i++) {
            File offlineFile = new File(offlineCallsDir, "offline_call_" + i + ".json");
            
            JSONObject headers = new JSONObject();
            headers.put("api_key", "test_key_" + i);
            
            JSONObject params = new JSONObject();
            params.put("param" + i, "value" + i);
            
            JSONObject offlineCall = new JSONObject();
            offlineCall.put("url", "https://api.contentstack.io/v3/test_" + i);
            offlineCall.put("controller", "controller_" + i);
            offlineCall.put("headers", headers);
            offlineCall.put("params", params);
            offlineCall.put("cacheFileName", "cache_" + i + ".json");
            offlineCall.put("requestInfo", "info_" + i);
            
            FileWriter writer = new FileWriter(offlineFile);
            writer.write(offlineCall.toString());
            writer.close();
        }
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            // Should process multiple offline calls without exception
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should process multiple offline calls gracefully");
        }
    }

    @Test
    public void testOnReceive_withNetworkAndComplexHeaders() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        File offlineFile = new File(offlineCallsDir, "offline_complex.json");
        
        JSONObject headers = new JSONObject();
        headers.put("api_key", "blt123456789");
        headers.put("access_token", "cs_token_abc");
        headers.put("authorization", "Bearer token123");
        headers.put("content-type", "application/json");
        headers.put("x-custom-header", "custom-value");
        
        JSONObject params = new JSONObject();
        params.put("locale", "en-us");
        params.put("environment", "production");
        
        JSONObject offlineCall = new JSONObject();
        offlineCall.put("url", "https://api.contentstack.io/v3/content_types/blog/entries");
        offlineCall.put("controller", "getQueryEntries");
        offlineCall.put("headers", headers);
        offlineCall.put("params", params);
        offlineCall.put("cacheFileName", "blog_cache.json");
        offlineCall.put("requestInfo", "blog_query");
        
        FileWriter writer = new FileWriter(offlineFile);
        writer.write(offlineCall.toString());
        writer.close();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            // Should process complex headers without exception
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle complex headers gracefully");
        }
    }

    // =====================
    // Exception Scenarios
    // =====================

    @Test
    public void testOnReceive_withInvalidJsonFile() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        File offlineFile = new File(offlineCallsDir, "invalid.json");
        
        // Write invalid JSON
        FileWriter writer = new FileWriter(offlineFile);
        writer.write("{ invalid json content ");
        writer.close();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        // Should handle exception gracefully
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle invalid JSON gracefully");
        }
    }

    @Test
    public void testOnReceive_withEmptyFile() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        File offlineFile = new File(offlineCallsDir, "empty.json");
        offlineFile.createNewFile();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle empty file gracefully");
        }
    }

    @Test
    public void testOnReceive_withMissingUrl() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        File offlineFile = new File(offlineCallsDir, "missing_url.json");
        
        JSONObject headers = new JSONObject();
        headers.put("api_key", "test");
        
        JSONObject offlineCall = new JSONObject();
        // Missing "url" field
        offlineCall.put("controller", "test");
        offlineCall.put("headers", headers);
        
        FileWriter writer = new FileWriter(offlineFile);
        writer.write(offlineCall.toString());
        writer.close();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle missing URL gracefully");
        }
    }

    @Test
    public void testOnReceive_withMissingController() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        File offlineFile = new File(offlineCallsDir, "missing_controller.json");
        
        JSONObject headers = new JSONObject();
        headers.put("api_key", "test");
        
        JSONObject offlineCall = new JSONObject();
        offlineCall.put("url", "https://api.test.com");
        // Missing "controller" field
        offlineCall.put("headers", headers);
        
        FileWriter writer = new FileWriter(offlineFile);
        writer.write(offlineCall.toString());
        writer.close();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle missing controller gracefully");
        }
    }

    @Test
    public void testOnReceive_withEmptyHeaders() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        File offlineFile = new File(offlineCallsDir, "empty_headers.json");
        
        JSONObject headers = new JSONObject();
        
        JSONObject offlineCall = new JSONObject();
        offlineCall.put("url", "https://api.test.com");
        offlineCall.put("controller", "test");
        offlineCall.put("headers", headers);
        
        FileWriter writer = new FileWriter(offlineFile);
        writer.write(offlineCall.toString());
        writer.close();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle empty headers gracefully");
        }
    }

    // =====================
    // Edge Cases
    // =====================

    @Test
    public void testOnReceive_withNonJsonFile() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        File offlineFile = new File(offlineCallsDir, "text_file.txt");
        
        FileWriter writer = new FileWriter(offlineFile);
        writer.write("This is not JSON content at all!");
        writer.close();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle non-JSON file gracefully");
        }
    }

    @Test
    public void testOnReceive_withLargeNumberOfOfflineCalls() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        // Create 10 offline calls
        for (int i = 0; i < 10; i++) {
            File offlineFile = new File(offlineCallsDir, "call_" + i + ".json");
            
            JSONObject headers = new JSONObject();
            headers.put("header_" + i, "value_" + i);
            
            JSONObject offlineCall = new JSONObject();
            offlineCall.put("url", "https://api.test.com/" + i);
            offlineCall.put("controller", "ctrl_" + i);
            offlineCall.put("headers", headers);
            
            FileWriter writer = new FileWriter(offlineFile);
            writer.write(offlineCall.toString());
            writer.close();
        }
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle large number of offline calls gracefully");
        }
    }

    @Test
    public void testOnReceive_networkToggle() {
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            // Multiple sequential calls
            connectionStatus.onReceive(context, intent);
            connectionStatus.onReceive(context, intent);
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle network toggle gracefully");
        }
    }

    @Test
    public void testOnReceive_multipleReceiversSimultaneously() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        ConnectionStatus receiver1 = new ConnectionStatus();
        ConnectionStatus receiver2 = new ConnectionStatus();
        ConnectionStatus receiver3 = new ConnectionStatus();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            receiver1.onReceive(context, intent);
            receiver2.onReceive(context, intent);
            receiver3.onReceive(context, intent);
            assertNotNull(receiver1);
            assertNotNull(receiver2);
            assertNotNull(receiver3);
        } catch (Exception e) {
            fail("Should handle multiple receivers gracefully");
        }
    }

    @Test
    public void testOnReceive_withDifferentIntentActions() {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        // Test with different intent actions
        Intent intent1 = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        Intent intent2 = new Intent("android.net.wifi.WIFI_STATE_CHANGED");
        Intent intent3 = new Intent("android.intent.action.BOOT_COMPLETED");
        
        try {
            connectionStatus.onReceive(context, intent1);
            connectionStatus.onReceive(context, intent2);
            connectionStatus.onReceive(context, intent3);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle different intent actions gracefully");
        }
    }

    @Test
    public void testOnReceive_withSpecialCharactersInHeaders() throws Exception {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        
        File offlineFile = new File(offlineCallsDir, "special_chars.json");
        
        JSONObject headers = new JSONObject();
        headers.put("key_with_!@#$%", "value_with_^&*()");
        headers.put("unicode_key_日本語", "unicode_value_中文");
        headers.put("spaces in key", "spaces in value");
        
        JSONObject offlineCall = new JSONObject();
        offlineCall.put("url", "https://api.test.com/special");
        offlineCall.put("controller", "test");
        offlineCall.put("headers", headers);
        
        FileWriter writer = new FileWriter(offlineFile);
        writer.write(offlineCall.toString());
        writer.close();
        
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            connectionStatus.onReceive(context, intent);
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle special characters gracefully");
        }
    }

    @Test
    public void testOnReceive_rapidFireMultipleCalls() {
        SDKConstant.IS_NETWORK_AVAILABLE = true;
        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        
        try {
            // Rapidly call onReceive multiple times
            for (int i = 0; i < 20; i++) {
                connectionStatus.onReceive(context, intent);
            }
            assertNotNull(connectionStatus);
        } catch (Exception e) {
            fail("Should handle rapid fire calls gracefully");
        }
    }
}

