package com.contentstack.sdk;

import static com.contentstack.sdk.SDKConstant.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

/**
 * Connection Status.
 *
 * @author ishaileshmishra
 */
public class ConnectionStatus extends BroadcastReceiver {

    private final String TAG = ConnectionStatus.class.getSimpleName();

    /**
     * Instantiates a new Connection status.
     */
    public ConnectionStatus() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        CSUtil.isNetworkAvailable(context);

        if (!IS_NETWORK_AVAILABLE) {
            //no net connection
            IS_NETWORK_AVAILABLE = false;
        } else {
            try {
                JSONObject jsonObj = null;
                JSONObject headerObject = null;
                HashMap<String, Object> headerGroup = new HashMap<>();
                IS_NETWORK_AVAILABLE = true;
                File offlineCallsFolder = new File(context.getDir("OfflineCalls", 0).getPath());

                if (offlineCallsFolder.isDirectory()) {
                    File[] childFiles = offlineCallsFolder.listFiles();
                    assert childFiles != null;
                    for (File child : childFiles) {
                        File file = new File(offlineCallsFolder, child.getName());
                        if (file.exists()) {
                            jsonObj = SDKUtil.getJsonFromCacheFile(file);
                            if (jsonObj != null) {
                                headerObject = jsonObj.optJSONObject("headers");
                                assert headerObject != null;
                                int count = Objects.requireNonNull(headerObject.names()).length();
                                for (int i = 0; i < count; i++) {
                                    String key = Objects.requireNonNull(headerObject.names()).getString(i);
                                    headerGroup.put(key, headerObject.optString(key));
                                }
                                CSConnectionRequest connectionRequest = new CSConnectionRequest();
                                connectionRequest.setParams(
                                        Objects.requireNonNull(jsonObj.opt("url")).toString(),
                                        RequestMethod.POST,
                                        Objects.requireNonNull(jsonObj.opt("controller")).toString(),
                                        jsonObj.optJSONObject("params"),
                                        headerGroup,
                                        jsonObj.opt("cacheFileName"),
                                        jsonObj.opt("requestInfo"),
                                        null
                                );
                            }
                            child.delete();
                        } else {
                            SDKUtil.showLog(TAG, "No offline network calls");
                        }
                    }
                }
            } catch (Exception e) {
                SDKUtil.showLog(TAG, "contentstack send saved network calls-------catch|" + e);
            }
        }
    }

}
