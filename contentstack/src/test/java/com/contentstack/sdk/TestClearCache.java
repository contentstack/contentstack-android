package com.contentstack.sdk;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestClearCache {

    private File createTempDir() {
        File dir = new File(System.getProperty("java.io.tmpdir"),
                "ContentstackCacheTest_" + System.nanoTime());
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        return dir;
    }

    private File writeCacheFile(File dir, String name, long timestampMillis) throws Exception {
        File file = new File(dir, name);
        JSONObject json = new JSONObject();
        json.put("timestamp", String.valueOf(timestampMillis));
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json.toString());
        }
        return file;
    }

    @Test
    public void testOnReceive_deletesOldFilesAndKeepsRecent() throws Exception {
        // Mock Context
        Context context = mock(Context.class);

        // Use a temp directory to simulate ContentstackCache
        File cacheDir = createTempDir();
        when(context.getDir("ContentstackCache", 0)).thenReturn(cacheDir);

        // current time (UTC aligned like ClearCache)
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        long nowMillis = cal.getTimeInMillis();

        long twentyFiveHoursAgo = nowMillis - TimeUnit.HOURS.toMillis(25);
        long oneHourAgo = nowMillis - TimeUnit.HOURS.toMillis(1);

        // old file: should be deleted
        File oldFile = writeCacheFile(cacheDir, "old_response.json", twentyFiveHoursAgo);

        // recent file: should be kept
        File recentFile = writeCacheFile(cacheDir, "recent_response.json", oneHourAgo);

        // session and installation files: never deleted
        File sessionFile = writeCacheFile(cacheDir, "Session", twentyFiveHoursAgo);
        File installationFile = writeCacheFile(cacheDir, "Installation", twentyFiveHoursAgo);

        ClearCache clearCache = new ClearCache();
        clearCache.onReceive(context, new Intent("test.intent.CLEAR_CACHE"));

        // Old file should be gone
        assertFalse("Old cache file should be deleted", oldFile.exists());

        // Recent file should still be there
        assertTrue("Recent cache file should not be deleted", recentFile.exists());

        // Session and Installation should not be deleted
        assertTrue("Session file should not be deleted", sessionFile.exists());
        assertTrue("Installation file should not be deleted", installationFile.exists());
    }

    @Test
    public void testOnReceive_handlesEmptyDirectoryGracefully() {
        Context context = mock(Context.class);

        File cacheDir = createTempDir();
        when(context.getDir("ContentstackCache", 0)).thenReturn(cacheDir);

        // Ensure directory is empty
        File[] existing = cacheDir.listFiles();
        if (existing != null) {
            for (File f : existing) {
                //noinspection ResultOfMethodCallIgnored
                f.delete();
            }
        }

        ClearCache clearCache = new ClearCache();
        clearCache.onReceive(context, new Intent("test.intent.CLEAR_CACHE"));

        // No crash is success; directory should still exist
        assertTrue(cacheDir.exists());
    }
}
