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

    /**
    * Called to send all ships coordinates to the other player of the game, 
    * after placing all ships on the board in order to update other player's 
    * Data module. 
    * @param listShips   List of placed ships
    * @param dest The player who must receive ships coordinates
    * @return nothing
    */
    public void sendShipsToEnnemy(List<Ship> listShips, PublicUser dest){
        M_PlaceShip m_placeship = new M_PlaceShip(iDataCom.getMyPublicUserProfile(), listShips);
        Sender os = new Sender(kIpCtrl.getHashMap().get(dest.getId()).getHostAddress(), 80, m_placeship);
        new Thread(os).start();
    }

    /**
    * Called to initialize attribute kIpCtrl's knownIp hashmap.
    * @return nothing
    */
    public void notifyUserSignedIn(PublicUser user){
        kIpCtrl.initIpList(iDataCom);
        
        /*M_Connexion m_connexion = new M_Connexion(user);
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_connexion);
            new Thread(os).start();
        }*/
    }

    /**
    * Called to send "log out" notification to everybody.
    * @param user   current user
    * @return nothing
    */
    public void notifyUserSignedOut(PublicUser user){
        M_Deconnexion m_deconnexion = new M_Deconnexion(user);
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_deconnexion);
            new Thread(os).start();
        }
    }

    /**
    * Called to send a chat message to all the recipients of the message.
    * @param message   Contains message and recipients
    * @return nothing
    */
    public void notifyNewMessage(com.utclo23.data.structure.Message message) {
        M_Chat m_chat = new M_Chat(iDataCom.getMyPublicUserProfile(), message, message.getTimestamp());
        for (LightPublicUser recipient : message.getRecipients()) {
            Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), 80, m_chat);
            new Thread(os).start();
        }
    }

    /**
    * Called to send new mine to the other player and the spectators. 
    * @param mine   New placed mine
    * @param recipients recipients of the new mine
    * @return nothing
    */
    public void notifyNewCoordinates(Mine mine, List<LightPublicUser> recipients){
        M_PlaceMine m_placemine = new M_PlaceMine(iDataCom.getMyPublicUserProfile(), mine);
        for(LightPublicUser recipient : recipients){
           Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), 80, m_placemine);
           new Thread(os).start();
        }
    }

    /**
    * Called to notify everybody of the creation of a new game to update all 
    * Data's module. 
    * @param game   New created game
    * @return nothing
    */
    public void notifyNewGame(StatGame game){
        M_CreationGame m_creationgame = new M_CreationGame(iDataCom.getMyPublicUserProfile(), game);
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_creationgame);
            new Thread(os).start();
        }
    }

    /**
    * Called to send a demand of joining the specified game.
    * @param game   Game you want to join
    * @return nothing
    */
    public void connectionToGame(StatGame game){
        M_JoinGame m_joingame = new M_JoinGame(iDataCom.getMyPublicUserProfile(), game);
        Inet4Address adr = KnownIPController.getInstance().getHashMap().get(game.getCreator().getId());
        Sender os = new Sender(adr.getHostAddress(), 80, m_joingame);
        new Thread(os).start();
    }

    /**
    * Called to send "leave game" notification to everybody.
    * @param user   List of placed ships
    * @return nothing
    */
    public void leaveGame(PublicUser user){
        M_LeaveGame m_leavegame = new M_LeaveGame(iDataCom.getMyPublicUserProfile());
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), 80, m_leavegame);
            new Thread(os).start();
        }
    }

    /**
    * Called to launch network discovery.
    * @param user   List of placed ships
    * @param listIpTarget List of ip targets
    * @return nothing
    */
    public void sendDiscovery(PublicUser user, List<Inet4Address> listIpTarget) {
        
        for (int i = 0; i < listIpTarget.size(); i++) {
            M_GetIP m_getIp = new M_GetIP(iDataCom.getMyPublicUserProfile());
            Sender os = new Sender(listIpTarget.get(i).getHostAddress(), 80, m_getIp);
            new Thread(os).start();
            discoCtrl.addIP(listIpTarget.get(i));
        }

    }

    /**
    * Called to send a demand of getting to the specified recipient's 
    * PublicUserInfo.
    * @param id   UID of the recipient
    * @return nothing
    */
    public void getPublicUserProfile(String id){
        M_GetPlayerInfo m_getplayerinfo = new M_GetPlayerInfo(iDataCom.getMyPublicUserProfile());
        Sender os = new Sender(kIpCtrl.getHashMap().get(id).getHostAddress(), 80, m_getplayerinfo);
        new Thread(os).start();
    }

    /**
    * If success, called to send a join game notification to everybody with the 
    * player who joins the game.Otherwise, the player who wanted to join the 
    * game is advertise that it fails.
    * @param success True if the play can join the game, false otherwise 
    * @param id UID of the player demanding to join the game
    * @param game The game in question
    * @return nothing
    */
    public void joinGameResponse (boolean success, String id, StatGame game){
        M_JoinGameResponse m_joingameresponse = new M_JoinGameResponse(iDataCom.getMyPublicUserProfile(), success);
        if (success){
            for(Inet4Address ip : kIpCtrl.getHashMap().values()){
                Sender os = new Sender(ip.getHostAddress(), 80, m_joingameresponse);
                new Thread(os).start();
            }
        } else {
            Sender os = new Sender(kIpCtrl.getHashMap().get(id).getHostAddress(), 80, m_joingameresponse);
            new Thread(os).start();
        }
    }

}
