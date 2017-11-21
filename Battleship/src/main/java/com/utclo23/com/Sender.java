/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import com.utclo23.com.messages.M_Connection;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.utclo23.com.messages.Message;
import com.utclo23.data.structure.PublicUser;
import java.net.InetSocketAddress;

/**
 * This class is used to send an object to a recipient. 
 * The object is sent by a different thread thanks to a socket. Thread and 
 * socket are closed after sending. A sender should be instantiate every time 
 * an object has to be sent.
 * @author Grégoire MARTINACHE
 */
public class Sender implements Runnable {
    Socket socket;
    int port;
    String ip;
    Message request;
    ObjectOutputStream out;
    //ObjectInputStream in;

    /**
    * Constructor of Sender class. Initialize attributes ip, port and request 
    * message with the specified parameters.
    * @param id IP destination
    * @param port Number of the port
    * @param request Message instance to send
    */
    public Sender(String ip, int port, Message request) {
        this.ip = ip;
        this.port = port;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            socket = new Socket();
            socket.setSoTimeout(2000);
            socket.connect(new InetSocketAddress(ip, port), 2000);
            out = new ObjectOutputStream(socket.getOutputStream());
            //in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(request);
            //in.close();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
