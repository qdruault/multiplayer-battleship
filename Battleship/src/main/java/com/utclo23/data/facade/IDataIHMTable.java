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

    /**
     *
     * @return
     * @throws DataException
     */
    public List<Ship> getTemplateShips() throws DataException;

    /**
     *
     * @param ship
     * @throws DataException
     */
    public void setShip(Ship ship) throws DataException;
    
    /**
     *
     * @param coords
     * @param isAttack
     * @param playerWhoPutTheMine
     * @return
     */
    public Pair<Integer, Ship> attack(Coordinate coords, boolean isAttack, Player playerWhoPutTheMine);

    /**
     *
     */
    public void leaveGame();

    /**
     *
     * @param gameid
     * @return
     */
    public List<Ship> getInitialBoardFromGameId(String gameid);

    /**
     *
     * @return
     */
    public List<Event> getPreviousBoard();

    /**
     *
     * @return
     */
    public List<Event> getNextBoard();

    /**
     *
     * @param text
     */
    public void sendMessage(String text);

    /**
     *
     * @return
     */
    public Game getGame();

    /**
     *
     * @return
     */
    public PublicUser getMyPublicUserProfile();
}
