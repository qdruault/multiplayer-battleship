/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.utclo23.com.messages.Message;

/**
 *
 * @author Thomas MICHEL
 * @author Grégoire MARTINACHE
 */
public class Sender implements Runnable {
    Socket socket;
    int port;
    String ip;
    Message request;
    ObjectOutputStream out;
    ObjectInputStream in;

    public Sender(String ip, int port, Message request) {
        ip = ip;
        port = port;
        request = request;
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
