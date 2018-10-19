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
    private SSLContext sslContext;

    protected OkHttpClass(String protocol) {
        this(protocol, new OkHttpClient());
    }

    protected OkHttpClass(String protocol, OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }

        if(protocol.equals(CSAppConstants.URLSCHEMA_HTTP)){
            OkHttpClient okHttpClient = getUnsafeOkHttpClient(client);
            mFactory = new OkUrlFactory(okHttpClient);
        }else{
            mFactory = new OkUrlFactory(client);
        }
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return mFactory.open(url);
    }

    protected OkHttpClient getUnsafeOkHttpClient(OkHttpClient okHttpClient) {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, e.toString());
        }
        return null;
    }
}
