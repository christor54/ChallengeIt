package com.challengeit.android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


public class Challenge implements Serializable {

    @Getter @Setter
    private String title;

    @Getter @Setter
    private Message message;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private Date deadline;

    @Getter @Setter
    private int pointValue;

    @Getter @Setter
    private ArrayList<Response> responses;

    @Getter @Setter
    private Date lastUpdate;

    public Challenge(){}

    public boolean equals(Challenge challenge) {
        return this.getTitle().equals(challenge.getTitle());
    }

    public boolean equals(String challengeTitle) {
        return this.getTitle().equals(challengeTitle);
    }

    public void addResponse (Response response){
        //Many responses from the same autho
        if(responses==null)
            responses = new ArrayList<>();
        responses.add(response);
    }
}
