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
    
    public ComputerPlayer() {
        super(LightPublicUser.generateComputerProfile());
    }
    
}
