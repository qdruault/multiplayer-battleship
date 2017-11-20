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
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.StatGame;
import com.utclo23.ihmmain.facade.IHMMainFacade;

import com.utclo23.ihmtable.IHMTableFacade;
import com.utclo23.ihmtable.IIHMTableToData;

import java.io.File;


import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @return testMode true if test mode is activated
     */
    public  boolean isTestMode() {
        return testMode;
    }

    /**
     * Set the test mode
     * @param boolean the value to set to testMode parameter
     */
    public  void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public IHMMainFacade getIhmMainFacade() {
        return ihmMainFacade;
    }

    public IIHMTableToData getIhmTablefacade() {
        return ihmTablefacade;
    }

    /**
     * Constructor
     */
    public DataFacade() {
        System.out.println(this.getClass() + " Creation de la facade");

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
     * @return communication facade 
     */
    public ComFacade getComfacade() {
        return comfacade;
    }


    
   
    public void setFacadeLinks(
            ComFacade comFacade,
            IIHMTableToData ihmTableToData,
            IHMMainFacade ihmMainFacade
    ){
        this.comfacade = comFacade;
        this.ihmMainFacade = ihmMainFacade;
        this.ihmTablefacade = ihmTableToData;

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
<<<<<<< HEAD
     *
     * @param game
=======
     * @param game the game to add
>>>>>>> Data/javadoc
     */
    @Override
    public void addNewGame(StatGame game) {
      
            this.gameMediator.addNewGame(game);
     
    }

    /**
     * Set the ennemy ships

     * @param ships the ships to set as a list

     */
    @Override
    public void setEnnemyShips(List<Ship> ships) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * *
     * Forwad coordinates
     *
     * @param mine
     */
    @Override
    public void forwardCoordinates(Mine mine) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Notify that opponent has left
     */
    @Override
    public void opponentHasLeftGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Notify that connection is lost
     */
    @Override
    public void connectionLostWithOpponent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**

     * Add connected user
     * @param user user to add as connected

     */
    @Override
    public void addConnectedUser(LightPublicUser user) {
     
            this.userMediator.addConnectedUser(user);
        
    }

    /**
     * Remove connected user
     * @param user user to removed as connected
     */
    @Override
    public void removeConnectedUser(LightPublicUser user) {
        try {
            this.userMediator.removeConnectedUser(user);
        } catch (RuntimeException e) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.WARNING, e.getMessage());
        }

    }

    /**
     * Forward a message
     * @param msg message to forward
     */
    @Override
    public void forwardMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get my owner profile
     * @return my owner profile
     */
    public Owner getMyOwnerProfile() {
        
            return this.userMediator.getMyOwnerProfile();
       
    }

    /**
     * Get my public user profile
     * @return my public profile
     */
    @Override
    public PublicUser getMyPublicUserProfile() {
        try {
            return this.userMediator.getMyPublicUserProfile();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /**

     * Update game list as a new user has joined a game
     * @param user the new user who has joined
     * @param id id of the stat game 
     * @param role role of the new user
     */
    @Override
    public void updateGameList(LightPublicUser user, String id, String role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get ships to place
     * @return the list of ships to place

     */
    @Override
    public List<Ship> getShips() throws DataException{
        if(this.gameMediator.getCurrentGame()!=null)
        {
            return this.gameMediator.getCurrentGame().getTemplateShips();
        }
        else
        {
            throw new DataException("Data : no current game");
        }
    }

    /**
     * Set a given ship

     * @param ship the ship to set

     */
    @Override
    public void setShip(Ship ship) throws DataException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attack a given location

     * @param coords the location to attack
     * @return success/failure of the attack

     */
    @Override
    public boolean attack(Coordinate coords) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Notify that player leaves game
     */
    @Override
    public void leaveGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**

     * Get the initial board of a game
     * @param gameid the game from which we want the initial board
     * @return the initial board as a list of ships
     */
    @Override
    public List<Ship> getInitialBoardFromGameId(String gameid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**

     * Get the previous board
     * @return the previous board as a list of events such as mines, message

     */
    @Override
    public List<Event> getPreviousBoard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get the next board
     * @return the next board as a list of events such as mines, messag
     */
    @Override
    public List<Event> getNextBoard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**

     * Send a chat message 
     * @param text the message to send

     */
    @Override
    public void sendMessage(String text) {
       
    }

    /**
     * Get the current game

     * @return the current game

     */
    @Override
    public Game getGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Create a user
     * @param playerName the pseudo of the new player
     * @param password the password of the new player
     * @param firstName the first name of the new player
     * @param lastName the last name of the new player
     * @param birthDate the birth date of the new player
     * @param fileImage the avatar of the new user
     * @throws DataException if the playername or password is empty, or if the account already exists
     */
    @Override
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate, String fileImage) throws DataException {
        this.userMediator.createUser(playerName, password, firstName, lastName, birthDate, fileImage);
    }


   /**
    * Update user information
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

     * Get a user profile
     * @param id the id of the user to get his profile
     * @return the public user

     */
    @Override
    public PublicUser getPublicUserProfile(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    /** 
     * Get list of games
     * @return list of games as StatGame

     */
    @Override
    public List<StatGame> getGameList() {
        return this.gameMediator.getGamesList();
    }

    /**
     * Create a game

     * @param name the name of the game created
     * @param spectator are spectators allowed or not
     * @param spectatorChat are spectators allowed to chat or not
     * @param type type of the game created
     */
    @Override
    public Game createGame(String name, boolean spectator, boolean spectatorChat, GameType type) {
     
        return this.createGame(name, spectator, spectatorChat, type);
        
    }

    
    
    
    /**
     * *
     *
     * @param username
     * @param password
     * @throws DataException
=======
    /**
     * Sign in the application
     * @param username
     * @param password
     * @throws DataException if the user is already connected, there is a problem in saving or reading file, or if the password is incorrect
>>>>>>> Data/javadoc
     */
    @Override
    public void signin(String username, String password) throws DataException {
        this.userMediator.signIn(username, password);

    }

    /**

     * Sign out of the application
     * @throws DataException if there is no connected user

     */
    @Override
    public void signOut() throws DataException {
        this.userMediator.signOut();
    }

    /**

     * Get users who are connected
     * @return the list of connected users

     */
    @Override
    public List<LightPublicUser> getConnectedUsers() {
        return this.userMediator.getConnectedUsers();
    }

    /**

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

     */
    @Override
    public void setIPDiscovery(List<String> discoveryNodes) throws DataException {
            this.userMediator.setIPDiscovery(discoveryNodes);
    }
}
