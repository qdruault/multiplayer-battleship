/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

/**
 *
 * @author Davy
 */
public class Mine extends Event {
    private Player owner;
    private Coordinate coord;
    
    public Mine(Player owner, Coordinate coord)
    {
        this.owner = owner;
        this.coord = coord;
        
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }
    
    
}
