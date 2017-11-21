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
import com.utclo23.com.messages.*;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.LightPublicUser;
import java.net.Inet4Address;
import java.util.List;

/**
 * Facade for the communication module
 *
 * @author Thibault CHICHE
 */
public class ComFacade {

    /**
     * The data facade
     */
    protected IDataCom iDataCom;

    private final DiscoveryController discoCtrl;
    private final KnownIPController kIpCtrl;
    private final Receiver receiver;
    
    public ComFacade(IDataCom iDataCom) {
        System.out.println(this.getClass() + " Creation de la facade");
        this.iDataCom = iDataCom;
        discoCtrl = DiscoveryController.getInstance();
        kIpCtrl = KnownIPController.getInstance(); // creation of KnownIPController
        // TODO: Instanciate receiver
        receiver = new Receiver(80, iDataCom);
        new Thread(receiver).start();
    }

    // envoi au dest
    public void sendShipsToEnnemy(List<Ship> listShips, PublicUser dest){
        M_PlaceShip m_placeship = new M_PlaceShip(listShips);
        Sender os = new Sender(kIpCtrl.getHashMap().get(dest.getId()).getHostAddress(), 80, m_placeship);
        new Thread(os).start();
    }

    // envoi à tout le monde
    // c'est sendDiscovery qui fait ça en fait non ?
    // dans le doute je l'implémente -> Thibault
    public void notifyUserSignedIn(PublicUser user){
        kIpCtrl.initIpList(iDataCom);
        
        /*M_Connexion m_connexion = new M_Connexion(user);
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_connexion);
            new Thread(os).start();
        }*/
    }

    // envoi à tout le monde
    public void notifyUserSignedOut(PublicUser user){
        M_Deconnexion m_deconnexion = new M_Deconnexion(user);
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_deconnexion);
            new Thread(os).start();
        }
    }

    // envoi à tout ceux présents dans le game
    public void notifyNewMessage(com.utclo23.data.structure.Message message) {
        M_Chat m_chat = new M_Chat(message, message.getTimestamp());
        for (LightPublicUser recipient : message.getRecipients()) {
            Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), 80, m_chat);
            new Thread(os).start();
        }
    }

    // envoi à tout ceux dans le game
    public void notifyNewCoordinates(Mine mine, List<LightPublicUser> recipients){
        M_PlaceMine m_placemine = new M_PlaceMine(mine);
        for(LightPublicUser recipient : recipients){
           Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), 80, m_placemine);
           new Thread(os).start();
        }
    }

    // à tout le monde
    public void notifyNewGame(StatGame game){
        M_CreationGame m_creationgame = new M_CreationGame(game);
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_creationgame);
            new Thread(os).start();
        }
    }

    // envoi à la machine qui a crée la game
    public void connectionToGame(StatGame game){
        M_JoinGame m_joingame = new M_JoinGame(game);
        Inet4Address adr = KnownIPController.getInstance().getHashMap().get(game.getCreator().getId());
        Sender os = new Sender(adr.getHostAddress(), 80, m_joingame);
        new Thread(os).start();
    }

    // envoi à tout ceux  qui sont dans la game logiquement, paramètre à revoir
    public void leaveGame(PublicUser user){
        M_LeaveGame m_leavegame = new M_LeaveGame();
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_leavegame);
            new Thread(os).start();
        }
    }

    // envoi à tout le monde
    public void sendDiscovery(PublicUser user, List<Inet4Address> listIpTarget) {
        
        for (int i = 0; i < listIpTarget.size(); i++) {
            M_GetIP m_getIp = new M_GetIP();
            Sender os = new Sender(listIpTarget.get(i).getHostAddress(), 80, m_getIp);
            new Thread(os).start();
            discoCtrl.addIP(listIpTarget.get(i));
        }

    }

    // envoi à l'id
    public void getPublicUserProfile(String id){
        M_GetPlayerInfo m_getplayerinfo = new M_GetPlayerInfo();
        Sender os = new Sender(kIpCtrl.getHashMap().get(id).getHostAddress(), 80, m_getplayerinfo);
        new Thread(os).start();
    }

    // envoi à tout le monde
    public void joinGameResponse (boolean success, String id, StatGame game){
        M_JoinGameResponse m_joingameresponse = new M_JoinGameResponse(success);
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_joingameresponse);
            new Thread(os).start();
        }
    }

}
