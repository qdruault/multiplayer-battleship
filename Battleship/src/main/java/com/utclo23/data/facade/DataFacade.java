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
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Message;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.Owner;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.StatGame;
import java.io.File;

import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facade for the data module
 * @author Davy
 */
public class DataFacade implements IDataCom, IDataIHMTable, IDataIHMMain {
    /**
     * Communication facade
     */
    private ComFacade comfacade;
    /**
     * test mode 
     * (useful for unit test to disable several features)
     */
    private  boolean testMode;
    /**
     * user mediator
     */
    private UserMediator userMediator;
    
    /**
     * game mediator
     */
    private GameMediator gameMediator;

    public  boolean isTestMode() {
        return testMode;
    }

    public  void setTestMode(boolean testMode) {
        this.testMode = testMode;
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

        //creation of the save directory if it doesn't exist
        File saveDir = new File(Configuration.SAVE_DIR);
        saveDir.mkdirs();
        
    }

    public ComFacade getComfacade() {
        return comfacade;
    }

    public void setComfacade(ComFacade comfacade) {
        this.comfacade = comfacade;
    }

    
    /**
     * Get the user mediator
     * @return mediator
     */
    public UserMediator getUserMediator() {
        return userMediator;
    }

    /**
     * Get the game mediator
     * @return  mediator
     */
    public GameMediator getGameMediator() {
        return gameMediator;
    }

    /**
     * Add new game
     * @param game 
     */
    @Override
    public void addNewGame(StatGame game) {
        try{
            this.gameMediator.addNewGame(game);
        } catch(RuntimeException e) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Set the ennemy ships
     * @param ships 
     */
    @Override
    public void setEnnemyShips(List<Ship> ships) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /***
     * Forwad coordinates
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
     * add connected user
     * @param user
     */
    @Override
    public void addConnectedUser(LightPublicUser user) {
        try{
            this.userMediator.addConnectedUser(user);
        } catch(RuntimeException e) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * remove connected user
     * @param user
     */
    @Override
    public void removeConnectedUser(LightPublicUser user) {
        try{
            this.userMediator.removeConnectedUser(user);
        } catch(RuntimeException e) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.WARNING, e.getMessage());
        }

    }

    /**
     * forward message
     * @param msg 
     */
    @Override
    public void forwardMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * get a owner profile
     * @return my owner profile
     */
    public Owner getMyOwnerProfile() {
        try {
            return this.userMediator.getMyOwnerProfile();
        }
        catch (Exception e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    /**
     * get Public user profile
     * @return public profile
     */
    @Override
    public PublicUser getMyPublicUserProfile() {
        try {
            return this.userMediator.getMyPublicUserProfile();
        }
        catch (Exception e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /**
     * Update game list
     * @param user
     * @param id
     * @param role 
     */
    @Override
    public void updateGameList(LightPublicUser user, String id, String role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * getShips
     * @return 
     */
    @Override
    public List<Ship> getShips() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Set a given ship
     * @param ship 
     */
    @Override
    public void setShip(Ship ship) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attack a given location
     * @param coords
     * @return success/failure
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
     * @return 
     */
    @Override
    public List<Event> getPreviousBoard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return 
     */
    @Override
    public List<Event> getNextBoard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * send a chat message 
     * @param text 
     */
    @Override
    public void sendMessage(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get the current game
     * @return 
     */
    @Override
    public Game getGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param playerName
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param fileImage
     * @throws java.lang.Exception
     * @para
     */
    @Override
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate, String fileImage) throws DataException {
        this.userMediator.createUser(playerName, password, firstName, lastName, birthDate, fileImage);
    }

   /**
    * updte user
    * @param password
    * @param firstName
    * @param lastName
    * @param birthDate
    * @param fileImage
    * @throws DataException 
    */
     @Override
    public void updateUser(String password, String firstName, String lastName, Date birthDate, String fileImage) throws DataException {

        this.userMediator.updateUser(password, firstName, lastName, birthDate, fileImage);
    }
    
    
    /**
     * get a user profile
     * @param id
     * @return 
     */
    @Override
    public PublicUser getPublicUserProfile(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * get game list
     * @return list of games
     */
    @Override
    public List<StatGame> getGameList() {
        return this.gameMediator.getGamesList() ;
    }

    /**
     * Create a game
     * @param name
     * @param spectator
     * @param spectatorChat
     * @param type 
     */
    @Override
    public void createGame(String name, boolean spectator, boolean spectatorChat, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  /***
   * 
   * @param username
   * @param password
   * @throws DataException 
   */
    @Override
    public void signin(String username, String password) throws DataException {
            this.userMediator.signIn(username, password);
        
    }

 /***
  * 
  * @throws DataException 
  */
    @Override
    public void signOut()throws DataException{
        this.userMediator.singOut();     
    }

    /**
     * get users who are connected
     * @return 
     */
    @Override
    public List<LightPublicUser> getConnectedUsers() {
        return this.userMediator.getConnectedUsers();
    }

    /**
     * Test
     * @param args 
     */
    public static void main(String[] args) {
        try {
            DataFacade df = new DataFacade();
            ComFacade cf = new ComFacade();
            
            df.setComfacade(cf);
            
            df.createUser("konam", "password", "DAVID", "KONAM", new Date(), "C:\\Users\\Davy\\Pictures\\avatar.png");
            df.signin("konam", "password");
            df.signOut();
        } catch (Exception e) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.SEVERE, e.getMessage());
        }

    }
}
