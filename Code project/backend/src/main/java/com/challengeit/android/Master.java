package com.challengeit.android;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by christophe on 6/25/2014.
 */

public class Master implements Serializable {

    @Getter @Setter
    private Long gameId;

    @Getter @Setter
    private String gameName;

    @Getter @Setter
    private Date lastUpdate;

    public Master() {
    }

    public Master(Long id) {
        this.gameId = id;
        lastUpdate= new Date();
    }

    public Boolean isEqual (Master master){
        return master.getGameId()==master.getGameId();
    }
}