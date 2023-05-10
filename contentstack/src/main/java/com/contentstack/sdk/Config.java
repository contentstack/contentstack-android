package com.contentstack.sdk;

import android.text.TextUtils;

/**
 * Set Configuration for stack instance creation.
 *
 * @author contentstack.com, Inc
 */
public class Config {
    protected String PROTOCOL = "https://";
    protected String URL = "cdn.contentstack.io";
    protected String VERSION = "v3";
    protected String environment = null;
    protected ContentstackRegion region = ContentstackRegion.US;

    /**
     * Sets region allow you to set your region for the Contentstack server.
     *
     * @param region <p>
     *               <b>Note:</b> Default region sets to us </a>
     *
     *               <br><br><b>Example For Different Regions:</b><br>
     *               <pre class="prettyprint">
     *               config.setRegion(ContentstackRegion.US);
     *               config.setRegion(ContentstackRegion.EU);
     *               config.setRegion(ContentstackRegion.AZURE_EU);
     *               config.setRegion(ContentstackRegion.AZURE_NA);
     *            </pre>
     */
    public ContentstackRegion setRegion(ContentstackRegion region) {
        this.region = region;
        return this.region;
    }


    public ContentstackRegion getRegion() {
        return this.region;
    }

    public enum ContentstackRegion {US, EU, AZURE_NA, AZURE_EU}

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


}
