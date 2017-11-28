/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import com.utclo23.com.messages.Message;
import com.utclo23.data.facade.IDataCom;

/**
 * This class implements a serverSocket to receive messages from another 
 * machines. An unique instance of Receiver should be running. Running in a 
 * different thread from the main thread.
 * @author Thomas MICHEL
 * @author Grégoire MARTINACHE
 */
public class Receiver implements Runnable {
    
    ServerSocket serverSocket;
    Socket client;
    Message request;
    ObjectInputStream in;
    IDataCom iDataCom;
    
    /**
    * Construction of Receiver. Initialize attributes "serverSocket" and 
    * "iDataCom" with the specified parameters.
    * @param port Number of the port used by the serverSocket
    * @param dataCom Access of the IDataCom interface used by the others COM 
    * modules
    */
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
                System.out.println(in.available());
                request = (Message) in.readObject();
                request.callback(iDataCom);
                /*while((request = (Message) in.readObject()) != null)
                {
                    request.callback(iDataCom);
                    break;
                }*/

                client.close();
                in.close();

            } catch (IOException e) {

            } catch (ClassNotFoundException e) {

            }

        }
    }
}
