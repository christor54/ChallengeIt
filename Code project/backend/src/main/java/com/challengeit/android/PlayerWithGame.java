package com.challengeit.android;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by christophe on 2/20/2015.
 */
public class PlayerWithGame {
    @Getter @Setter
    private Player player;

    @Getter @Setter
    private Game game;

    public PlayerWithGame() {
    }

    public PlayerWithGame(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

}
