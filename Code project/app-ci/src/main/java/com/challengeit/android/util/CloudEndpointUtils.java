package com.challengeit.android.util;

import android.app.Activity;
import android.widget.Toast;

import com.challengeit.android.log.LogWrapper;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

/**
 * Common utilities for working with Cloud Endpoints.
 *
 * If you'd like to test using a locally-running version of your App Engine
 * backend (i.e. running on the Development App Server), you need to set
 * LOCAL_ANDROID_RUN to 'true'.
 *
 * See the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
 * information.
 */
public class CloudEndpointUtils {

    /*
     * TODO: Need to change this to 'true' if you're running your backend locally using
     * the DevAppServer. See
     * http://developers.google.com/eclipse/docs/cloud_endpoints for more
     * information.
     */
    protected static final boolean LOCAL_ANDROID_RUN = false;
    protected static final boolean LOCAL_ANDROID_RUN_EMULATOR=false;
    /*
     * The root URL of where your DevAppServer is running (if you're running the
     * DevAppServer locally).
     */
    protected static final String LOCAL_APP_ENGINE_SERVER_URL = "http://10.220.248.98:8080";
    protected static final String DEPLOYED_APP_ENGINE_SERVER_URL = "https://1-dot-" +
            "challengeit-92.appspot.com";
    //192.168.2.14
    /*
     * The root URL of where your DevAppServer is running when it's being
     * accessed via the Android emulator (if you're running the DevAppServer
     * locally). In this case, you're running behind Android's virtual router.
     * See
     * http://developer.android.com/tools/devices/emulator.html#networkaddresses
     * for more information.
     */
    protected static final String LOCAL_APP_ENGINE_SERVER_URL_FOR_ANDROID = "http://10.0.2.2:8080";//http://192.168.1.31:8080

    @Getter @Setter
    private static GoogleAccountCredential credential;
    /**
     * Updates the Google client builder to connect the appropriate server based
     * on whether LOCAL_ANDROID_RUN is true or false.
     *
     * @param builder
     *            Google client builder
     * @return same Google client builder
     */
    public static <B extends AbstractGoogleClient.Builder> B updateBuilder(
            B builder) {
        if (LOCAL_ANDROID_RUN) {
            if(LOCAL_ANDROID_RUN_EMULATOR){
                builder.setRootUrl(LOCAL_APP_ENGINE_SERVER_URL_FOR_ANDROID
                        + "/_ah/api/");
            }

            else { //Real device
                builder.setRootUrl(LOCAL_APP_ENGINE_SERVER_URL
                        + "/_ah/api/");

            }
        }
        else{
            builder.setRootUrl(DEPLOYED_APP_ENGINE_SERVER_URL
                    + "/_ah/api/");
        }

        builder.setHttpRequestInitializer(credential);
        // only enable GZip when connecting to remote server
        final boolean enableGZip = builder.getRootUrl().startsWith("https:");

        builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
            public void initialize(AbstractGoogleClientRequest<?> request)
                    throws IOException {
                if (!enableGZip) {
                    request.setDisableGZipContent(true);
                }
            }
        });

        return builder;
    }

    /**
     * Logs the given message and shows an error alert dialog with it.
     *
     * @param activity
     *            activity
     */
    public static void logAndShowError(Activity activity,  Exception e) {
        String message;
        if (e != null) {
            message = e.getMessage();
            if (message == null) {
                message = e.toString();
            }
            LogWrapper.error(CloudEndpointUtils.class, message);
            showError(activity, message);
        }

    }

    /**
     * Shows an error alert dialog with the given message.
     *
     * @param activity
     *            activity
     * @param message
     *            message to show or {@code null} for none
     */
    public static void showError(final Activity activity, String message) {
        final String errorMessage = message == null ? "Error" : "[Error ] "
                + message;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}