package com.challengeit.android;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by christophe on 3/5/2015.
 */
public class Invitation {
    @Getter @Setter
    private String usernameSender;

    @Getter @Setter
    Date timeStamp;

    public Invitation() {
        timeStamp = new Date();
    }

    public boolean equals (Invitation invitation){
        return this.usernameSender.equals(invitation.getUsernameSender());
    }
}
