/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.*;
import java.util.List;
import javafx.util.Pair;


/**
 *
 * @author Davy
 */
public interface IDataIHMTable {
    public List<Ship> getTemplateShips() throws DataException;
    public void setShip(Ship ship) throws DataException;
    
    public Pair<Integer, Ship> attack(Coordinate coords, boolean isAttack);
    public void leaveGame();

    public List<Ship> getInitialBoardFromGameId(String gameid);
    public List<Event> getPreviousBoard();
    public List<Event> getNextBoard();
    public void sendMessage(String text);
    public Game getGame();
    public PublicUser getMyPublicUserProfile();
}
