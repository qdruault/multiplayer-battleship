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
 * @author root
 */
public class M_Rouge extends Message {
    private final HashMap<String, Inet4Address> hashMapReceived;
    private final List<LightPublicUser> usersReceived;
    private final List<StatGame> listGamesReceived;
    
    public M_Rouge(PublicUser user, HashMap<String, Inet4Address> hashMap, List<LightPublicUser> users, List<StatGame> listGames) {
        super(user);
        this.hashMapReceived = hashMap;
        this.usersReceived = users;
        this.listGamesReceived = listGames;
    }
    
        @Override
    public void callback(IDataCom iDataCom){
        Logger.getLogger(M_Bleu.class.getName()).log(Level.INFO, null, "Red message received");    
        List<String> myUsersId = new ArrayList(KnownIPController.getInstance().getHashMap().keySet());
        List<LightPublicUser> myUsersProfile = new ArrayList(iDataCom.getConnectedUsers());
        
        HashMap<String, Inet4Address> newPairs = new HashMap();
        List<LightPublicUser> newUsersProfile = new ArrayList();  
             
        // envoi message rouge à ceux non déjà présents dans notre liste 
        for(Map.Entry<String, Inet4Address> entry : this.hashMapReceived.entrySet()){
            if(!myUsersId.contains(entry.getKey())){
                // on ne renvoie rien au sender
                if(!entry.getKey().equals(this.user.getId())){
                    List<LightPublicUser> tmp = new ArrayList(myUsersProfile);
                    tmp.add(iDataCom.getMyPublicUserProfile().getLightPublicUser());

                    HashMap<String, Inet4Address> tmpHash = 
                            new HashMap<>(KnownIPController.getInstance().getHashMap());
                    
                    tmpHash.put(iDataCom.getMyPublicUserProfile().getId(), KnownIPController.getInstance().getMyInetAddress());

                    M_Rouge mRouge = new M_Rouge(iDataCom.getMyPublicUserProfile(), tmpHash, tmp, iDataCom.getGameList());
                    Sender os = new Sender(entry.getValue().getHostAddress(), KnownIPController.getInstance().getPort(), mRouge);
                    Thread thread = new Thread(os);
                    thread.start();                                     
                }
                if(!entry.getKey().equals(iDataCom.getMyPublicUserProfile().getId()))
                    newPairs.put(entry.getKey(), entry.getValue());
            }
        }
        
        for(LightPublicUser key : this.usersReceived){
            if(!myUsersProfile.contains(key) && 
                    !key.getId().equals(iDataCom.getMyPublicUserProfile().getLightPublicUser().getId())){
                newUsersProfile.add(key);
            }
        }
        
        // Maj de nos listes avec les nouvelles données reçues
        // maj de la liste des games
        for(StatGame game : this.listGamesReceived){
            if(!iDataCom.getGameList().contains(game))
                iDataCom.addNewGame(game);
        }
        for(Map.Entry<String, Inet4Address> entry : newPairs.entrySet())
            KnownIPController.getInstance().getHashMap().put(entry.getKey(), entry.getValue());
        for(LightPublicUser key : newUsersProfile)
            iDataCom.addConnectedUser(key);
    }   
}
