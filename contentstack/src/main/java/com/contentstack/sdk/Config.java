package com.contentstack.sdk;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.Proxy;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;


/**
 * Set Configuration for stack instance creation.
 *
 * @author contentstack.com, Inc
 */
public class Config {
    protected String livePreviewHash = null;
    protected String livePreviewContentType = null;
    protected String livePreviewEntryUid = null;
    protected String PROTOCOL = "https://";
    protected String URL = "cdn.contentstack.io";
    protected String VERSION = "v3";
    protected String environment = null;
    protected String branch = null;
    protected String[] earlyAccess = null;
    protected Proxy proxy = null;
    protected ConnectionPool connectionPool = new ConnectionPool();
    protected String endpoint;
    protected boolean enableLivePreview = false;
    protected String livePreviewHost;
    protected JSONObject livePreviewEntry = null;
    protected String previewToken;
    protected String managementToken;



    /**
     * get branch is used for internal use
     *
     * @return branch
     */
    protected String getBranch() {
        return branch;
    }

    /**
     * Set branch to the config
     *
     * @param branch
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    protected ContentstackRegion region = ContentstackRegion.US;

    /**
     * Sets region allow you to set your region for the Contentstack server.
     *
     * @param region <p>
     *               <b>Note:</b> Default region sets to us </a>
     *
     *               <br><br><b>Example For Different Regions:</b><br>
     *               <pre>
     *                                                         {@code
     *                                                         config.setRegion(ContentstackRegion.US);
     *                                                         config.setRegion(ContentstackRegion.EU);
     *                                                         config.setRegion(ContentstackRegion.AZURE_EU);
     *                                                         config.setRegion(ContentstackRegion.AZURE_NA);
     *                                                         config.setRegion(ContentstackRegion.GCP_NA);
     *                                                         }
     *                                                         </pre>
     */
    public ContentstackRegion setRegion(ContentstackRegion region) {
        this.region = region;
        return this.region;
    }


    public ContentstackRegion getRegion() {
        return this.region;
    }

    public String[] getEarlyAccess() {
        return this.earlyAccess;
    }

    public Config earlyAccess(String[] earlyAccess) {
        if (earlyAccess == null) {
            Objects.requireNonNull("Null early access");
        }
        this.earlyAccess = earlyAccess;
        return this;
    }

    public enum ContentstackRegion {US, EU, AZURE_NA, AZURE_EU, GCP_NA}

    /**
     * Config constructor
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Config config = new Config();
     * </pre>
     */
    public Config() {
    }

    /**
     * Sets host name of the Contentstack server.
     *
     * @param hostName host name.
     *                 <p>
     *                 <b>Note:</b> Default hostname sets to <a href ="https://cdn.contentstack.io"> cdn.contentstack.io </a>
     *                 and default protocol is HTTPS.
     *                 <br><br><b>Example :</b><br>
     *                 <pre
     *                 config.setHost("cdn.contentstack.io");
     *                 </pre>
     */

    public void setHost(String hostName) {
        if (!TextUtils.isEmpty(hostName)) {
            URL = hostName;
        }
    }


    /**
     * Get Host.
     *
     * <br><br><b>Example :</b><br>
     * <pre
     * String url = config.getHost();
     * </pre>
     */
    public String getHost() {
        return URL;
    }


    /**
     * Get version of the Contentstack server.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String version = config.getVersion();
     * </pre>
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     * Changes the Contentstack version to be used in the final URL.
     *
     * @param version version string.
     *
     *                <br><br><b>Example :</b><br>
     *                <pre
     *                config.setVersion("v3");
     *                </pre>
     */
    private void setVersion(String version) {
        if (!TextUtils.isEmpty(version)) {
            VERSION = version;
        }
    }

    /**
     * set environment.
     *
     * @param environment environment uid/name
     *
     *                    <br><br><b>Example :</b><br>
     *                    <pre
     *                    config.setEnvironment("stag", false);
     *                    </pre>
     */
    protected void setEnvironment(String environment) {
        if (!TextUtils.isEmpty(environment)) {
            this.environment = environment;
        }
    }

    /**
     * Get environment.
     * <br><br><b>Example :</b><br>
     * <pre
     * String environment = config.getEnvironment();
     * </pre>
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Proxy can be set like below.
     *
     * @param proxy Proxy setting, typically a type (http, socks) and a socket address. A Proxy is an immutable object
     *              <br>
     *              <br>
     *              <b>Example:</b><br>
     *              <br>
     *              <code>
     *              java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP,  new InetSocketAddress("proxyHost", "proxyPort"));
     *              java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("sl.theproxyvpn.io", 80)); Config
     *              config = new Config(); config.setProxy(proxy);
     *              </code>
     */
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     * Returns the Proxy instance
     *
     * @return Proxy
     */
    public Proxy getProxy() {
        return this.proxy;
    }

    /**
     * Enable live preview config.
     *
     * @param enableLivePreview to enable live preview
     * @return the config
     */
    public Config enableLivePreview(boolean enableLivePreview) {
        this.enableLivePreview = enableLivePreview;
        return this;
    }

    /**
     * Sets live preview host.
     *
     * @param livePreviewHost the live preview host
     * @return the live preview host
     */
    public Config setLivePreviewHost( @NotNull String livePreviewHost) {
        this.livePreviewHost = livePreviewHost;
        return this;
    }

    protected Config setLivePreviewEntry(@NotNull JSONObject livePreviewEntry) {
        this.livePreviewEntry = livePreviewEntry;
        return this;
    }

     /**
     * Sets preview token.
     *
     * @param previewToken the preview token
     * @return the preview token
     */
    public Config setPreviewToken(@NotNull String previewToken){
        this.previewToken = previewToken;
        return this;
    }

    /**
     * Sets management token.
     *
     * @param managementToken the management token
     * @return the management token
     */
    public Config setManagementToken(@NotNull String managementToken) {
        this.managementToken = managementToken;
        return this;
    }
    /**
     * Manages reuse of HTTP and HTTP/2 connections for reduced network latency. HTTP requests that * share the same
     * {@link okhttp3.Address} may share a {@link okhttp3.Connection}. This class implements the policy * of which
     * connections to keep open for future use.
     *
     * @param maxIdleConnections the maxIdleConnections default value is 5
     * @param keepAliveDuration  the keepAliveDuration default value is 5
     * @param timeUnit           the timeUnit default value is TimeUnit. MINUTES
     * @return ConnectionPool
     */
    public ConnectionPool connectionPool(int maxIdleConnections, long keepAliveDuration, TimeUnit timeUnit) {
        this.connectionPool = new ConnectionPool(maxIdleConnections, keepAliveDuration, timeUnit);
        return this.connectionPool;
    }

    protected String getEndpoint() {
        return endpoint + "/" + getVersion() + "/";
    }

    protected void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }


}
