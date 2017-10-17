/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public class Owner {
    private PublicUser userIdentity;
    private String password;
    private ArrayList<String> discoveryNodes;
    private ArrayList<StatGame> savedGamesList;
    private ArrayList<StatGame> playedGamesList;
    private ArrayList<LightPublicUser> contactList;

    
    
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

    public ArrayList<String> getDiscoveryNodes() {
        return discoveryNodes;
    }

    public void setDiscoveryNodes(ArrayList<String> discoveryNodes) {
        this.discoveryNodes = discoveryNodes;
    }

    public ArrayList<StatGame> getSavedGamesList() {
        return savedGamesList;
    }

    public void setSavedGamesList(ArrayList<StatGame> savedGamesList) {
        this.savedGamesList = savedGamesList;
    }

    public ArrayList<StatGame> getPlayedGamesList() {
        return playedGamesList;
    }

    public void setPlayedGamesList(ArrayList<StatGame> playedGamesList) {
        this.playedGamesList = playedGamesList;
    }

    public ArrayList<LightPublicUser> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<LightPublicUser> contactList) {
        this.contactList = contactList;
    }
    
    
    
}
