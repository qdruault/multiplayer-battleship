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
    private Integer[] coord;
    
    public Mine(Player owner, int x, int y)
    {
        this.owner = owner;
        this.coord = new Integer[2];
        this.coord[0] = x;
        this.coord[1] = y;
        
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Integer[] getCoord() {
        return coord;
    }

    public void setCoord(Integer[] coord) {
        this.coord = coord;
    }
    
    
}
