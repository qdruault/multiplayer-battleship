/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.*;
import java.util.List;

/**
 *
 * @author Davy
 */
public interface IDataCom {
   
    /**
     *
     * @param game
     */
    public void addNewGame(StatGame game);
    public void removeGame(String id);
    /**
     *
     * @param ships
     */
    public void setEnnemyShips(List<Ship> ships);

    /**
     *
     * @param mine
     */
    public void forwardCoordinates(Mine mine);

    /**
     *
     */
    public void opponentHasLeftGame();

    /**
     *
     */
    public void connectionLostWithOpponent();

    /**
     *
     * @param user
     */
    public void addConnectedUser(LightPublicUser user);

    /**
     *
     * @param user
     */
    public void removeConnectedUser(LightPublicUser user);

    /**
     *
     * @param msg
     */
    public void forwardMessage(Message msg);

    /**
     *
     * @return
     */
    public PublicUser getMyPublicUserProfile();

    /**
     *
     * @param user
     * @param id
     * @param role
     * @throws DataException
     */
    public void updateGameList(LightPublicUser user, String id, String role) throws DataException;

    /**
     *
     * @return
     */
    public List<StatGame> getGameList();

    /**
     *
     * @return
     */
    public List<LightPublicUser> getConnectedUsers();

    /**
     *
     * @throws DataException
     */
    public void connectionImpossible() throws DataException;

    /**
     *
     * @param game
     * @throws DataException
     */
    public void receptionGame(Game game) throws DataException;

    /**
     *
     * @param profile
     */
    public void receivePublicUserProfile(PublicUser profile);

}
