/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.rmi.server.UID;

/**
 *
 * @author lucillefargeau
 */
public class ComputerPlayer extends Player{
    private Coordinate focus;
    private int dx;
    private int dy;
    
    public ComputerPlayer() {
        super(LightPublicUser.generateComputerProfile());
        this.focus = null;
        this.dx = 0;
        this.dy = 0;
    }

    public Coordinate getFocus() {
        return focus;
    }

    public void setFocus(Coordinate focus) {
        this.focus = focus;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
    
    
    
    
    
    
}
