/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 *
 * @author Thomas Michel
 * @author Grégoire Martinache
 */
class OutSocket implements Runnable {
    Socket socket;
    int port;
    String ip;
    ObjectOutputStream out;
    ObjectInputStream in;

    
    public OutSocket(String ip, int port, Message request){
        ip = ip;
        port = port;
    }
    
    
    
    public void run() {
     try {
        socket = new Socket(ip, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        out.writeObject(request);
        
        in.close();
        out.close();
        socket.close();
        
        
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

