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
import com.utclo23.data.facade.IDataCom;
/**
 *
 * @author thibault
 */
public class ComFacade {
    //private HashMap<UID, InetAddress> UID_IP;
    public IDataCom iDataCom;
    
    public ComFacade(IDataCom iDataCom) {
        System.out.println(this.getClass() + " Creation de la facade");
        this.iDataCom = iDataCom;
        // TODO: Instanciate receiver
    }
    
    void sendShipsToEnnemy(Ship[] listShips, PublicUser dest){
        
    }
    void notifyUserSignedIn(PublicUser user){

    }
    void notifyUserSignedOut(PublicUser user){

    }
    void notifyNewMessage(Message message){

    }
    void notifyNewCoordinates(Mine mine, PublicUser[] recipient){

    }
    void notifyNewGame(StatGame game){

    }
    void connectionToGame(StatGame game){

    }
    void leaveGame(PublicUser user){

    }
    void sendDiscovery(){

    }
}
