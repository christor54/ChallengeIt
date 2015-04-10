package com.challengeit.android;

/**
 * Reference
 * https://github.com/GoogleCloudPlatform/solutions-mobile-shopping-assistant-backend-java/blob/master/src/com/google/sample/mobileassistant/ApiKeysAndIds.java
 */
public class Configuration {

    static final String GCM_API_KEY = "!!! ENTER YOUR GCM API KEY HERE !!!";

    static final String ANDROID_CLIENT_ID = "android_client_key_here";
    static final String WEB_CLIENT_ID ="web_client_key_here";
    static final String AUDIENCE_ID = WEB_CLIENT_ID;

    static final Boolean AUTHENTIFICATION_REQUIRED_USER_API = true;
    static final Boolean AUTHENTIFICATION_REQUIRED_GAME_API = false;

    static final String API_OWNER = "challengeit.com";//Original -> google.com
    static final String API_PACKAGE_PATH = "challengeit.com";//Original -> sample.mobileassistant
    static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
}
