/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

/**
 * Mine that represents a shoot
 * @author Davy
 */
public class Mine extends Event {
    private Player owner;
    private Coordinate coord;
    private MineResult result;
    
    /**
     * constructor
     * @param owner
     * @param coord 
     */
    public Mine(Player owner, Coordinate coord)
    {
        this.owner = owner;
        this.coord = coord;
        this.result = MineResult.UNKNOWN;        
    }

    /**
     *
     */
    public Mine() {
    }
    
    /**
     *
     * @return
     */
    public MineResult getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    public void setResult(MineResult result) {
        this.result = result;
    }

    /**
     * get user that uses it
     * @return owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * set  user
     * @param owner 
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * get current coordinates
     * @return 
     */
    public Coordinate getCoord() {
        return coord;
    }

    
    /**
     * set coordinates
     * @param coord 
     */
    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }
    
    
}
