/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import com.utclo23.com.messages.Message;
import com.utclo23.data.facade.IDataCom;
/**
 *
 * @author Thomas MICHEL
 * @author Grégoire MARTINACHE
 */
public class Receiver implements Runnable {
    
    ServerSocket serverSocket;
    Socket client;
    Message request;
    ObjectInputStream in;
    IDataCom iDataCom;
    
    public Receiver(int port, IDataCom dataCom){
        try {
            serverSocket = new ServerSocket(port); 
            iDataCom = dataCom;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                client = serverSocket.accept();
                in = new ObjectInputStream(client.getInputStream());
                
                while((request = (Message) in.readObject()) != null)
                {
                    request.callback(iDataCom);
                    break;
                }

                client.close();
                in.close();

            } catch (IOException e) {

            } catch (ClassNotFoundException e) {

            }

        }
    }
}
