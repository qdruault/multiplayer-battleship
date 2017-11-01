/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.com.KnownIPController;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author thibault
 */
public class M_ReturnIP extends Message{
	private List<StatGame> listGames;
	private List<LightPublicUser> listUsers;
	private HashMap<String,Inet4Address> idToIp;
	private KnownIPController kic;
	
	public M_ReturnIP(List<StatGame> listGames, List<LightPublicUser> listUsers, HashMap<String,Inet4Address> idToIp){
		listGames = listGames;
		listUsers = listUsers;
		idToIp = idToIp;
	}
    
    @Override
    public void callback(IDataCom iDataCom){
        
		// MAJ for Data
		for (int i = 0; i < listUsers.size(); i++) {
			iDataCom.addConnectedUser(listUsers.get(i));
		}
		
		for (int i = 0; i < listUsers.size(); i++) {
			iDataCom.addNewGame(listGames.get(i));
		}
		
		//MAJ our knownIp hashMap
		kic.addNonExistingNodes(idToIp);
		
		// if SenderNode in GetIPIssuedList
			// send all available data EXCEPT IP for nodes in the GetIPIssuedList
		// else (we got this returnIp from a newly updated node
			// send only our OWN data
		
    }
    
}
