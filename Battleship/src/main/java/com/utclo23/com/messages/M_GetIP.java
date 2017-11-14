/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.com.KnownIPController;
import com.utclo23.com.Sender;
import java.net.Inet4Address;
import java.util.HashMap;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.StatGame;
import java.util.List;

/**
 *
 * @author thibault
 */
public class M_GetIP extends Message{
	
	KnownIPController kic;
	String name;
	
	
    public M_GetIP(){
    }
	
	
    @Override
    public void callback(IDataCom iDataCom){
		
		// get the necessairy data from the Data module to send back to the requesting node
		iDataCom.addConnectedUser(user.getLightPublicUser());
		
		// TODO: add fonction to get the data from DATA
		List<LightPublicUser> listUsers = null; // = iDataCom.getConnectedUsers();
		List<StatGame>listGames = null; // = iDataCom.getGameList();
		
		kic = KnownIPController.getInstance();
		// add new user to own knownIP hashmap. 
		kic.addNode(user.getLightPublicUser().getId(), IP_sender);
		
		name = this.getClass().getName();

		// get the hasmap of our IP to send it to the requesting node. 
		HashMap<String,Inet4Address> IdToIp = kic.getHashMap();
		
		// send back the data this node has about its known network.
		M_ReturnIP	returnIp = new M_ReturnIP(listGames, listUsers, IdToIp);
		
		Sender os = new Sender(IP_sender.toString(), 80, returnIp);
		Thread thread = new Thread(os);
			thread.start();
		
		// WORK IN PROGRESS
    }
}
