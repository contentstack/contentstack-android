package com.builtio.contentstack;

import com.builtio.contentstack.utilities.CSAppConstants;
import com.builtio.contentstack.utilities.CSAppUtils;
import com.builtio.contentstack.utilities.TLSSocketFactory;
import com.builtio.okhttp.OkHttpClient;
import com.builtio.okhttp.OkUrlFactory;
import com.builtio.volley.toolbox.HurlStack;

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
 * @author built.io, Inc
 *
 */
class OkHttpClass extends HurlStack {

    private static String TAG = "OkHttpClass";
    private final OkUrlFactory mFactory;
    private SSLContext sslContext;
    SSLSocketFactory sslSocketFactory;

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

            try {
                sslSocketFactory = new TLSSocketFactory();
                client.setSslSocketFactory(sslSocketFactory);
            } catch (Exception e) {
                e.printStackTrace();
            }

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

            okHttpClient.setSslSocketFactory(new TLSSocketFactory());
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
