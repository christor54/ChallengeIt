package com.challengeit.android;

import com.challengeit.android.MyExceptions.NoPlayerException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.challengeit.android.OfyService.ofy;

/** An endpoint class we are exposing */
@Api(name = "userLocalEndpoint", version = "v1",
        clientIds = {Configuration.WEB_CLIENT_ID,
                Configuration.ANDROID_CLIENT_ID,
                com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Configuration.AUDIENCE_ID},
        namespace = @ApiNamespace(ownerDomain = "android.challengeit.com",
        ownerName = "DareIt", packagePath="challengeit"
))

public class UserLocalEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(UserLocalEndpoint.class.getName());

    /**
     * This method gets the <code>UserLocal</code> object associated with the specified <code>id</code>.
     * @param user The id of the object to be returned.
     * @return The <code>UserLocal</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "get")
    public UserLocal get(User user) throws NotFoundException, UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);
        UserLocal userL = ofy().load().type(UserLocal.class).filter("username", user.getEmail()).first().now();
        if(userL==null)
            throw new NotFoundException("Could not find user with username: " + user.getEmail());
        return userL;
    }

    @ApiMethod(name = "getWithUsername", path="userlyfeboat/{username}")
    public UserLocal getWithUsername(User user, @Named("username")String username) throws NotFoundException, UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);
        UserLocal userL = ofy().load().type(UserLocal.class).filter("username", username).first().now();
        if(userL==null)
            throw new NotFoundException("Could not find user with username: " + username);
        return userL;
    }


    @ApiMethod(name = "insert")
    public UserLocal insert(User user, UserLocal userLocal) throws NotFoundException, UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);
        String username = userLocal.getUsername();//To be able to insert several user from API Explorer
        if(username==null)
            username= user.getEmail();
        checkAlreadyExists(username);
        userLocal.setUsername(username);
        ofy().save().entity(userLocal).now();
        return  ofy().load().entity(userLocal).now();
    }

    @ApiMethod(name = "acceptFriendInvitation")
    public UserLocal acceptFriendInvitation(User user, Invitation invitation) throws NotFoundException, UnauthorizedException {
        UserLocal userLocal = get(user);
        checkExists(invitation.getUsernameSender());

        //once the invitation has been accepted, the invitation is removed from the user and a friend is added instead.

        //add friend with the username of the one sending the invitation
        Friend friend = new Friend (true);
        friend.setUsername(invitation.getUsernameSender());
        userLocal.addFriend(friend);

        //make valide the friend object of the one sending the invitation
        UserLocal userSender = getWithUsername(user,invitation.getUsernameSender());
        userSender.acceptFriend(user.getEmail());

        //Save userSender
        ofy().save().entity(userSender).now();

        //remove invitation
        userLocal.removeInvitation(invitation);

        //Save userLocal
        ofy().save().entity(userLocal).now();
        return ofy().load().entity(userLocal).now();
    }


    /**
     * This inserts a new <code>UserLocal</code> object.
     * @param userLocal The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "update")
    public UserLocal update(User user,  UserLocal userLocal) throws NotFoundException, UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);

        String username = user.getEmail();
        checkExists(username);
        userLocal.setUsername(username);
        ofy().save().entity(userLocal).now();
        LOG.info("Calling update method");
        return userLocal;
    }

    @ApiMethod(name = "addFriend")
    public UserLocal addFriend(User user, @Named("friend_username") String friendUsername) throws Exception {
        MyUtils.checkUserValidityUserApi(user);

        UserLocal userSender = get(user);
        UserLocal userReceiver = null;
        try {
             userReceiver = getWithUsername(user,friendUsername);
        } catch (NotFoundException e) {
            throw new NotFoundException("username of the friend added doesn't exist");
        }

        //Add friend(friendUsernamme) in the list of friend of the sender with accepted = false.
        Friend friend = new Friend (false);
        friend.setUsername(friendUsername);
        userSender.addFriend(friend);   //checks if friend is not already in the list before adding
        ofy().save().entity(userSender).now();

        //Add invitation to the receiver
        Invitation invitation = new Invitation();
        invitation.setUsernameSender(user.getEmail());
        userReceiver.addInvitation(invitation);
        ofy().save().entity(userReceiver).now();

        return ofy().load().entity(userSender).now();
    }


    @ApiMethod(name = "listUsers", path="UserLocal/list")
    public CollectionResponse<UserLocal> listUsers(User user, @Named("count") int count) throws UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);

        List<UserLocal> records = ofy().load().type(UserLocal.class).limit(count).list();
        return CollectionResponse.<UserLocal>builder().setItems(records).build();
    }

    @ApiMethod(name = "addPlayer")
    public void addPlayer(User user, Player player) throws NotFoundException, MissingFormatArgumentException, UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);

        String username = player.getUsername();
        checkExists(username);
        if(player.getGameId()==null)
            throw new MissingFormatArgumentException("player doesn't have an gameId");
        UserLocal userLocal = get(user);
        userLocal.addPlayer(player);
        ofy().save().entity(userLocal).now();

    }

    @ApiMethod(name = "insertGame",
            path = "userLocal/game")
    public UserLocal insertGame(User user, Game game) throws NotFoundException, UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);
        UserLocal userLocal = get(user);

        if(game.getGameName()==null)
            throw new MissingFormatArgumentException("The game need a gameName");

        //Save the game to get an game id to affect to master
        ofy().save().entity(game).now();
        Game gameSaved = ofy().load().entity(game).safe();

        //Create Master with gameId of the game saved and add it to UserLocal
        Master master = new Master(game.getId());
        userLocal.addMaster(master);

        //Save the Updated UserLocal
        ofy().save().entity(userLocal).now();

        //insert the Player in the UserLocal invited into the game
        insertPlayerInUsers(game);

        return ofy().load().entity(userLocal).now();
    }


    @ApiMethod(name = "listMastersWithGame", path="UserLocal/listMasters")
    public  CollectionResponse<MasterWithGame> listMastersWithGame(User user) throws NotFoundException, UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);
        UserLocal userLocal = get(user);
        List<Master> masters = userLocal.getMasterList();
        if(masters==null)
            return null;
        List<MasterWithGame> mastersWithGame = new ArrayList<>();
        GameEndpoint gameEndpoint = new GameEndpoint();
        Game game =null;
        MasterWithGame masterWithGame;
        for(Master master : masters){
            game = gameEndpoint.get(user, master.getGameId());
            masterWithGame = new MasterWithGame();
            masterWithGame.setGame(game);
            masterWithGame.setMaster(master);
            mastersWithGame.add(masterWithGame);
        }
        return CollectionResponse.<MasterWithGame>builder().setItems(mastersWithGame).build();
    }


    @ApiMethod(name = "listPlayersWithGames", path="Userlocal/listPlayers")
    public  CollectionResponse<PlayerWithGame> listPlayersWithGame(User user) throws NotFoundException, NoPlayerException, UnauthorizedException {
        MyUtils.checkUserValidityUserApi(user);
        UserLocal userLocal = get(user);
        List<Player> players = userLocal.getPlayerList();
        if(players==null)
            throw new NoPlayerException("No player in this game");
        List<PlayerWithGame> playersWithGame = new ArrayList<>();
        PlayerWithGame playerWithGame;
        Game game;
        for (Player player : players) {
            game = GameEndpoint.get(user, player.getGameId());
            playerWithGame = new PlayerWithGame(player, game);
            playersWithGame.add(playerWithGame);
        }
        return CollectionResponse.<PlayerWithGame>builder().setItems(playersWithGame).build();
    }

    private void insertPlayerInUsers(Game game) throws NotFoundException {
        //for each user, add game into user.players
        List<Player> players = game.getPlayers();
        if(players==null)
            return;
        for(Player player : players){
            player.setGameId(game.getId());
            addPlayerInUser(player);
        }
    }
    private void addPlayerInUser(Player player) throws NotFoundException, MissingFormatArgumentException {
        String username = player.getUsername();
        checkExists(username);
        if(player.getGameId()==null)
            throw new MissingFormatArgumentException("player doesn't have a gameId");
        UserLocal userLocal = ofy().load().type(UserLocal.class).filter("username", player.getUsername()).first().now();
        userLocal.addPlayer(player);
        ofy().save().entity(userLocal).now();
    }

    private static void checkExists(String username)throws NotFoundException {
        try {
             ofy().load().type(UserLocal.class).filter("username", username).first().safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find user with name: " + username);
        }
    }

    private void checkAlreadyExists(String username)throws RuntimeException {
        UserLocal userLocal = ofy().load().type(UserLocal.class).filter("username", username).first().now();
        if(userLocal!=null)
            throw new RuntimeException("UserLyfeboat : "+ username+ " already exists");
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(UserLocal.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find user with ID: " + id);
        }
    }


}