package com.challengeit.android;

import com.googlecode.objectify.annotation.Index;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by christophe on 3/5/2015.
 */
public class Friend {
    @Getter @Setter
    private String username;

    @Index
    @Getter @Setter
    private Boolean accepted;
    
    @Index
    @Getter @Setter
    private Boolean blocked;

    public Friend() {
    }

    public Friend(Boolean accepted) {
        this.accepted=accepted;
        blocked= false;
    }

    public boolean equals (Friend friend){
        return this.getUsername().equals(friend.getUsername());
    }
}
