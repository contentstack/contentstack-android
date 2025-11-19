package com.contentstack.sdk;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
public class TestClearCache {

    private Context context;
    private File cacheDir;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();

        // This will be something like /data/data/.../app_ContentstackCache-test
        cacheDir = context.getDir("ContentstackCache", 0);

        // Clean it before each test
        File[] files = cacheDir.listFiles();
        if (files != null) {
            for (File f : files) {
                // Best-effort cleanup
                f.delete();
            }
        }
    }

    private File createJsonCacheFile(String name, long timestampMillis) throws Exception {
        File f = new File(cacheDir, name);
        JSONObject obj = new JSONObject();
        obj.put("timestamp", String.valueOf(timestampMillis));
        FileWriter writer = new FileWriter(f);
        writer.write(obj.toString());
        writer.flush();
        writer.close();
        return f;
    }

    private File createPlainFile(String name) throws Exception {
        File f = new File(cacheDir, name);
        FileWriter writer = new FileWriter(f);
        writer.write("dummy");
        writer.flush();
        writer.close();
        return f;
    }

    private long now() {
        return System.currentTimeMillis();
    }

    // ----------------------------------------------------
    // 1. Old file (>=24h) should be deleted
    // ----------------------------------------------------
    @Test
    public void testOnReceive_deletesOldFile() throws Exception {
        long twentyFiveHoursAgo = now() - TimeUnit.HOURS.toMillis(25);

        File oldFile = createJsonCacheFile("old_response.json", twentyFiveHoursAgo);

        assertTrue("Old file should exist before onReceive", oldFile.exists());

        ClearCache clearCache = new ClearCache();
        clearCache.onReceive(context, new Intent("com.contentstack.sdk.CLEAR_CACHE"));

        assertFalse("Old file should be deleted", oldFile.exists());
    }

    // ----------------------------------------------------
    // 2. Recent file (<24h) should NOT be deleted
    // ----------------------------------------------------
    @Test
    public void testOnReceive_keepsRecentFile() throws Exception {
        long oneHourAgo = now() - TimeUnit.HOURS.toMillis(1);

        File recentFile = createJsonCacheFile("recent_response.json", oneHourAgo);

        assertTrue("Recent file should exist before onReceive", recentFile.exists());

        ClearCache clearCache = new ClearCache();
        clearCache.onReceive(context, new Intent("com.contentstack.sdk.CLEAR_CACHE"));

        assertTrue("Recent file should NOT be deleted", recentFile.exists());
    }

    // ----------------------------------------------------
    // 3. Session and Installation files are ignored
    // ----------------------------------------------------
    @Test
    public void testOnReceive_ignoresSessionAndInstallationFiles() throws Exception {
        // Even if they look old, code explicitly ignores them

        long twentyFiveHoursAgo = now() - TimeUnit.HOURS.toMillis(25);

        File sessionFile = createJsonCacheFile("Session", twentyFiveHoursAgo);
        File installationFile = createJsonCacheFile("Installation", twentyFiveHoursAgo);

        assertTrue(sessionFile.exists());
        assertTrue(installationFile.exists());

        ClearCache clearCache = new ClearCache();
        clearCache.onReceive(context, new Intent("com.contentstack.sdk.CLEAR_CACHE"));

        // They should still exist because of the name-based skip condition
        assertTrue("Session file should not be deleted", sessionFile.exists());
        assertTrue("Installation file should not be deleted", installationFile.exists());
    }

    // ----------------------------------------------------
    // 4. File without valid JSON or timestamp should be ignored (no crash)
    // ----------------------------------------------------
    @Test
    public void testOnReceive_invalidJsonOrNoTimestamp_doesNotCrashAndKeepsFile() throws Exception {
        File invalidFile = createPlainFile("invalid.json");

        assertTrue(invalidFile.exists());

        ClearCache clearCache = new ClearCache();
        clearCache.onReceive(context, new Intent("com.contentstack.sdk.CLEAR_CACHE"));

        // Since getJsonFromCacheFile likely returns null or throws handled internally,
        // the file should not be deleted by our logic.
        assertTrue("Invalid file should still exist", invalidFile.exists());
    }
}
