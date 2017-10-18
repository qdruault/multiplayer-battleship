/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.StatGame;
import java.net.InetAddress;
import java.util.HashMap;
import com.utclo23.com.messages.Message;
/**
 *
 * @author thibault
 */
public class ComFacade {
    //private HashMap<UID, InetAddress> UID_IP;
    
    public void sendShipsToEnnemy(Ship[] listShips, PublicUser dest){
        
    }
    public void notifyUserSignedIn(PublicUser user){

    }
    public void notifyUserSignedOut(PublicUser user){

    }
    public void notifyNewMessage(Message message){

    }
    public void notifyNewCoordinates(Mine mine, PublicUser[] recipient){

    }
    public void notifyNewGame(StatGame game){

    }
    public void connectionToGame(StatGame game){

    }
    public void leaveGame(PublicUser user){

    }
    public void sendDiscovery(){

    }
}
