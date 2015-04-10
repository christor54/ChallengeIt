package com.challengeit.android;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by christophe on 6/21/2014.
 */
@Cache(expirationSeconds= Consts.CACHE_PERIOD)
@Entity (name="UserLocal")
public class UserLocal implements Serializable {
    @Id
    @Getter private Long id;

    @Index
    @Getter @Setter private String username;

    @Index
    @Getter @Setter private String password;

    @Index
    @Getter @Setter private int score;

    @Index
    @Getter @Setter private List<Friend> friends;

    @Index
    @Getter @Setter private List<Master> masterList;

    @Index
    @Getter @Setter private List<Player> playerList;

    @Index
    @Getter @Setter private List<Invitation> friendInvitations;

    @Index
    @Getter @Setter private Date creationDate;


    public UserLocal() {
        friends = new ArrayList<Friend>();
        masterList = new ArrayList<Master>();
        playerList = new ArrayList<Player>();
        creationDate = new Date();
    }

    public UserLocal(String username) {
        this.username = username;
        friends = new ArrayList<Friend>();
        masterList = new ArrayList<Master>();
        creationDate = new Date();
    }


    public UserLocal(String username, String password) {
        this.username = username;
        this.password = password;
        friends = new ArrayList<Friend>();
        masterList = new ArrayList<Master>();
        creationDate = new Date();

    }

    public void addFriend(Friend friend){
        if(this.friends==null){
            friends = new ArrayList<Friend>();
        }
        if(!listAlreadyContains(friends, friend))
            friends.add(friend);
    }

    private boolean listAlreadyContains(List<Friend> friends, Friend newFriend) {
        Iterator<Friend> iterator = friends.iterator();
        while(iterator.hasNext()) {
            if (iterator.next().equals(newFriend)) {
                return true;
            }
        }
        return false;
    }

    public void removeFriend (Friend friend){
        Iterator<Friend> iterator = friends.iterator();
        while(iterator.hasNext()) {
            if (iterator.next().equals(friend)) {
                iterator.remove();
            }
        }
    }

    public void addInvitation(Invitation invitation) throws Exception {
        if(this.friendInvitations==null){
            friendInvitations = new ArrayList<Invitation>();
        }
        listAlreadyContains(friendInvitations, invitation);//check if the friend wasn't already on the list
        friendInvitations.add(invitation);

    }

    private void listAlreadyContains(List<Invitation> invitations, Invitation invitation) throws Exception {
        Iterator<Invitation> iterator = invitations.iterator();
        while(iterator.hasNext()) {
            if (iterator.next().equals(invitation)) {
                throw new Exception("friend already added");
            }
        }
    }

    public void removeInvitation (Invitation invitation){
        Iterator<Invitation> iterator = friendInvitations.iterator();
        Invitation current;
        while(iterator.hasNext()) {
            current = iterator.next();
            if (current.equals(invitation)) {
                iterator.remove();
            }
        }
    }

    public void addMaster (Master master)  {
        if (this.masterList == null) {
            masterList = new ArrayList<Master>();
        }
        masterList.add(master);
    }

    public void addPlayer (Player player){
        if(this.playerList==null){
            playerList = new ArrayList<Player>();
        }
        playerList.add(player);
    }

    public void removeMaster(Master master){
        Iterator iterator = masterList.iterator();
        while(iterator.hasNext()){
            if(((Master) iterator.next()).isEqual(master)){
                iterator.remove();
            }
        }
    }

    public List<String> getAllFriendsUsername(){
        List<String> list = new ArrayList<String>();
        String username;
        if(friends==null)
            return null;
        for(Friend friend : friends){
            friend.getUsername();
            username = friend.getUsername();
            list.add(username);
        }
        return list;
    }

    public List<String> getFriendsUsername(){
        List<String> list = new ArrayList<String>();
        String username;
        for(Friend friend : friends){
            if(friend.getAccepted()) {
                friend.getUsername();
                username = friend.getUsername();
                list.add(username);
            }
        }
        return list;
    }
    //TO BE TESTED
    public void acceptFriend(String usernameFriendAccepted) {
        //Set accepted to true to the friend of the friends list named usernameFriendAccpted
        Friend friend = null;
        Iterator<Friend> iterator = friends.iterator();
        while(iterator.hasNext()) {
            friend= iterator.next();
            if (friend.getUsername().equals(usernameFriendAccepted)) {
                friend.setAccepted(true);
            }
        }
    }
}
