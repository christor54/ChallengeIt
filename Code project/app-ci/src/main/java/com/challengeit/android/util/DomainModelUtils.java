package com.challengeit.android.util;

import android.content.Intent;

import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;

/**
 * Created by christophe on 6/30/2014.
 */
public class DomainModelUtils {

    public static UserLocal getUserLocal(Intent intent) {
        UserLocal userLocal = new UserLocal();
        userLocal.setUsername(intent.getStringExtra("username"));
        userLocal.setId(intent.getLongExtra("id", 1));
        int score=intent.getIntExtra("score", 0);
        userLocal.setScore(new Integer(score));
        return userLocal;
    }
}
