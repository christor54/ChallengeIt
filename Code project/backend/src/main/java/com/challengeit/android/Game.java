package com.challengeit.android;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by christophe on 6/22/2014.
 */
@Cache(expirationSeconds= Consts.CACHE_PERIOD)
@Entity
@Index
public class Game implements Serializable {
    @Id
    @Getter
    Long id;

    @Index
    @Getter @Setter
    String gameName;

    @Index
    @Getter @Setter
    String description;

    @Index
    @Getter
    Date dateCreation;

    @Index
    @Getter @Setter
    ArrayList<Player> players;

    @Index
    @Getter @Setter
    ArrayList<Challenge> challenges;

    @Index
    @Getter @Setter
    int highestScore;

    public Game() {
    }

    public Game( String gameName) {
        this.gameName = gameName;
    }

    public Game (Game gameIn){
        this.gameName= gameIn.getGameName();
        this.description=gameIn.getDescription();
        this.dateCreation= new Date();
        this.highestScore= gameIn.getHighestScore();
        this.challenges= gameIn.getChallenges();
    }

    public void addPlayer(Player player) {
        if(players==null)
           players = new ArrayList<Player>();
        this.players.add(player);
    }

    public boolean addChallenge(Challenge challenge) {
        if(challenges==null)
            challenges= new ArrayList<Challenge>();
        if(!challengeAlreadyExist(challenges,challenge)) {
            challenges.add(challenge);
            return true;
        }
        else
            return false;
    }

    private boolean challengeAlreadyExist(ArrayList<Challenge> challenges, Challenge challenge) {
        for(Challenge challengeCurrent:challenges){
            if(challengeCurrent.equals(challenge))
                return true;
        }
        return false;
    }

    public void addResponse(String challengeTitle, Response response) {
        Iterator<Challenge> iterator = challenges.iterator();
        Challenge currrent;
        while(iterator.hasNext()){
            currrent= iterator.next();
            if(currrent.equals(challengeTitle)){
                currrent.addResponse(response);
            }
        }

    }
}
