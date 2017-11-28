/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.rmi.server.UID;
import java.util.List;

/**
 *
 * @author Davy
 */
public class Player extends SerializableEntity{
    private List<Ship> ships;
    private List<Mine> mines;
    private LightPublicUser lightPublicUser;
    private boolean computer;

    public Player(LightPublicUser lightPublicUser) {
        super();
        this.lightPublicUser = lightPublicUser;
        this.computer = false;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Mine> getMines() {
        return mines;
    }

    public void setMines(List<Mine> mines) {
        this.mines = mines;
    }

    public LightPublicUser getLightPublicUser() {
        return lightPublicUser;
    }

    public boolean isComputer() {
        return computer;
    }

    public void setComputer(boolean computer) {
        this.computer = computer;
    }
    
    
}
