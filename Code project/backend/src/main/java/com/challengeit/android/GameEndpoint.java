package com.challengeit.android;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.challengeit.android.OfyService.ofy;


/** An endpoint class we are exposing */
@Api(name = "gameEndpoint", version = "v1",
        clientIds = {Configuration.WEB_CLIENT_ID,
                Configuration.ANDROID_CLIENT_ID,
                com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Configuration.AUDIENCE_ID},
        namespace = @ApiNamespace(ownerDomain = "android.challengeit.com",
        ownerName = "DareIt", packagePath="challengeit"))
public class GameEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(GameEndpoint.class.getName());

    @ApiMethod(name = "get")
    public static Game get(User user, @Named("id") Long id) throws NotFoundException, UnauthorizedException {
        MyUtils.checkUserValidityGameApi(user);
        Game game = null;
        LOG.info("Calling getGame method");
        try {
            game = ofy().load().type(Game.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find user with ID: " + id);
        }
        return game;
    }

    /**
     * This method gets the <code>Game</code> object associated with the specified <code>id</code>.
     * @param gameName
     * @return The <code>Game</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getGame",
    path= "game/gamename")
    public Game getGame(User user, @Named("gameName") String gameName) throws UnauthorizedException {
        MyUtils.checkUserValidityGameApi(user);
        // Implement this function
        Game game =  ofy().load().type(Game.class).filter("gameName", gameName).first().now();
        LOG.info("Calling getGame method");
        return game;
    }

    /**
     * This inserts a new <code>Game</code> object.
     * @param game The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertGame")
    public Game insertGame(User user, Game game) throws UnauthorizedException {
        MyUtils.checkUserValidityGameApi(user);
        LOG.info("Calling insertGame method");
        ofy().save().entity(game).now();
        return game;
    }

    @ApiMethod(name = "addPlayerToGame")
    public Game addPlayerToGame(User user, @Named ("username")String username,
                                  @Named("gameId") Long gameId) throws NotFoundException, UnauthorizedException {
        MyUtils.checkUserValidityGameApi(user);
        LOG.info("Calling addPlayerToGame method");
        Game game = get(user,gameId);
//
//        //Create and player to the game
//        Player player = new Player(username, username);
//        game.addPlayer(player);
//
//        //Add player to the user
//        player.setGameId(gameId);//Important so that you can get PlayersWithGame with the UserAPI
//        UserLocalEndpoint.addPlayerInUser(player);
//
//        ofy().save().entity(game).now();
        return ofy().load().entity(game).now();
    }



    @ApiMethod(name ="addChallenge")
    public Game addChallenge (User user, @Named("game_Id") Long game_Id, Challenge challenge) throws Exception {
        MyUtils.checkUserValidityGameApi(user);
        LOG.info("Calling addChallenge method");
        Game game = ofy().load().type(Game.class).id(game_Id).now();
        if(game.addChallenge(challenge)==false)
            throw new Exception("challenge already exists");
        ofy().save().entity(game).now();
        return ofy().load().entity(game).now();
    }

    @ApiMethod(name = "respondToChallenge")
    public Game respondToChallenge (User user, @Named("gameId") Long gameId, @Named("challengeName") String challengeTitle, Response response) throws UnauthorizedException {
        MyUtils.checkUserValidityGameApi(user);
        LOG.info("Calling respondToChallenge method");
        Game game = ofy().load().type(Game.class).id(gameId).now();
        game.addResponse(challengeTitle,response);
        ofy().save().entity(game).now();
        return ofy().load().entity(game).now();
    }

    @ApiMethod(name = "reviewResponse")
    public void reviewResponse(User user, @Named("gameId") Long gameId, @Named("challengeName") String challengeTitle, @Named("author_username") String author) throws UnauthorizedException {
        MyUtils.checkUserValidityGameApi(user);

    }

    @ApiMethod(name = "listGames", path="game/list")
    public CollectionResponse<Game> listGames(User user, @Named("count") int count) throws UnauthorizedException {
        MyUtils.checkUserValidityGameApi(user);
        List<Game> games = ofy().load().type(Game.class).limit(count).list();
        return CollectionResponse.<Game>builder().setItems(games).build();
    }

    @ApiMethod(name = "listPlayers", path="player/list")
    public CollectionResponse<Player> listPlayers(User user, @Named("count") int count) throws UnauthorizedException {
        MyUtils.checkUserValidityGameApi(user);
        List<Player> players = null;
        return CollectionResponse.<Player>builder().setItems(players).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Game.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find user with ID: " + id);
        }
    }

}
