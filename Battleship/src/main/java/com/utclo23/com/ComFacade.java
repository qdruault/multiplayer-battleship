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
import com.utclo23.com.messages.M_GetIP;
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

    public ComFacade(IDataCom iDataCom) {
        System.out.println(this.getClass() + " Creation de la facade");
        this.iDataCom = iDataCom;
        discoCtrl = DiscoveryController.getInstance();
        kIpCtrl = KnownIPController.getInstance(); // creation of KnownIPController
        // TODO: Instanciate receiver

    }

    // envoi au dest
    public void sendShipsToEnnemy(Ship[] listShips, PublicUser dest) {

    }

    // envoi à tout le monde
    public void notifyUserSignedIn(PublicUser user) {

    }

    // envoi à tout le monde
    public void notifyUserSignedOut(PublicUser user) {

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
    public void notifyNewCoordinates(Mine mine, PublicUser[] recipient) {

    }

    // à tout le monde
    public void notifyNewGame(StatGame game) {

    }

    // envoi à la machine qui a crée la game
    public void connectionToGame(StatGame game) {

    }

    // envoi à tout ceux  qui sont dans la game logiquement, paramètre à revoir
    public void leaveGame(PublicUser user) {

    }

    // envoi à tout le monde
    public void sendDiscovery(PublicUser user, List<Inet4Address> listIpTarget) {
        kIpCtrl.initIpList(iDataCom);
        for (int i = 0; i < listIpTarget.size(); i++) {
            M_GetIP m_getIp = new M_GetIP();
            Sender os = new Sender(listIpTarget.get(i).toString(), 80, m_getIp);
            new Thread(os).start();
            discoCtrl.addIP(listIpTarget.get(i));
        }

    }

    // envoi à l'id
    public void getPublicUserProfile(String id) {

    }

    // envoi à l'id
    public void returnPublicUserProfile(String id) {

    }

    // envoi à tout le monde
    public void joinGameResponse(boolean success, String id, StatGame game) {

    }

}
