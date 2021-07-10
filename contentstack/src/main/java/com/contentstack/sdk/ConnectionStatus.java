package com.contentstack.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Connection Status.
 *
 * @author contentstack.com, Inc
 */
public class ConnectionStatus extends BroadcastReceiver {

    public ConnectionStatus() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Contentstack.isNetworkAvailable(context);

        if (!CSAppConstants.isNetworkAvailable) {
            //no net connection
            CSAppConstants.isNetworkAvailable = false;

        } else {
            try {
                JSONObject jsonObj = null;
                JSONObject headerObject = null;
                HashMap<String, Object> headerGroup = new HashMap();

                CSAppConstants.isNetworkAvailable = true;

                File offlineCallsFolder = new File(context.getDir("OfflineCalls", 0).getPath());

                if (offlineCallsFolder.isDirectory()) {
                    File[] childFiles = offlineCallsFolder.listFiles();
                    for (File child : childFiles) {
                        File file = new File(offlineCallsFolder, child.getName());
                        if (file.exists()) {
                            jsonObj = CSAppUtils.getJsonFromCacheFile(file);

                            if (jsonObj != null) {

                                headerObject = jsonObj.optJSONObject("headers");

                                int count = headerObject.names().length();
                                for (int i = 0; i < count; i++) {
                                    String key = headerObject.names().getString(i);
                                    headerGroup.put(key, headerObject.optString(key));
                                }

                                CSConnectionRequest connectionRequest = new CSConnectionRequest();
                                connectionRequest.setParams(
                                        jsonObj.opt("url").toString(),
                                        CSAppConstants.RequestMethod.POST,
                                        jsonObj.opt("controller").toString(),
                                        jsonObj.optJSONObject("params"),
                                        headerGroup,
                                        jsonObj.opt("cacheFileName"),
                                        jsonObj.opt("requestInfo"),
                                        null
                                );
                            }
                            child.delete();
                        } else {
                            CSAppUtils.showLog("ConnectionStatus", "--------------------no offline network calls");
                        }
                    }
                }

            } catch (Exception e) {
                CSAppUtils.showLog("ConnectionStatus", "-----content stack----------send  saved network calls-------catch|" + e);
            }
        }

        CSAppUtils.showLog("ConnectionStatus", "---------------------BuiltAppConstants.isNetworkAvailable|" + CSAppConstants.isNetworkAvailable);

    }

}
