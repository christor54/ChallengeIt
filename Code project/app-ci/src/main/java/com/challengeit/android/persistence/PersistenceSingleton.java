package com.challengeit.android.persistence;

import com.challengeit.android.challengeit.userLocalEndpoint.model.Challenge;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Response;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Game;
import com.challengeit.android.challengeit.userLocalEndpoint.model.MasterWithGame;
import com.challengeit.android.challengeit.userLocalEndpoint.model.PlayerWithGame;
import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * To be used to persist temporary data while executing the application. It is kind of a cross-activity way of transferring data.
 */
public class PersistenceSingleton {
    private static PersistenceSingleton instance = null;

    @Getter @Setter
    private UserLocal userLocal;

    @Getter @Setter
    private List<PlayerWithGame> playersWithGame;

    @Getter @Setter
    private List<MasterWithGame> mastersWithGame;

    @Getter
    private PlayerWithGame playerClicked;

    @Getter
    private MasterWithGame masterClicked;

    @Getter @Setter
    private Game gameClicked;

    @Getter @Setter
    private Challenge challengeClicked;

    @Getter @Setter
    private Boolean isMasterClicked;

    @Getter @Setter
    private Response responseClicked;

    @Getter @Setter
    private Game gameAdded;

    protected PersistenceSingleton() {
        // Exists only to defeat instantiation.
    }
    public static PersistenceSingleton getInstance() {
        if(instance == null) {
            instance = new PersistenceSingleton();
        }
        return instance;
    }

    public void setMasterClicked(MasterWithGame masterWithGame){
        this.isMasterClicked=true;
        this.masterClicked=masterWithGame;
        gameClicked=masterWithGame.getGame();
    }

    public void setPlayerClicked(PlayerWithGame playerClicked){
        this.isMasterClicked=false;
        this.playerClicked=playerClicked;
        gameClicked=playerClicked.getGame();
    }


    public void add(MasterWithGame masterWithGame) {
        if(mastersWithGame==null)
            mastersWithGame= new ArrayList<>();
        mastersWithGame.add(masterWithGame);
    }
}