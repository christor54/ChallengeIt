package com.challengeit.android;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by christophe on 2/20/2015.
 */
public class MasterWithGame {
    @Getter @Setter
    private Master master;

    @Getter @Setter
    private Game game;

    public MasterWithGame() {
    }
}
