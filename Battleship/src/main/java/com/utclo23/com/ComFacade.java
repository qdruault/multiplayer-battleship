/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.StatGame;
import com.utclo23.com.messages.*;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.LightPublicUser;
import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;


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

    private final KnownIPController kIpCtrl;
    private final Receiver receiver;

    public ComFacade(IDataCom iDataCom) {
        this.iDataCom = iDataCom;
        kIpCtrl = KnownIPController.getInstance(); 
        receiver = new Receiver(25000, iDataCom);
        new Thread(receiver).start();
        Logger.getLogger(ComFacade.class.getName()).log(Level.INFO, null, "Facade created");
    }

    public void setUsedInterface(InterfaceAddress uif) {
        kIpCtrl.setUsedInterface(uif);
    }

    /**
     * Called to send all ships coordinates to the other player of the game,
     * after placing all ships on the board in order to update other player's
     * Data module.
     *
     * @param listShips is the list of placed ships
     * @param recipients is the player who must receive the ships coordinates
     */
    public void sendShipsToEnnemy(List<Ship> listShips, List<LightPublicUser> recipients) {
        M_PlaceShip mPlaceship = new M_PlaceShip(iDataCom.getMyPublicUserProfile(), listShips);
        for (LightPublicUser recipient : recipients) {
            if (kIpCtrl.getHashMap().get(recipient.getId()) != null) {
                Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), kIpCtrl.getPort(), mPlaceship);
                new Thread(os).start();
            }            
        }
    }

    /**
     * Called to initialize attribute kIpCtrl's knownIp hashmap.
     */
    public void notifyUserSignedIn() {
        kIpCtrl.initIpList(iDataCom);
    }

    /**
     * Called to send "log out" notification to everybody.
     */
    public void notifyUserSignedOut() {
        M_Deconnection mDeconnection = new M_Deconnection(iDataCom.getMyPublicUserProfile());
        for (Inet4Address ip : kIpCtrl.getHashMap().values()) {
            Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), mDeconnection);
            new Thread(os).start();
        }
    }

    /**
     * Called to send a chat message to all the recipients of the message.
     *
     * @param message is the message content and recipients
     */
    public void notifyNewMessage(com.utclo23.data.structure.Message message) {
        M_Chat mChat = new M_Chat(iDataCom.getMyPublicUserProfile(), message);
        for (LightPublicUser recipient : message.getRecipients()) {
            Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), kIpCtrl.getPort(), mChat);
            new Thread(os).start();
        }
    }

    /**
     * Called to send new mine to the other player and the spectators.
     *
     * @param mine is the new placed mine
     * @param recipients are the recipients of the new mine
     */
    public void notifyNewCoordinates(Mine mine, List<LightPublicUser> recipients) {
        M_PlaceMine mPlaceMine = new M_PlaceMine(iDataCom.getMyPublicUserProfile(), mine);
        for (LightPublicUser recipient : recipients) {
            Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), kIpCtrl.getPort(), mPlaceMine);
            new Thread(os).start();
        }
    }

    /**
     * Called to notify everybody of the creation of a new game to update all
     * users Data's module.
     *
     * @param game is the new created game
     */
    public void notifyNewGame(StatGame game) {                
        M_CreationGame mCreationGame = new M_CreationGame(iDataCom.getMyPublicUserProfile(), game);
        for (Inet4Address ip : kIpCtrl.getHashMap().values()) {
            Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), mCreationGame);
            Thread thread = new Thread(os);
            thread.start();   
        }
    }

    /**
     * Called to send a request to join a specified game.
     *
     * @param game is a game the user wants to join
     * @param role is player or spectator
     */
    public void connectionToGame(StatGame game, String role) {

        M_JoinGame mJoinGame = new M_JoinGame(iDataCom.getMyPublicUserProfile(), game, role);
        Inet4Address adr = KnownIPController.getInstance().getHashMap().get(game.getCreator().getId());
        Sender os = new Sender(adr.getHostAddress(), kIpCtrl.getPort(), mJoinGame);
        new Thread(os).start();
    }

    /**
     * Called to send "leave game" notification to everybody.
     */
    public void leaveGame() {
        M_LeaveGame mLeaveGame = new M_LeaveGame(iDataCom.getMyPublicUserProfile());
        for (Inet4Address ip : kIpCtrl.getHashMap().values()) {
            Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), mLeaveGame);
            new Thread(os).start();
        }
    }

    /**
     * Called to launch network discovery.
     *
     * @param listIpTarget is the list of known ip targets
     */
    public void sendDiscovery(List<Inet4Address> listIpTarget) {
        HashMap<String, Inet4Address> tmpHash = new HashMap(kIpCtrl.getHashMap());
        tmpHash.put(iDataCom.getMyPublicUserProfile().getId(), kIpCtrl.getMyInetAddress());

        List<LightPublicUser> tmp = new ArrayList(iDataCom.getConnectedUsers());
        tmp.add(iDataCom.getMyPublicUserProfile().getLightPublicUser());
            
        // Suppression des doublons et de nous même au cas où
        // dans listIpTarget
        Set set = new HashSet();
        set.addAll(listIpTarget);
        if(set.contains(kIpCtrl.getMyInetAddress()))
            set.remove(kIpCtrl.getMyInetAddress());
        List<Inet4Address> newIpTarget = new ArrayList(set);
        
        for (Inet4Address ipDest : newIpTarget) {    
            // On vérie si l'ip n'est pas déjà dans le hashMap         
            if(!kIpCtrl.getHashMap().containsValue(ipDest)){
                List otherTargets = new ArrayList(newIpTarget);
                otherTargets.remove(ipDest);

                M_Bleu mBleu = new M_Bleu(iDataCom.getMyPublicUserProfile(), 
                        tmpHash, tmp, iDataCom.getGameList(), otherTargets);
                Sender os = new Sender(ipDest.getHostAddress(), kIpCtrl.getPort(), mBleu);
                new Thread(os).start();
            }
        }
    }

    /**
     * Called to send a request to get detailed information of a specified user.
     *
     * @param id is the UID of the recipient
     */
    public void getPublicUserProfile(String id) {
        M_GetPlayerInfo mGetPlayerInfo = new M_GetPlayerInfo(iDataCom.getMyPublicUserProfile());
        Sender os = new Sender(kIpCtrl.getHashMap().get(id).getHostAddress(), kIpCtrl.getPort(), mGetPlayerInfo);
        new Thread(os).start();
    }

    /**
     * If success, send a 'join game' notification to everybody with the player
     * who joined the game. Otherwise, the player who wanted to join the game is
     * advertised that it failed.
     *
     * @param success is true if the player can join the game, false otherwise
     * @param id is the UID of the player demanding to join the game
     * @param game is the game in question
     */
    public void joinGameResponse(boolean success, String id, StatGame game) {
        M_JoinGameResponse mJoinGameResponse = new M_JoinGameResponse(iDataCom.getMyPublicUserProfile(), success, game);

        if (success) {
            for (Inet4Address ip : kIpCtrl.getHashMap().values()) {
                Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), mJoinGameResponse);
                new Thread(os).start();
                Logger.getLogger(ComFacade.class.getName()).log(Level.INFO, null, "Send success joinGame");
            }
        } else {
            Sender os = new Sender(kIpCtrl.getHashMap().get(id).getHostAddress(), kIpCtrl.getPort(), mJoinGameResponse);
            new Thread(os).start();
            Logger.getLogger(ComFacade.class.getName()).log(Level.INFO, null, "Fail joinGame");
        }
    }
}
