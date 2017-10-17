/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.data.structure.*;
import java.rmi.server.UID;
import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public interface IDataIHMTable {
    public ArrayList<Ship> getShips();
    public void setShip(Ship ship);
    public boolean attack(Coordinate coords);
    public void leaveGame();
    public ArrayList<Ship> getInitialBoardFromGameId(UID gameid);
    public ArrayList<Event> getPreviousBoard();
    public ArrayList<Event> getNextBoard();
    public void sendMessage(String text);
    public Game getGame();
}
