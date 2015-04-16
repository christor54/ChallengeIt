package com.challengeit.android.log;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.challengeit.android.util.CloudEndpointUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogWrapper {

    private static final String TAG = "com.challengeit.android";
    private static final boolean DEBUG = true;

    public static void debug(Class<?> clazz, String message) {
        if (DEBUG) Log.d(TAG, format(clazz, message));
    }

    public static void info(Class<?> clazz, String message) {
        if (DEBUG) Log.i(TAG, format(clazz, message));
    }

    public static void warning(Class<?> clazz, String message) {
        Log.w(TAG, format(clazz, message));
    }

    public static void error(Class<?> clazz, String message) {
        Log.e(TAG, format(clazz, message));
    }

    public static void error(Class<?> clazz, String message, Exception e) {
        Log.e(TAG, format(clazz, message), e);
    }

    private static String format(Class<?> clazz, String message) {
        return new StringBuilder().append("[").append(clazz.getSimpleName()).append("] ").append(message).toString();
    }

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

    public static void showMessage(final Activity activity, String message) {
        final String messageToDisplay = message == null ? "Error" : message;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, messageToDisplay, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public static void showExceptionSimpleMessage(final Activity activity, Exception e, String backupMessage){
        String messageFromException = getMessageFromException(e);
        final String message =geMessageFromExceptionWithBackup(e,backupMessage);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public static String geMessageFromExceptionWithBackup(Exception e, String backupMessage){
        String messageFromException = getMessageFromException(e);
        return messageFromException != null ? messageFromException : backupMessage;
    }
    public static String getMessageFromException(Exception e){
        String message = null;
        if(e==null)
            return null;
        message= e.getMessage();
        try {
            Pattern p = Pattern.compile("\"message\": \"(.*)\",");
            Matcher m = p.matcher(message);
            if(m.find())
                return m.group(1);
            else
                return null;
        }
        catch(Exception ex){
            return null;
        }
    }
}