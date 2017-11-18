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
public class Ship extends SerializableEntity{
    private ShipType type;
    private Player owner;
    private int size;
    private List<Coordinate> listCoord;

    public Ship(ShipType type, Player owner, List<Coordinate> listCoord, int size) {
        this.type = type;
        this.owner = owner;
        this.listCoord = listCoord;
        this.size = size;
    }   
    
     public Ship(ShipType type, int size) {
        this.type = type;
        this.size = size;
        this.owner = null;
        this.listCoord = new ArrayList<>();
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

    public List<Coordinate> getListCoord() {
        return listCoord;
    }

    public void setListCoord(List<Coordinate> listCoord) {
        this.listCoord = listCoord;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    
    
}
