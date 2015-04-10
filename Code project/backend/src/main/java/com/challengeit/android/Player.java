package com.challengeit.android;


import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


public class Player  implements Serializable {


    @Getter @Setter
    String playername;

    @Getter @Setter
    int score;

    @Getter @Setter
    Long gameId;

    @Getter @Setter
    String username; //To add player into user.players for each player added to a game

    @Index
    @Getter
    Date lastUpdate;

    @Getter @Setter
    ArrayList<Response> myResponses;


    public Player() {
    }

    public Player(Long gameId) {
        this.gameId= gameId;
        lastUpdate = new Date();
    }

    public Player(String username) {
        this.username=username;
        this.playername = username;
        lastUpdate = new Date();
    }

    public Player(String username, String playername) {
        this.username=username;
        this.playername = playername;
        lastUpdate = new Date();
    }

}
