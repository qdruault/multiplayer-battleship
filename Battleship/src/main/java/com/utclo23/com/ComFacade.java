/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.StatGame;
import com.utclo23.com.messages.M_GetIP;
import java.net.InetAddress;
import java.util.HashMap;
import com.utclo23.com.messages.Message;
import com.utclo23.data.facade.IDataCom;
import java.net.Inet4Address;
import java.util.List;
/**
 *
 * @author thibault
 */
public class ComFacade {
    //private HashMap<UID, InetAddress> UID_IP;
    public IDataCom iDataCom;
	
	private DiscoveryController discoCtrl;
	private KnownIPController kIpCtrl;
    
    public ComFacade(IDataCom iDataCom) {
        System.out.println(this.getClass() + " Creation de la facade");
        this.iDataCom = iDataCom;
		discoCtrl = new DiscoveryController();
		kIpCtrl = new KnownIPController(iDataCom);
        // TODO: Instanciate receiver
    }
    
    public void sendShipsToEnnemy(Ship[] listShips, PublicUser dest){
        
    }
    public void notifyUserSignedIn(PublicUser user){

    }
    public void notifyUserSignedOut(PublicUser user){

    }
    public void notifyNewMessage(Message message){

    }
    public void notifyNewCoordinates(Mine mine, PublicUser[] recipient){

    }
    public void notifyNewGame(StatGame game){

    }
    public void connectionToGame(StatGame game){

    }
    public void leaveGame(PublicUser user){

    }
    public void sendDiscovery(PublicUser user, List<Inet4Address> listIpTarget){
		
		for (int i = 0; i < listIpTarget.size(); i++) {
			M_GetIP m_getIp = new M_GetIP();
			OutSocket os = new OutSocket(listIpTarget.get(i).toString(), 80, m_getIp);
			new Thread(os).start();
			discoCtrl.addIP(listIpTarget.get(i));
		}
		
		// 
		
		
    }
}
