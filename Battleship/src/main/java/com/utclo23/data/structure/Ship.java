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
public class Ship {
    private ShipType type;
    private Player owner;
    private ArrayList<Integer[]> listCoord;

    public Ship(ShipType type, Player owner, ArrayList<Integer[]> listCoord) {
        this.type = type;
        this.owner = owner;
        this.listCoord = listCoord;
    }    
    
    public ShipType getType() {
        return type;
    }

    public void setType(ShipType type) {
        this.type = type;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ArrayList<Integer[]> getListCoord() {
        return listCoord;
    }

    public void setListCoord(ArrayList<Integer[]> listCoord) {
        this.listCoord = listCoord;
    }
    
    
}
