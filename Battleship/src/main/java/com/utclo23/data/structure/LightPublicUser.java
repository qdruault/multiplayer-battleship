/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.rmi.server.UID;

/**
 *
 * @author Davy
 */
public class LightPublicUser {
    private UID id;
    private String playerName;
    //TODO avatarThumbnal

    public LightPublicUser(UID id, String playerName) {
        this.id = id;
        this.playerName = playerName;
    }

    
    
    public UID getId() {
        return id;
    }

    public void setId(UID id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    
}
