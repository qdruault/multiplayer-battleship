/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import com.utclo23.com.messages.Message;

/**
 *
 * @author Thomas MICHEL
 * @author Grégoire MARTINACHE
 */
class InSocket implements Runnable {

    ServerSocket serverSocket;
    Socket client;
    Message request;
    ObjectOutputStream out;
    ObjectInputStream in;

    public InSocket(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {

        }
    }

    public void run() {
        while (true) {
            try {
                client = serverSocket.accept();
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());

                while ((request = (Message) in.readObject()) != null) {
                    request.callback();
                    break;
                }

                client.close();
                out.close();
                in.close();

            } catch (IOException e) {

            } catch (ClassNotFoundException e) {

            }

        }
    }
}
