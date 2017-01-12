package com.builtio.contentstack;

import android.text.TextUtils;

import com.builtio.contentstack.utilities.CSAppConstants;

/**
 * Set Configuration for stack instance creation.
 *
 * @author  built.io. Inc
 *
 */
public class Config {

    protected String URLSCHEMA      = "https://";
    protected String URL            = "cdn.contentstack.io";
    protected String VERSION        = "v3";
    protected String environment    = null;

    /**
     * BuiltConfig constructor
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * BuiltConfig config = new BuiltConfig();
     * </pre>
     */
    public Config(){}

    /**
     * Sets host name of the Built.io Contentstack server.
     *
     * @param hostName
     * 					host name.
     *
     * <p>
     * <b>Note:</b> Default hostname sets to <a href ="https://cdn.contentstack.io"> cdn.contentstack.io </a>
     *  and default protocol is HTTPS.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * config.setHost("cdn.contentstack.io");
     * </pre>
     */
    public void setHost(String hostName){
        if(!TextUtils.isEmpty(hostName)) {
            URL = hostName;
        }
    }

    /**
     * Sets the protocol in base url.
     *
     * @param isSSL
     * 					true/false values initiating calls (HTTPS/HTTP) respectively.
     *
     * <p>
     * <b>Note:</b> Default protocol is HTTPS.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * config.setSSL(true);
     * </pre>
     */
    public void setSSL(boolean isSSL){

        if(isSSL){
            URLSCHEMA = CSAppConstants.URLSCHEMA_HTTPS;
        }else{
            URLSCHEMA = CSAppConstants.URLSCHEMA_HTTP;
        }
    }

    /**
     * Get URL.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String url = config.getHost();
     * </pre>
     */
    public String getHost(){
        return URL;
    }

    /**
     * Get URL.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * boolean protocol = config.isSSL();
     * </pre>
     */
    public boolean isSSL(){

        return URLSCHEMA.equalsIgnoreCase(CSAppConstants.URLSCHEMA_HTTPS) ? true : false;
    }

    /**
     * Get version of the Built.io Contentstack server.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String version = config.getVersion();
     * </pre>
     */
    public String getVersion(){
        return  VERSION;
    }

    /**
     * Changes the Built.io Contentstack version to be used in the final URL.
     *
     * @param version
     *                  version string.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      config.setVersion("v3");
     * </pre>
     */
    private void setVersion(String version){
        if(!TextUtils.isEmpty(version)){
            VERSION = version;
        }
    }

    /**
     * set environment.
     *
     * @param environment
     *                      environment uid/name
     *
     *  <br><br><b>Example :</b><br>
     *  <pre class="prettyprint">
     *  config.setEnvironment("stag", false);
     * </pre>
     */
    protected void setEnvironment(String environment){
        if(!TextUtils.isEmpty(environment)){
            this.environment = environment;
        }
    }

    /**
     * Get environment.
     *
     *  <br><br><b>Example :</b><br>
     *  <pre class="prettyprint">
     *  String environment = config.getEnvironment();
     * </pre>
     */
    public String getEnvironment(){
        return environment;
    }


}
