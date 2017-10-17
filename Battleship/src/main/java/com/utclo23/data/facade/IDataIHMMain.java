/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.data.structure.*;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Davy
 */
public interface IDataIHMMain {
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate);
    public PublicUser getPublicUserProfile(UID id);
    public ArrayList<StatGame> getGameList();
    public void createGame(String name, boolean spectator, boolean spectatorChat, String type);
    public void signin(String username, String password);
    public void signOut();
    public ArrayList<LightPublicUser> getConnectedUsers();
}

