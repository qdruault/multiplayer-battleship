/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.data.structure.*;
import java.rmi.server.UID;
import java.util.List;

/**
 *
 * @author Davy
 */
public interface IDataCom {
    public void addNewGame(StatGame game);
    public void setEnnemyShips(List<Ship> ships);
    public void forwardCoordinates(Mine mine);
    public void opponentHasLeftGame();
    public void connectionLostWithOpponent();
    public void addConnectedUser(LightPublicUser user);
    public void removeConnectedUser(LightPublicUser user);
    public void forwardMessage(Message msg);
    public PublicUser getMyPublicUserProfile();

    public void updateGameList(LightPublicUser user, String id, String role);
    public List<StatGame> getGameList();
    public List<LightPublicUser> getConnectedUsers();

}
