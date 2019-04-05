package com.contentstack.sdk;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.okhttp.OkHttpClient;
import com.contentstack.okhttp.OkUrlFactory;
import com.contentstack.volley.toolbox.HurlStack;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Contentstack.com, Inc
 *
 */
class OkHttpClass extends HurlStack {

    private static String TAG = "OkHttpClass";
    private final OkUrlFactory mFactory;

    protected OkHttpClass(String protocol) {
        this(protocol, new OkHttpClient());
    }

    protected OkHttpClass(String protocol, OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }

        mFactory = new OkUrlFactory(client);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return mFactory.open(url);
    }


}
