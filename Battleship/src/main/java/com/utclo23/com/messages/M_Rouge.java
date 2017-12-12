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

/**
 *
 * @author root
 */
public class M_Rouge extends Message {
    HashMap<String, Inet4Address> hashMap_received;
    List<LightPublicUser> users_received;
    List<StatGame> listGames_received;
    
    public M_Rouge(PublicUser user, HashMap<String, Inet4Address> hashMap, List<LightPublicUser> users, List<StatGame> listGames) {
        super(user);
        this.hashMap_received = hashMap;
        this.users_received = users;
        this.listGames_received = listGames;
    }
    
        @Override
    public void callback(IDataCom iDataCom){
        System.out.println("Message rouge reçu");      
        List<String> my_users_id = new ArrayList(KnownIPController.getInstance().getHashMap().keySet());
        List<LightPublicUser> my_users_profile = new ArrayList(iDataCom.getConnectedUsers());
        
        HashMap<String, Inet4Address> new_pairs = new HashMap();
        List<LightPublicUser> new_users_profile = new ArrayList();  
             
        // envoi message rouge à ceux non déjà présents dans notre liste 
        for(String key : this.hashMap_received.keySet()){
            if(!my_users_id.contains(key)){
                // on ne renvoie rien au sender
                if(!key.equals(this.user.getId())){
                    List<LightPublicUser> tmp = new ArrayList(my_users_profile);
                    tmp.add(iDataCom.getMyPublicUserProfile().getLightPublicUser());

                    HashMap<String, Inet4Address> tmp_hash = 
                            new HashMap<>(KnownIPController.getInstance().getHashMap());
                    
                    tmp_hash.put(iDataCom.getMyPublicUserProfile().getId(), KnownIPController.getInstance().getMyInetAddress());

                    M_Rouge m_Rouge = new M_Rouge(iDataCom.getMyPublicUserProfile(), tmp_hash, tmp, iDataCom.getGameList());
                    Sender os = new Sender(this.hashMap_received.get(key).getHostAddress(), KnownIPController.getInstance().getPort(), m_Rouge);
                    Thread thread = new Thread(os);
                    thread.start();                                     
                }
                if(!key.equals(iDataCom.getMyPublicUserProfile().getId()))
                    new_pairs.put(key, this.hashMap_received.get(key));
            }
        }
        
        for(LightPublicUser key : this.users_received){
            if(!my_users_profile.contains(key) && 
                    !key.equals(iDataCom.getMyPublicUserProfile().getLightPublicUser())){
                new_users_profile.add(key);
            }
        }
        
        // Maj de nos listes avec les nouvelles données reçues
        // maj de la liste des games
        for(StatGame game : this.listGames_received){
            if(!iDataCom.getGameList().contains(game))
                iDataCom.addNewGame(game);
        }
        for(String key : new_pairs.keySet())
            KnownIPController.getInstance().getHashMap().put(key, new_pairs.get(key));
        for(LightPublicUser key : new_users_profile)
            iDataCom.addConnectedUser(key);
    }   
}
