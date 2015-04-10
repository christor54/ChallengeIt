package com.challengeit.android;

import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

/**
 * Created by christophe on 2/10/2015.
 */
public class MyUtils {

    public static void checkUserValidityUserApi(User user) throws UnauthorizedException {
        if (user == null && Configuration.AUTHENTIFICATION_REQUIRED_USER_API==true) {
            throw new UnauthorizedException("Only authenticated calls are allowed");
        }
    }

    public static void checkUserValidityGameApi(User user) throws UnauthorizedException {
        if (user == null && Configuration.AUTHENTIFICATION_REQUIRED_GAME_API==true) {
            throw new UnauthorizedException("Only authenticated calls are allowed");
        }
    }

}
