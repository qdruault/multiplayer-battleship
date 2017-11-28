/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.rmi.server.UID;

/**
 * light profile
 * @author Davy
 */
public class LightPublicUser extends SerializableEntity{
    private String id;
    private String playerName;
    private byte[] avatarThumbnail;

    public LightPublicUser() {
        this.id ="";
        this.playerName ="";
        
    }

    public static LightPublicUser generateComputerProfile()
    {
        String id = new UID().toString();
        String playerName = "IA LO23";
        LightPublicUser computerProfile = new LightPublicUser(id, playerName);
        
        return computerProfile;
    }
    /**
     * constructor
     * @param id
     * @param playerName 
     */
    public LightPublicUser(String id, String playerName) {
        this.id = id;
        this.playerName = playerName;
        this.avatarThumbnail = null;
    }
    
    
    /**
     * get id 
     * @return uniq id 
     */
    public String getId() {
        return id;
    }

    /**
     * set id
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get player name
     * @return name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * set name
     * @param playerName 
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * get thumbnail
     * @return 
     */
    public byte[] getAvatarThumbnail() {
        return avatarThumbnail;
    }

    /**
     * set thumbnail
     * @param avatarThumbnail 
     */
    public void setAvatarThumbnail(byte[] avatarThumbnail) {
        this.avatarThumbnail = avatarThumbnail;
    }
    
    
    
}
