/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.rmi.server.UID;
import java.util.ArrayList;

/**
 *
 * @author Davy
 */
class Player extends LightPublicUser{
    private ArrayList<Ship> ships;
    private ArrayList<Mine> mines;

    public Player(UID id, String playerName) {
        super(id, playerName);
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public ArrayList<Mine> getMines() {
        return mines;
    }

    public void setMines(ArrayList<Mine> mines) {
        this.mines = mines;
    }
    
    
}
