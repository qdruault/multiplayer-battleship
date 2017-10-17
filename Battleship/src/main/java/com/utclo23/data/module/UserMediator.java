package com.utclo23.data.module;

import com.utclo23.data.structure.*;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Date;



/**
 *
 * @author wuxiaoda
 */
public class UserMediator {
    private ArrayList<LightPublicUser> listConnectedUser;
    private Owner owner;
    
    /**
     *
     * @param playerName
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param avatar
     */
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate);
    public LightPublicUser getLightPublicUser(UID id);
    public void signIn(String username, String password);
    public void singOut();
    public boolean addConnectedUser(LightPublicUser usr);
    public ArrayList<LightPublicUser> getConnectedUsers();
    public boolean removeConnectedUser();
}