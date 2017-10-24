/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.StatGame;
import java.util.ArrayList;
/**
 *
 * @author thibault
 */
public class M_GetIP extends Message{
	ArrayList<LightPublicUser> ListUsers;
	ArrayList<StatGame> ListGames;
	DataFacade dtf;
	PublicUser initUser;
    public M_GetIP(){
		name = "GET_IP";
		initUser = dtf.getMyPublicUserProfile();
    }
	
    @Override
    public void callback(){
		dtf.addConnectedUser(); // isn't it supposed to atke a user in argument ? Also It should be newConnectedUser() but they don't have...
		ListUsers = dtf.getConnectedUsers();
		ListGames = dtf.getGameList();
		
		//TODO: Construction of the hashmap
		// we should have that as a Communication intern methode (idk about its class).
		// uidToIp = getUidToIp();
		
		// send back the data this node has about its known network. 
		// M_ReturnIP	returnIp = new ReturnIp(ListGames, ListUsers, uidToIp);
		
		// at the message level we don't have the Ip of the sender anymore. We had it in the InSocket. 
		// so here we can't initialize the response anymore. 
		// OutSocket os = new OutSocket(ip, port, returnIp);
		
		// WORK IN PROGRESS
    }
}
