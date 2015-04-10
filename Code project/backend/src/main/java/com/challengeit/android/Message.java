package com.challengeit.android;

import java.io.Serializable;
import java.util.List;

/**
* Created by christophe on 5/13/2014.
*/
public class Message implements Serializable{

    private String text;
    private String mediaURL;
    private int typeMedia=0; //1 image ; 2 video; 3 audio
    private List<String> attachedFilesURL;

    public Message() {
    }

    public Message(String text, String mediaURL, int typeMedia) {
        this.text = text;
        this.mediaURL = mediaURL;
        this.typeMedia = typeMedia;

    }

    public List<String> getAttachedFilesURL() {
        return attachedFilesURL;
    }

    public void setAttachedFilesURL(List<String> attachedFilesURL) {
        this.attachedFilesURL = attachedFilesURL;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public int getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(int typeMedia) {
        this.typeMedia = typeMedia;
    }

}
