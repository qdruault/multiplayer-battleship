/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable.structure;

/**
 *
 * @author gum
 */
public class InGameStats {
    
    /**
     * Initializer : everything at 0 at the beginning
     */
    public InGameStats() {
        this.deadShipCounter = 0;
        this.impactCounter = 0;
        this.mineTotalCounter = 0;
    }

    /**
     * Number of ships from the current player that have been killed
     */
    private int deadShipCounter;
   
    /**
     * Number of mines sent by the current player that have touched an opponent ship
     */
    private int impactCounter;
   
    /**
     * Number of mines sent by the current player
     */
    private int mineTotalCounter;

    /**
     * @return the deadShipCounter
     */
    public String getDeadShipCounter() {
        return Integer.toString(deadShipCounter) ;
    }

    /**
     * @return the impactCounter
     */
    public String getImpactCounter() {
        return Integer.toString(impactCounter);
    }

    /**
     * @return the mineTotalCounter
     */
    public String getMineTotalCounter() {
        return Integer.toString(mineTotalCounter);
    }
    
    /**
     * 
     * @param impactedShip true if a ship was touched
     * @param killedShip  true if a ship was killed
     */
    public void turnPlayed(boolean impactedShip, boolean killedShip) {
        mineTotalCounter++;
        if (impactedShip) impactCounter++;
        if (killedShip) deadShipCounter++;
    }
}
