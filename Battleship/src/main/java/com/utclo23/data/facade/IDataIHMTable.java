/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.*;
import java.rmi.server.UID;
import java.util.List;

/**
 *
 * @author Davy
 */
public interface IDataIHMTable {
    public List<Ship> getShips() throws DataException;
    public void setShip(Ship ship) throws DataException;
    public boolean attack(Coordinate coords);
    public void leaveGame();
    public List<Ship> getInitialBoardFromGameId(String gameid);
    public List<Event> getPreviousBoard();
    public List<Event> getNextBoard();
    public void sendMessage(String text);
    public Game getGame();
    
}
