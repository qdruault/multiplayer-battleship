/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;
import java.util.List;

/**
 *
 * @author lucillefargeau
 */
public class Coordinate extends SerializableEntity{
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o){
        if(o == this) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate c = (Coordinate) o;
        
        return ((c.getX() == this.getX()) && (c.getY() == this.getY()));
        
    }
    
    /**
     * Checks if the coordinate is taken by a ship of a list.
     * 
     * @param ships
     * @return 
     */
    public boolean isAllowed(List<Ship> ships) {
        if(!this.isInBoard()) {
            return false;
        }
        boolean available = true;
        for(int s = 0; s < ships.size(); s++) {
            if(ships.get(s).isCrossed(this)) {
                available = false;
                break;
            }
        }
        return available;
    }
    
    /**
     * Ckecks if the coordinate is within the board.
     * @return 
     */
    private boolean isInBoard() {
        return (x < 10 && x >= 0 && y < 10 && y >= 0);
    }
}
