/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.com.DiscoveryController;
import com.utclo23.com.KnownIPController;
import com.utclo23.com.Sender;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.StatGame;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thibault
 */
public class M_ReturnIP extends Message {

    private List<StatGame> listGames;
    private List<LightPublicUser> listUsers;
    private HashMap<String, Inet4Address> idToIp;
    private KnownIPController kic;
    private DiscoveryController discoCtrl;

    public M_ReturnIP(List<StatGame> listGames, List<LightPublicUser> listUsers, HashMap<String, Inet4Address> idToIp) {
        this.listGames = listGames;
        this.listUsers = listUsers;
        this.idToIp = idToIp;
    }

    @Override
    public void callback(IDataCom iDataCom) {

        // MAJ for Data
        for (int i = 0; i < listUsers.size(); i++) {
            iDataCom.addConnectedUser(listUsers.get(i));
        }

        for (int i = 0; i < listUsers.size(); i++) {
            iDataCom.addNewGame(listGames.get(i));
        }

        kic = KnownIPController.getInstance();
        //MAJ our knownIp hashMap
        kic.addNonExistingNodes(idToIp);

        discoCtrl = DiscoveryController.getInstance();

        // if SenderNode in GetIPIssuedList
        // send all available data EXCEPT IP for nodes in the GetIPIssuedList
        // else (we got this returnIp from a newly updated node
        // send only our OWN data to all new IP
        if (discoCtrl.isIn(IP_sender)) {

            // TODO: add fonction to get the data from DATA
            List<LightPublicUser> listUsers = null; // = iDataCom.getConnectedUsers();
            List<StatGame> listGames = null; // = iDataCom.getGameList();

            // get the hasmap of our IP to send it to the requesting node. 
            HashMap<String, Inet4Address> IdToIp = kic.getNewIpHashMap();

            // send back the data this node has about its known network.
            M_ReturnIP returnIp = new M_ReturnIP(listGames, listUsers, IdToIp);

            Sender os = new Sender(IP_sender.toString(), 80, returnIp);
            new Thread(os).start();

        } else {
            String myId = iDataCom.getMyPublicUserProfile().getLightPublicUser().getId();
            // TODO: add fonction to get the data from DATA
            List<LightPublicUser> listUsersTmp = null; // = iDataCom.getConnectedUsers();
            List<StatGame> listGames = null; // = iDataCom.getGameList();

            List<LightPublicUser> listUsers = null;
            for (int i = 0; i < listUsersTmp.size(); i++) {
                if (listUsers.get(i).getId() == myId) {
                    listUsers.add(listUsersTmp.get(i));
                }
            }
            // get the hasmap of our IP to send it to the requesting node. 
            HashMap<String, Inet4Address> IdToIp = null;

            try {
                IdToIp.put(myId, (Inet4Address) Inet4Address.getLocalHost());
            } catch (UnknownHostException ex) {
                Logger.getLogger(M_ReturnIP.class.getName()).log(Level.SEVERE, null, ex);
            }

            M_ReturnIP returnIp = new M_ReturnIP(listGames, listUsers, IdToIp);
            Sender os = new Sender(IP_sender.toString(), 80, returnIp);
            Thread thread = new Thread(os);
            thread.start();

        }

    }

}
