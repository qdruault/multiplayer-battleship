/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import java.net.Inet4Address;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class DiscoveryController {
	
	// TODO: add a lock on this one
	private List<Inet4Address>getIpIssuedList;
	
	public void addIP(Inet4Address ip){
		getIpIssuedList.add(ip);
	}
}
