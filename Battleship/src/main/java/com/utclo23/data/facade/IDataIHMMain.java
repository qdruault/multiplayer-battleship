/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.*;
import java.net.InterfaceAddress;
import java.rmi.server.UID;
import java.util.List;
import java.util.Date;

/**
 *
 * @author Davy
 */
public interface IDataIHMMain {

    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate, String imageFile) throws DataException;
    public void updateUser(String password, String firstName, String lastName, Date birthDate, String imageFile) throws DataException;
    public void askPublicUserProfile(String id);
    
    public List<StatGame> getGameList();
    
    public Game createGame(String name, boolean computerMode, boolean spectator, boolean spectatorChat, GameType type) throws DataException;
    public void signin(String username, String password) throws DataException;
    public void signOut() throws Exception;
    public List<LightPublicUser> getConnectedUsers();
    public PublicUser getMyPublicUserProfile();

    public Owner getMyOwnerProfile();
    
    public List<String> getIPDiscovery();
    public void setIPDiscovery(List<String> discoveryNodes) throws DataException;
    
    public void gameConnectionRequestGame(String id, String role);

    
     public void setNetworkInterface(InterfaceAddress net_interface) ;
     
    public void updatePlayername(String playername) throws DataException;
    public void updateFirstname(String firstname) throws DataException;
    public void updateLastname(String lastname) throws DataException;
    public void updateBirthdate(Date birthdate) throws DataException;
    public void updateFileImage(String fileImage) throws DataException;
    public void updatePassword(String password) throws DataException;



}