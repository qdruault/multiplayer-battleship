/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.com.ComFacade;
import com.utclo23.data.configuration.Configuration;
import com.utclo23.data.module.DataException;
import com.utclo23.data.module.GameMediator;
import com.utclo23.data.module.UserMediator;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Event;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Message;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.Owner;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.StatGame;
import com.utclo23.ihmmain.facade.IHMMainFacade;

import com.utclo23.ihmtable.IIHMTableToData;

import java.io.File;
import java.io.IOException;
import java.net.InterfaceAddress;
import java.util.ArrayList;

import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 * Facade for the data module
 *
 * @author Davy
 */
public class DataFacade implements IDataCom, IDataIHMTable, IDataIHMMain {

    /**
     * Communication facade
     */
    private ComFacade comfacade;
    private IHMMainFacade ihmMainFacade;
    private IIHMTableToData ihmTablefacade;

    /**
     * test mode (useful for unit test to disable several features)
     */
    private boolean testMode;
    /**
     * user mediator
     */
    private UserMediator userMediator;

    /**
     * game mediator
     */
    private GameMediator gameMediator;

    /**
     * Get the test mode
     *
     * @return testMode true if test mode is activated
     */
    public boolean isTestMode() {
        return testMode;
    }

    /**
     * Set the test mode
     *
     * @param testMode
     * @param boolean the value to set to testMode parameter
     */
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    /**
     *
     * @return
     */
    public IHMMainFacade getIhmMainFacade() {
        return ihmMainFacade;
    }

    /**
     *
     * @return
     */
    public IIHMTableToData getIhmTablefacade() {
        return ihmTablefacade;
    }

    /**
     * Constructor
     */
    public DataFacade() {
        /**
         * Construction of mediators by giving them a reference to this facade
         */
        this.userMediator = new UserMediator(this);
        this.gameMediator = new GameMediator(this);
        this.testMode = false;
        this.comfacade = null;
        this.ihmMainFacade = null;
        this.ihmTablefacade = null;

        //creation of the save directory if it doesn't exist
        File saveDir = new File(Configuration.SAVE_DIR);
        saveDir.mkdirs();

    }

    /**
     * Get the communication facade parameter
     *
     * @return communication facade
     */
    public ComFacade getComfacade() {
        return comfacade;
    }

    /**
     *
     * @param comFacade
     * @param ihmTableToData
     * @param ihmMainFacade
     */
    public void setFacadeLinks(
            ComFacade comFacade,
            IIHMTableToData ihmTableToData,
            IHMMainFacade ihmMainFacade
    ) {
        this.comfacade = comFacade;
        this.ihmMainFacade = ihmMainFacade;
        this.ihmTablefacade = ihmTableToData;

    }

    /**
     * Update user playername
     *
     * @param playername playname
     * @throws DataException if there is an error in updating
     */
    @Override
    public void updatePlayername(String playername) throws DataException {

        this.userMediator.updatePlayername(playername);
    }

    /**
     *
     * @param firstName
     * @throws DataException
     */
    @Override
    public void updateFirstname(String firstName) throws DataException {

        this.userMediator.updateFirstname(firstName);
    }

    /**
     *
     * @param lastName
     * @throws DataException
     */
    @Override
    public void updateLastname(String lastName) throws DataException {

        this.userMediator.updateLastname(lastName);
    }

    /**
     *
     * @param birthdate
     * @throws DataException
     */
    @Override
    public void updateBirthdate(Date birthdate) throws DataException {

        this.userMediator.updateBirthdate(birthdate);
    }

    /**
     *
     * @param fileImage
     * @throws DataException
     */
    @Override
    public void updateFileImage(String fileImage) throws DataException {

        this.userMediator.updateFileImage(fileImage);
    }

    /**
     *
     * @param password
     * @throws DataException
     */
    @Override
    public void updatePassword(String password) throws DataException {

        this.userMediator.updatePassword(password);
    }

    /**
     * Get the user mediator
     *
     * @return mediator
     */
    public UserMediator getUserMediator() {
        return userMediator;
    }

