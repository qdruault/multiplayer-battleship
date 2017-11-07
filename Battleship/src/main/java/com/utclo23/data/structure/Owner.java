/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class Owner extends SerializableEntity{
    private PublicUser userIdentity;
    private String password;
    private List<String> discoveryNodes;
    private List<StatGame> savedGamesList;
    private List<StatGame> playedGamesList;
    private List<LightPublicUser> contactList;
    private byte[] avatar;
    
    public Owner()
    {
        this.discoveryNodes = new ArrayList<>();
        this.savedGamesList = new ArrayList<>();
        this.playedGamesList = new ArrayList<>();
        
    }
    
    public PublicUser getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(PublicUser userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getDiscoveryNodes() {
        return discoveryNodes;
    }

    public void setDiscoveryNodes(List<String> discoveryNodes) {
        this.discoveryNodes = discoveryNodes;
    }

    public List<StatGame> getSavedGamesList() {
        return savedGamesList;
    }

    public void setSavedGamesList(List<StatGame> savedGamesList) {
        this.savedGamesList = savedGamesList;
    }

    public List<StatGame> getPlayedGamesList() {
        return playedGamesList;
    }

    public void setPlayedGamesList(List<StatGame> playedGamesList) {
        this.playedGamesList = playedGamesList;
    }

    public List<LightPublicUser> getContactList() {
        return contactList;
    }

    public void setContactList(List<LightPublicUser> contactList) {
        this.contactList = contactList;
    }


    
    
    
}
