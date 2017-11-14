/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class DiscoveryController {

    // TODO: add a lock on this one
    private List<Inet4Address> getIpIssuedList;

    private DiscoveryController() {
        getIpIssuedList = new ArrayList<Inet4Address>();
    }
    // SINGLETON
    // Holder

    private static class SingletonHolder {

        private final static DiscoveryController instance = new DiscoveryController();
    }

    // Access point for unique instance of the singleton class
    public static DiscoveryController getInstance() {
        return SingletonHolder.instance;
    }

    public void addIP(Inet4Address ip) {
        getIpIssuedList.add(ip);
    }

    public boolean isIn(Inet4Address Ip) {
        return getIpIssuedList.contains(Ip);
    }

    public List<Inet4Address> getGetIpIssuedList() {
        return getIpIssuedList;
    }
}