    /**
     * Get the game mediator
     *
     * @return mediator
     */
    public GameMediator getGameMediator() {
        return gameMediator;
    }

    /**
     * Add new game
     *
     * @param game
     */
    @Override
    public void addNewGame(StatGame game) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Data | new game received");

        this.gameMediator.addNewGame(game);
        if (!this.testMode) {

            try {
                this.ihmMainFacade.refreshGameList();
            } catch (IOException ex) {
                Logger.getLogger(DataFacade.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            }
        }
    }

    /**
     * Set the ennemy ships
     *
     * @param ships the ships to set as a list
     *
     */
    @Override
    public void setEnnemyShips(List<Ship> ships) {

        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | set ennemy ships");

        this.gameMediator.setEnnemyShips(ships);
    }

    /**
     * *
     * Forwad coordinates
     *
     * @param mine
     */
    @Override
    public void forwardCoordinates(Mine mine) {
        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | forward coordinates");
        this.gameMediator.forwardCoordinates(mine);
    }

    /**
     * Notify that player leaves game Different behavior when a player and an
     * observer leave a game and when the player who leaves is or isn't an host.
     */
    @Override
    public void leaveGame() {
        String role = this.gameMediator.getOwnerStatus();
        if (!role.equals("spectator") && this.gameMediator.getCurrentGame()!=null) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | leave game");

            this.comfacade.leaveGame(this.gameMediator.getCurrentGame().getRecipients(this.getMyPublicUserProfile().getPlayerName()));
            this.gameMediator.leaveGame();
            try {
                this.ihmMainFacade.toMenu();
            } catch (IOException ex) {
                Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data " + ex.getMessage());
            }

            this.opponentHasLeftGame();
        } else {
            this.ihmTablefacade.spectatorLeaveGame();
        }
    }

    /**
     * Notify that opponent has left the game and gives the owner the win
     */
    @Override
    public void opponentHasLeftGame() {
        Logger.getLogger(DataFacade.class.getName()).info("data | opponent has left");

        
        if (!this.gameMediator.isFinishedGame()) {
            try {
                this.gameMediator.defWin();
            } catch (DataException e) {
                Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data " + e.getMessage());
            }
            this.gameMediator.leaveGame();
            this.ihmTablefacade.opponentHasLeftGame();
        }
    }
    
    @Override
    public void removeGame(String id)
    {
        this.gameMediator.removeGame(id);
    }

    /**
     * Notify that connection is lost
     */
    @Override
    public void connectionLostWithOpponent() {

        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | connection lost");

        //stats.
        this.gameMediator.setWinner(null);

        this.gameMediator.getCurrentGame().getStatGame().setGameAbandonned(true);
        this.gameMediator.leaveGame();
        this.ihmTablefacade.connectionLostWithOpponent();
    }

    /**
     * Request for a connection to a specific game hosted by another player.
     *
     * @param gameID ID of the game you want to connect to
     * @param role role you want to play int this game (obs, player)
     */
    public void gameConnectionRequest(String gameID, String role) {
        StatGame game = this.gameMediator.getGame(gameID);
        if (!this.isTestMode()) {
            this.comfacade.connectionToGame(game, role);
        }
    }

    /**
     *
     * Add connected user
     *
     * @param user user to add as connected
     *
     */
    @Override
    public void addConnectedUser(LightPublicUser user) {

        this.userMediator.addConnectedUser(user);

        try {
            this.ihmMainFacade.refreshUserList();
        } catch (Exception ex) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, ex.getMessage());
        }

    }

    /**
     * Remove connected user
     *
     * @param user user to removed as connected
     */
    @Override
    public void removeConnectedUser(LightPublicUser user) {

        this.userMediator.removeConnectedUser(user);

        try {
            this.ihmMainFacade.refreshUserList();
        } catch (Exception ex) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, ex.getMessage());
        }

    }

    /**
     * Forward a message
     *
     * @param msg message to forward
     */
    @Override
    public void forwardMessage(Message msg) {
        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | forward message");

        this.gameMediator.forwardMessage(msg);

    }

    /**
     * Get my owner profile
     *
     * @return my owner profile
     */
    public Owner getMyOwnerProfile() {

        return this.userMediator.getMyOwnerProfile();

    }

    /**
     * Get my public user profile
     *
     * @return my public profile
     */
    @Override
    public PublicUser getMyPublicUserProfile() {

        PublicUser usr = this.userMediator.getMyPublicUserProfile();
        try {
            usr.setNumberDefeats(this.getNumberDefeats());

            usr.setNumberVictories(this.getNumberVictories());
            usr.setNumberAbandons(this.getNumberAbandons());

        } catch (DataException ex) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usr;

    }

    /**
     *
     * Update game list as a new user has joined it.
     *
     * @param user the new user who has joined
     * @param id id of the stat game
     * @param role role of the new user
     */
    @Override
    public void updateGameList(LightPublicUser user, String id, String role) throws DataException {
        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | update game list " + user.getPlayerName() + " " + id + " " + role);
        this.gameMediator.updateGameList(user, id, role);
    }

    /**
     * Get ships to place
     *
     * @return the list of ships to place
     *
     */
    @Override
    public List<Ship> getTemplateShips() throws DataException {
        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | get template ships");

        if (this.gameMediator.getCurrentGame() != null) {
            return this.gameMediator.getCurrentGame().getTemplateShips();
        } else {
            throw new DataException("Data : no current game");
        }
    }

    /**
     * Set a given ship
     *
     * @param ship the ship to set
     *
     */
    public void setShip(Ship ship) throws DataException {
        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | set ship");

        this.gameMediator.setPlayerShip(ship);

    }

    /**
     * Attack a given location
     *
     * @param coords the location to attack
     * @return Pair<Integer, Ship>
     * Integer = 0 if the mine is not in a right place ; Integer = 1 if the mine
     * is in the place of a ship. Ship = null if the ship isn't destroyed ; ship
     * is a ship if this ship is destroyed
     *
     */
    @Override
    public Pair<Integer, Ship> attack(Coordinate coords, boolean isAttack, Player playerWhoPutTheMine) {

        try {

            return this.gameMediator.attack(coords, isAttack, playerWhoPutTheMine);

        } catch (Exception ex) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | " + ex.getMessage());
            return null;
        }
    }

    /**
     *
     * Get the previous board
     *
     * @return the previous board as a list of events such as mines, message
     *
     */
    @Override
    public List<Event> getPreviousBoard() {
        return new ArrayList<Event>();
    }

    /**
     * Get the next board
     *
     * @return the next board as a list of events such as mines, messag
     */
    @Override
    public List<Event> getNextBoard() {
        return new ArrayList<Event>();
    }

    /**
     * Send a chat message
     *
     * @param text the message to send
     */
    @Override
    public void sendMessage(String text) {

        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | send  message " + text);

        this.gameMediator.sendMessage(text);
    }

    /**
     * Get the current game
     *
     * @return the current game
     *
     */
    @Override
    public Game getGame() {
        return this.gameMediator.getCurrentGame();
    }

    /**
     * Create a user
     *
     * @param playerName the pseudo of the new player
     * @param password the password of the new player
     * @param firstName the first name of the new player
     * @param lastName the last name of the new player
     * @param birthDate the birth date of the new player
     * @param fileImage the avatar of the new user
     * @throws DataException if the playername or password is empty, or if the
     * account already exists
     */
    @Override
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate, String fileImage) throws DataException {
        this.userMediator.createUser(playerName, password, firstName, lastName, birthDate, fileImage);
    }

    /**
     * Update user information
     *
     * @param password the new password
     * @param firstName the new first name
     * @param lastName the new last name
     * @param birthDate the new birthdate
     * @param fileImage the new avatar
     * @throws DataException if there is an error in updating
     */
    @Override
    public void updateUser(String password, String firstName, String lastName, Date birthDate, String fileImage) throws DataException {

        this.userMediator.updateUser(password, firstName, lastName, birthDate, fileImage);
    }

    /**
     *
     * Get a user profile
     *
     * @param id the id of the user to get his profile
     *
     */
    @Override
    public void askPublicUserProfile(String id) {
        this.comfacade.getPublicUserProfile(id);
    }

    /**
     * Get list of games
     *
     * @return list of games as StatGame
     *
     */
    @Override
    public List<StatGame> getGameList() {
        return this.gameMediator.getGamesList();
    }

    /**
     * Create a game
     *
     * @param name the name of the game created
     * @param spectator are spectators allowed or not
     * @param spectatorChat are spectators allowed to chat or not
     * @param type type of the game created
     */
    @Override
    public Game createGame(String name, boolean computerMode, boolean spectator, boolean spectatorChat, GameType type) throws DataException {

        return this.gameMediator.createGame(name, computerMode, spectator, spectatorChat, type);

    }

    /**
     * @param username
     * @param password
     * @throws DataException if the user is already connected, there is a
     * problem in saving or reading file, or if the password is incorrect
     */
    @Override
    public void signin(String username, String password) throws DataException {
        this.userMediator.signIn(username, password);

    }

    /**
     *
     * Sign out of the application
     *
     * @throws DataException if there is no connected user
     *
     */
    @Override
    public void signOut() throws DataException {
        this.userMediator.signOut();
    }

    /**
     *
     * Get users who are connected
     *
     * @return the list of connected users
     *
     */
    @Override
    public List<LightPublicUser> getConnectedUsers() {
        return this.userMediator.getConnectedUsers();
    }

    /**
     *
     * get the discovery nodes
     *
     * @return
     */
    @Override
    public List<String> getIPDiscovery() {
        return this.userMediator.getIPDiscovery();
    }

    /**
     * set the discovery nodes
     *
     * @param discoveryNodes
     * @throws com.utclo23.data.module.DataException
     *
     */
    @Override
    public void setIPDiscovery(List<String> discoveryNodes) throws DataException {
        this.userMediator.setIPDiscovery(discoveryNodes);
    }

    /**
     *
     * @param profile
     */
    public void receivePublicUserProfile(PublicUser profile) {
        try {
            this.ihmMainFacade.recievePublicUserProfile(profile);
        } catch (IOException ex) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, ex.getMessage());
        }
    }

    /**
     *
     * @param id
     * @param role
     */
    @Override
    public void gameConnectionRequestGame(String id, String role) {

        this.gameMediator.gameConnectionRequestGame(id, role);
    }

    /**
     *
     * @param netinterface
     */
    @Override
    public void setNetworkInterface(InterfaceAddress netinterface) {

        if (this.getComfacade() != null) {
            this.getComfacade().setUsedInterface(netinterface);
        }
    }

    /**
     *
     * @param game
     * @throws DataException
     */
    @Override
    public void receptionGame(Game game) throws DataException {
        Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | reception game");

        if (game != null) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.INFO, null, "data | game not null");
            this.gameMediator.receptionGame(game);

        } else {
            throw new DataException("Error in Data : no game received");
        }

    }

    /**
     *
     * @throws DataException
     */
    @Override
    public void connectionImpossible() throws DataException {
        this.gameMediator.connectionImpossible();
    }

    /**
     *
     * @param gameid
     * @return
     */
    @Override
    public List<Ship> getInitialBoardFromGameId(String gameid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * send number of victories
     *
     * @return int number of victories
     * @throws DataException
     */
    @Override
    public int getNumberVictories(GameType type) throws DataException {
        return this.userMediator.getNumberVictories(type) ;
    }

    /**
     *
     * send number of defeats
     *
     * @return int number of defeats
     * @throws DataException
     */
    @Override
    public int getNumberDefeats(GameType type) throws DataException {
        return this.userMediator.getNumberDefeats(type) ;
    }

    /**
     *
     * send number of abandons
     *
     * @return int number of abandons
     * @throws DataException
     */
    @Override
    public int getNumberAbandons(GameType type) throws DataException {
        return this.userMediator.getNumberAbandons(type) ;
    }
}
