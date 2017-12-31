/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.*;
import java.net.InterfaceAddress;
import java.util.List;
import java.util.Date;

/**
 *
 * @author Davy
 */
public interface IDataIHMMain {

    /**
     *
     * @param playerName
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param imageFile
     * @throws DataException
     */
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate, String imageFile) throws DataException;

    /**
     *
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param imageFile
     * @throws DataException
     */
    public void updateUser(String password, String firstName, String lastName, Date birthDate, String imageFile) throws DataException;

    /**
     *
     * @param id
     */
    public void askPublicUserProfile(String id);
    
    /**
     *
     * @return
     */
    public List<StatGame> getGameList();
    
    /**
     *
     * @param name
     * @param computerMode
     * @param spectator
     * @param spectatorChat
     * @param type
     * @return
     * @throws DataException
     */
    public Game createGame(String name, boolean computerMode, boolean spectator, boolean spectatorChat, GameType type) throws DataException;

    /**
     *
     * @param username
     * @param password
     * @throws DataException
     */
    public void signin(String username, String password) throws DataException;

    /**
     *
     * @throws DataException
     */
    public void signOut() throws DataException;

    /**
     *
     * @return
     */
    public List<LightPublicUser> getConnectedUsers();

    /**
     *
     * @return
     */
    public PublicUser getMyPublicUserProfile();

    /**
     *
     * @return
     */
    public Owner getMyOwnerProfile();
    
    /**
     *
     * @return
     */
    public List<String> getIPDiscovery();

    /**
     *
     * @param discoveryNodes
     * @throws DataException
     */
    public void setIPDiscovery(List<String> discoveryNodes) throws DataException;
    
    /**
     *
     * @param id
     * @param role
     */
    public void gameConnectionRequestGame(String id, String role);

    /**
     *
     * @param netinterface
     */
    public void setNetworkInterface(InterfaceAddress netinterface);
     
    /**
     *
     * @param playername
     * @throws DataException
     */
    public void updatePlayername(String playername) throws DataException;

    /**
     *
     * @param firstname
     * @throws DataException
     */
    public void updateFirstname(String firstname) throws DataException;

    /**
     *
     * @param lastname
     * @throws DataException
     */
    public void updateLastname(String lastname) throws DataException;

    /**
     *
     * @param birthdate
     * @throws DataException
     */
    public void updateBirthdate(Date birthdate) throws DataException;

    /**
     *
     * @param fileImage
     * @throws DataException
     */
    public void updateFileImage(String fileImage) throws DataException;

    /**
     *
     * @param password
     * @throws DataException
     */
    public void updatePassword(String password) throws DataException;

    public int getNumberVictories(GameType type) throws DataException;
    public int getNumberDefeats(GameType type) throws DataException;
    public int getNumberAbandons(GameType type) throws DataException;

}
