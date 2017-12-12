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
import com.utclo23.data.structure.LightPublicUser;
import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

    private final DiscoveryController discoCtrl;
    private final KnownIPController kIpCtrl;
    private final Receiver receiver;

    public ComFacade(IDataCom iDataCom) {
        System.out.println(this.getClass() + " Creation de la facade");
        this.iDataCom = iDataCom;
        discoCtrl = DiscoveryController.getInstance();
        kIpCtrl = KnownIPController.getInstance(); // creation of KnownIPController
        // TODO: Instanciate receiver
        receiver = new Receiver(25000, iDataCom);
        new Thread(receiver).start();
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
        M_PlaceShip m_placeship = new M_PlaceShip(iDataCom.getMyPublicUserProfile(), listShips);
        for (LightPublicUser recipient : recipients) {
            Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), kIpCtrl.getPort(), m_placeship);
            new Thread(os).start();
        }
    }

    /**
     * Called to initialize attribute kIpCtrl's knownIp hashmap.
     */
    public void notifyUserSignedIn() {
        kIpCtrl.initIpList(iDataCom);

        /*M_Connexion m_connexion = new M_Connexion(user);
        for(Inet4Address ip : kIpCtrl.getHashMap().values()){
            Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), m_connexion);
            new Thread(os).start();
        }*/
    }

    /**
     * Called to send "log out" notification to everybody.
     */
    public void notifyUserSignedOut() {
        M_Deconnection m_deconnection = new M_Deconnection(iDataCom.getMyPublicUserProfile());
        for (Inet4Address ip : kIpCtrl.getHashMap().values()) {
            Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), m_deconnection);
            new Thread(os).start();
        }
    }

    /**
     * Called to send a chat message to all the recipients of the message.
     *
     * @param message is the message content and recipients
     */
    public void notifyNewMessage(com.utclo23.data.structure.Message message) {
        M_Chat m_chat = new M_Chat(iDataCom.getMyPublicUserProfile(), message, message.getTimestamp());
        for (LightPublicUser recipient : message.getRecipients()) {
            Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), kIpCtrl.getPort(), m_chat);
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
        M_PlaceMine m_placemine = new M_PlaceMine(iDataCom.getMyPublicUserProfile(), mine);
        for (LightPublicUser recipient : recipients) {
            Sender os = new Sender(kIpCtrl.getHashMap().get(recipient.getId()).getHostAddress(), kIpCtrl.getPort(), m_placemine);
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
        
        System.out.println("notifyNewGame");
        
        M_CreationGame m_creationgame = new M_CreationGame(iDataCom.getMyPublicUserProfile(), game);
        for (Inet4Address ip : kIpCtrl.getHashMap().values()) {
            Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), m_creationgame);
            Thread thread = new Thread(os);
            thread.start();
            System.out.println("envoi creationGame");
        }
    }

    /**
     * Called to send a request to join a specified game.
     *
     * @param game is a game the user wants to join
     * @param role is player or spectator
     */
    public void connectionToGame(StatGame game, String role) {

        M_JoinGame m_joingame = new M_JoinGame(iDataCom.getMyPublicUserProfile(), game, role);
        Inet4Address adr = KnownIPController.getInstance().getHashMap().get(game.getCreator().getId());
        Sender os = new Sender(adr.getHostAddress(), kIpCtrl.getPort(), m_joingame);
        new Thread(os).start();
    }

    /**
     * Called to send "leave game" notification to everybody.
     */
    public void leaveGame() {
        M_LeaveGame m_leavegame = new M_LeaveGame(iDataCom.getMyPublicUserProfile());
        for (Inet4Address ip : kIpCtrl.getHashMap().values()) {
            Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), m_leavegame);
            new Thread(os).start();
        }
    }

    /**
     * Called to launch network discovery.
     *
     * @param listIpTarget is the list of known ip targets
     */
    public void sendDiscovery(List<Inet4Address> listIpTarget) {
        HashMap<String, Inet4Address> tmp_hash = new HashMap(kIpCtrl.getHashMap());
        tmp_hash.put(iDataCom.getMyPublicUserProfile().getId(), kIpCtrl.getMyInetAddress());

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
            /*M_GetIP m_getIp = new M_GetIP(iDataCom.getMyPublicUserProfile());
            Sender os = new Sender(listIpTarget.get(i).getHostAddress(), kIpCtrl.getPort(), m_getIp);
            new Thread(os).start();
            discoCtrl.addIP(listIpTarget.get(i));*/
            
            // On vérie si l'ip n'est pas déjà dans le hashMap         
            if(!kIpCtrl.getHashMap().containsValue(ipDest)){
                List otherTargets = new ArrayList(newIpTarget);
                otherTargets.remove(ipDest);

                M_Bleu m_Bleu = new M_Bleu(iDataCom.getMyPublicUserProfile(), 
                        tmp_hash, tmp, iDataCom.getGameList(), otherTargets);
                Sender os = new Sender(ipDest.getHostAddress(), kIpCtrl.getPort(), m_Bleu);
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
        M_GetPlayerInfo m_getplayerinfo = new M_GetPlayerInfo(iDataCom.getMyPublicUserProfile());
        Sender os = new Sender(kIpCtrl.getHashMap().get(id).getHostAddress(), kIpCtrl.getPort(), m_getplayerinfo);
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
        M_JoinGameResponse m_joingameresponse = new M_JoinGameResponse(iDataCom.getMyPublicUserProfile(), success, game);
        if (success) {
            for (Inet4Address ip : kIpCtrl.getHashMap().values()) {
                Sender os = new Sender(ip.getHostAddress(), kIpCtrl.getPort(), m_joingameresponse);
                new Thread(os).start();
            }
        } else {
            Sender os = new Sender(kIpCtrl.getHashMap().get(id).getHostAddress(), kIpCtrl.getPort(), m_joingameresponse);
            new Thread(os).start();
        }
    }
}
