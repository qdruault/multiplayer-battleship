/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import java.util.HashMap;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import com.utclo23.data.facade.IDataCom;
import java.util.Iterator;
/**
 *
 * @author Thomas
 */
public class KnownIPController {
	
	// All IP known by this node
	// TODO: add a lock on this one
	private HashMap<String, Inet4Address> knownIp;
	

	public KnownIPController(IDataCom iDataCom){
		try{
			knownIp.put(iDataCom.getMyPublicUserProfile().getId(), (Inet4Address)Inet4Address.getLocalHost());
		}
		catch(UnknownHostException e){
			// something...
		}
	}
	
	public HashMap<String, Inet4Address> getIdToIp()
	{
		try{
			Inet4Address ourIpAddress = (Inet4Address)Inet4Address.getLocalHost();
			HashMap<String, Inet4Address> tmphash = knownIp;
						
			tmphash.remove(dtf.getMyPublicUserProfile().getLightPublicUser().getId());	// to avoid sending back our own Ip adress (cuz it already got it).
			return tmphash;
		}
		catch(UnknownHostException e){
			// No good
			return null;	
		}
	}
	
	public void addNode(String id, Inet4Address ip)
	{
		knownIp.put(id, ip);
	}
	
	public void addNonExistingNodes(HashMap<String, Inet4Address> hashToCheck)
	{
		Iterator it = hashToCheck.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry)it.next();
			if(!knownIp.containsKey(pair.getKey())){
				knownIp.put((String)pair.getKey(), (Inet4Address)pair.getValue());
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
	}
	
}
