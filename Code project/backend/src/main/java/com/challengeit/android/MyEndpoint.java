/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.challengeit.android;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.authentication.ctprojects", ownerName = "backend.authentication.ctprojects", packagePath = ""))
public class MyEndpoint {
    @ApiMethod(name = "sayHiAuth",
            clientIds = {Configuration.WEB_CLIENT_ID,
                    Configuration.ANDROID_CLIENT_ID,
                    com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
            audiences = {Configuration.AUDIENCE_ID})

    public MyBean sayHiAuth(User user, @Named("name") String name) throws UnauthorizedException {
        if (user == null)
            throw new UnauthorizedException("User is Not At All Valid");
        MyBean response = new MyBean();
        response.setData("Congrats ! " + name + "was an authentificated message !!!!");

        return response;
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

}
