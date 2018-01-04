/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Davy
 */
public class Ship extends SerializableEntity{
    private ShipType type;
    private Player owner;
    private int size;
    private List<Coordinate> listCoord;
    
    /**
     *
     * @param type
     * @param owner
     * @param listCoord
     * @param size
     */
    public Ship(ShipType type, Player owner, List<Coordinate> listCoord, int size) {
        this.type = type;
        this.owner = owner;
        this.listCoord = listCoord;
        this.size = size;
    }   
    
    /**
     *
     * @param type
     * @param size
     */
    public Ship(ShipType type, int size) {
        this.type = type;
        this.size = size;
        this.owner = null;
        this.listCoord = new ArrayList<>();
    }   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.type);
        hash = 47 * hash + Objects.hashCode(this.owner);
        hash = 47 * hash + this.size;
        hash = 47 * hash + Objects.hashCode(this.listCoord);
        return hash;
    }

    /**
     *
     */
    public Ship() {
        this.type = ShipType.BATTLESHIP;
        this.size = 0;
        this.owner = null;
        this.listCoord = new ArrayList<>();
    }
     
    /**
     *
     * @return
     */
    public ShipType getType() {
        return type;
    }
    
    /**
     * Checks if a coordinate belongs to the ship.
     * 
     * @param coord
     * @return 
     */
    @JsonIgnore
    public boolean isCrossed(Coordinate coord) {
        for(int i = 0; i < listCoord.size(); i++){
            if(coord.equals(listCoord.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param type
     */
    public void setType(ShipType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public Player getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     */
    public List<Coordinate> getListCoord() {
        return listCoord;
    }

    /**
     *
     * @param listCoord
     */
    public void setListCoord(List<Coordinate> listCoord) {
        this.listCoord = listCoord;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }    
    
    @Override
    public boolean equals(Object obj) {
        
        boolean check = true;
       
       
     
        if (null==obj || getClass() != obj.getClass())
            check = false;
       
        Ship other = (Ship) obj;
        
        if (null == other || type != other.type)
            check =  false;
       
        if (null  == other || !owner.getLightPublicUser().getId().equals(other.getOwner().getLightPublicUser().getId()))
            check =  false;
        
        if (null == other || size != other.getSize())
            check =  false;
        
        if (null == other || !listCoord.equals(other.getListCoord()))
            check = false;
        
       return check;
    }
}
