/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import java.util.HashMap;
import java.net.Inet4Address;
import com.utclo23.data.facade.IDataCom;
import java.net.InterfaceAddress;
import java.util.Iterator;

/**
 * Class containing a hashmap with UID and corresponding IP address of the known
 * players. Singleton class,only one instance can be instantiate in the
 * application.
 *
 * @author Thomas Michel
 */
public class KnownIPController {

    // TODO: add a lock on this one
    private final HashMap<String, Inet4Address> knownIp;
    protected IDataCom iDataCom;
    private InterfaceAddress usedInterface;
    
    private static final int PORT = 25000;

    private KnownIPController() {
        knownIp = new HashMap<>();
    }

    /**
     * Called to instantiate the unique instance of KnowIPController class in
     * the application.
     */
    private static class SingletonHolder {
        private SingletonHolder(){
            throw new IllegalStateException("Utility class");
        }

        private static final KnownIPController INSTANCE = new KnownIPController();
    }

    /**
     * Called to return the unique INSTANCE of the singleton class
     * KnownIPController.
     *
     * @return singleton instnce of KnowIPController
     */
    public static KnownIPController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Inet4Address getMyInetAddress() {
        return (Inet4Address) this.usedInterface.getAddress();
    }

    public void setUsedInterface(InterfaceAddress uif) {
        this.usedInterface = uif;
    }

    /**
     * Called to put our own IP in attribute "knownIP" and initialize iDataCom
     * attribute.
     *
     * @param iDataCom is the Value affected to attribute iDataCom of the class
     */
    public void initIpList(IDataCom iDataCom) {
        this.iDataCom = iDataCom;
        knownIp.put(
                iDataCom.getMyPublicUserProfile().getId(),
                getMyInetAddress());

    }

    /**
     * Called to return attribute "knownIp" value without our own node.
     *
     * @return Hashmap value
     */
    public HashMap<String, Inet4Address> getHashMap() {
        HashMap<String, Inet4Address> tmpHash = knownIp;
        String id = iDataCom.getMyPublicUserProfile().getLightPublicUser().getId();
        tmpHash.remove(id);	// to avoid sending back our own Ip adress (cuz it already got it).
        return tmpHash;

    }

    /**
     * Called to add a new node in the attribute "knownIP".
     *
     * @param id is the "id", of type string, of the new node
     * @param ip is the Inet4address of the new node
     */
    public void addNode(String id, Inet4Address ip) {
        knownIp.put(id, ip);
    }

    /**
     * Called to add non existing nodes in attribute "knownIp" from another
     * hashmap.
     *
     * @param hashToCheck is the Hashmap containing ids of type String and
     * Inet4Address
     * @return the new updated HashMap
     */
    public HashMap<String, Inet4Address> addNonExistingNodes(HashMap<String, Inet4Address> hashToCheck) {
        Iterator it = hashToCheck.entrySet().iterator();
        HashMap<String, Inet4Address> tmpHash = new HashMap<>();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            if (!knownIp.containsKey(pair.getKey())) {
                knownIp.put((String) pair.getKey(), (Inet4Address) pair.getValue());
                tmpHash.put((String) pair.getKey(), (Inet4Address) pair.getValue());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return tmpHash;
    }
    
    /**
     * Acessor
     * @return the network port number
     */
    public int getPort(){
        return PORT;
    }
}
