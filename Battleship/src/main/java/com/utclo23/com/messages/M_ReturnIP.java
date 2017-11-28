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
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thibault CHICHE
 */
public class M_ReturnIP extends Message {

    private final List<StatGame> listGames;
    private final List<LightPublicUser> listUsers;
    private final HashMap<String, Inet4Address> idToIp;
    private KnownIPController kic;
    private DiscoveryController discoCtrl;

    public M_ReturnIP(PublicUser user, List<StatGame> listGames, List<LightPublicUser> listUsers, HashMap<String, Inet4Address> idToIp) {
        super(user);
        this.listGames = listGames;
        this.listUsers = listUsers;
        this.idToIp = idToIp;
    }

    @Override
    public void callback(IDataCom iDataCom) {
        System.out.println("RETURN IP Received");
        // MAJ for Data
        for (int i = 0; i < listUsers.size(); i++) {
            iDataCom.addConnectedUser(listUsers.get(i));
        }

        for (int i = 0; i < listGames.size(); i++) {
            iDataCom.addNewGame(listGames.get(i));
        }

        kic = KnownIPController.getInstance();
        //MAJ our knownIp hashMap
        kic.addNonExistingNodes(idToIp);

        discoCtrl = DiscoveryController.getInstance();

        // remove IP from GetIpIssuedList
        if (discoCtrl.isIn(IP_sender)) {
            discoCtrl.removeIpRetrieved(IP_sender);
        }

        List<LightPublicUser> listUsersMaj = iDataCom.getConnectedUsers();
        List<StatGame> listGamesMaj = iDataCom.getGameList();

        // if SenderNode in GetIPIssuedList
        // send all available data EXCEPT IP for nodes in the GetIPIssuedList
        // else (we got this returnIp from a newly updated node
        // send only our OWN data to all new IP
        if (discoCtrl.isIn(IP_sender)) {

            // get the hasmap of our IP to send it to the requesting node. EXECT our own and the ones in getIPIssuedLIst
            HashMap<String, Inet4Address> IdToIp = kic.getNewIpHashMap();

            // improvement : do not send the games which the users in getIPIssuedList are in. 
            for (LightPublicUser usr : listUsersMaj) {
                for (Inet4Address ip : discoCtrl.getGetIpIssuedList()) {
                    String id = kic.getKeyFromValue(kic.getHashMap(), ip);
                    if (usr.getId().equals(id)) {
                        listUsersMaj.remove(usr);
                    }
                }
            }

            // send back the data this node has about its known network.
            M_ReturnIP returnIp = new M_ReturnIP(user, listGamesMaj, listUsersMaj, IdToIp);

            Sender os = new Sender(IP_sender.getHostAddress(), 80, returnIp);
            new Thread(os).start();

        } else {

            LightPublicUser myUser = iDataCom.getMyPublicUserProfile().getLightPublicUser();
            List<LightPublicUser> listUsersToSend = null;
            listUsersToSend.add(myUser);
            // get the hasmap of our IP to send it to the requesting node. 
            HashMap<String, Inet4Address> IdToIp = null;

            try {
                IdToIp.put(myUser.getId(), (Inet4Address) Inet4Address.getLocalHost());
            } catch (UnknownHostException ex) {
                Logger.getLogger(M_ReturnIP.class.getName()).log(Level.SEVERE, null, ex);
            }

            M_ReturnIP returnIp = new M_ReturnIP(user, listGamesMaj, listUsersToSend, IdToIp);
            for (Inet4Address ip : kic.getHashMap().values()) {
                Sender os = new Sender(ip.getHostAddress(), 80, returnIp);
                Thread thread = new Thread(os);
                thread.start();
            }
        }

    }
}
