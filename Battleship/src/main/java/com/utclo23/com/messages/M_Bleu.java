/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.com.KnownIPController;
import com.utclo23.com.Sender;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thibault Chiche
 */
public class M_Bleu extends Message {

    private final HashMap<String, Inet4Address> hashMapReceived;
    private final List<LightPublicUser> usersReceived;
    private final List<StatGame> listGamesReceived;
    private final List<Inet4Address> otherTargets;

    public M_Bleu(PublicUser user, HashMap<String, Inet4Address> hashMap,
            List<LightPublicUser> users, List<StatGame> listGames, List<Inet4Address> oTargets) {
        super(user);
        this.hashMapReceived = hashMap;
        this.usersReceived = users;
        this.listGamesReceived = listGames;
        this.otherTargets = oTargets;
    }

    @Override
    public void callback(IDataCom iDataCom) {
        Logger.getLogger(M_Bleu.class.getName()).log(Level.INFO, null, "Blue message received");
        List<String> myUsersId = new ArrayList(KnownIPController.getInstance().getHashMap().keySet());
        List<LightPublicUser> myUsersProfile = new ArrayList(iDataCom.getConnectedUsers());

        HashMap<String, Inet4Address> newPairs = new HashMap();
        List<LightPublicUser> newUsersProfile = new ArrayList();

        // Envoi message bleu aux autres targets afin que tout le monde ait 
        // les mêmes joueurs connectés. Les premiers qui recevront ces messages
        // seront prioritaires
        HashMap<String, Inet4Address> tmpHash = new HashMap(KnownIPController.getInstance().getHashMap());
        tmpHash.put(iDataCom.getMyPublicUserProfile().getId(), KnownIPController.getInstance().getMyInetAddress());

        List<LightPublicUser> tmp = new ArrayList(iDataCom.getConnectedUsers());
        tmp.add(iDataCom.getMyPublicUserProfile().getLightPublicUser());

        if (this.otherTargets != null) {
            for (Inet4Address ipDest : this.otherTargets) {
                // On envoie pas si on avait déjà l'IP dans le hashMap
                if (!KnownIPController.getInstance().getHashMap().containsValue(ipDest)) {
                    M_Bleu m_Bleu = new M_Bleu(iDataCom.getMyPublicUserProfile(),
                            tmpHash, tmp, iDataCom.getGameList(), null);
                    Sender os = new Sender(ipDest.getHostAddress(), KnownIPController.getInstance().getPort(), m_Bleu);
                    new Thread(os).start();
                }
            }
        }

        // Envoi message rouge à ceux non déjà présents dans nos joueurs connectés
        for (Map.Entry<String, Inet4Address> entry : this.hashMapReceived.entrySet()) {
            if (!entry.getKey().equals(iDataCom.getMyPublicUserProfile().getId())
                    && !myUsersId.contains(entry.getKey())) {
                M_Rouge mRouge = new M_Rouge(iDataCom.getMyPublicUserProfile(), tmpHash, tmp, iDataCom.getGameList());
                Sender os = new Sender(entry.getValue().getHostAddress(), KnownIPController.getInstance().getPort(), mRouge);
                Thread thread = new Thread(os);
                thread.start();

                newPairs.put(entry.getKey(), entry.getValue());
            }
        }

        for (LightPublicUser key : this.usersReceived) {
            if (!key.getId().equals(iDataCom.getMyPublicUserProfile().getLightPublicUser().getId())
                    && !myUsersProfile.contains(key)) {
                newUsersProfile.add(key);
            }
        }

        // Maj de nos listes avec les nouvelles données reçues
        // maj de la liste des games
        for (StatGame game : this.listGamesReceived) {
            if (!iDataCom.getGameList().contains(game)) {
                iDataCom.addNewGame(game);
            }
        }
        for (Map.Entry<String, Inet4Address> entry : newPairs.entrySet()) {
            KnownIPController.getInstance().getHashMap().put(entry.getKey(), entry.getValue());
        }
        for (LightPublicUser key : newUsersProfile) {
            iDataCom.addConnectedUser(key);
        }
    }
}
