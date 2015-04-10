package com.challengeit.android;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
* Created by christophe on 5/13/2014.
*/

public class Response implements Serializable{

    @Getter @Setter
    private Message message;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private int rate;

    @Getter @Setter
    private String author;

    public Response() {
    }

}
