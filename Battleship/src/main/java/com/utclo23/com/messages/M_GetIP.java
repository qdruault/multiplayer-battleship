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
import java.util.List;

/**
 *
 * @author Thibault CHICHE
 */
public class M_GetIP extends Message {

    KnownIPController kic;
    String name;

    /**
     * Constructor.
     *
     * @param user is the message's sender
     */
    public M_GetIP(PublicUser user) {
        super(user);
    }

    @Override
    public void callback(IDataCom iDataCom){
		System.out.println("GET IP Received");
		// get the necessairy data from the Data module to send back to the requesting node
		iDataCom.addConnectedUser(user.getLightPublicUser());
		
		// TODO: add fonction to get the data from DATA
		List<LightPublicUser> listUsers = iDataCom.getConnectedUsers();
                listUsers.add(iDataCom.getMyPublicUserProfile().getLightPublicUser());
		List<StatGame>listGames = iDataCom.getGameList();
		
		kic = KnownIPController.getInstance();
		// add new user to own knownIP hashmap. 
		kic.addNode(user.getLightPublicUser().getId(), IP_sender);
		
		name = this.getClass().getName();
		
		// send back the data this node has about its known network.
		M_ReturnIP returnIp = new M_ReturnIP(iDataCom.getMyPublicUserProfile(), listGames, listUsers, kic.getHashMap());
		
		Sender os = new Sender(IP_sender.getHostAddress(), kic.getPort(), returnIp);
		Thread thread = new Thread(os);
			thread.start();
    }
}
