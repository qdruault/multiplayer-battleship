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
    
    
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate){
        UID id = new UID();
        LightPublicUser lightPublicUser = new LightPublicUser(id, playerName);
        PublicUser publicUser = new PublicUser(lightPublicUser, lastName, firstName, birthDate);
        //TODO avatar:picture
        owner.setUserIdentity(publicUser);
        owner.setPassword(password);
    }
    
    public LightPublicUser getLightPublicUser(UID id){
        LightPublicUser lightPublicUser = null;
        int numberElement = listConnectedUser.size();
        for(int i = 0; i < numberElement ; i++){
            lightPublicUser = listConnectedUser.get(i);
            if(lightPublicUser.getId() == id){
                return lightPublicUser;
            }
        }
        return null; // When the id given doesn't existe in data base, return null
    }
    
    public void signIn(String username, String password);
    public void singOut();
    public boolean addConnectedUser(LightPublicUser usr);
    public ArrayList<LightPublicUser> getConnectedUsers();
    public boolean removeConnectedUser();
}