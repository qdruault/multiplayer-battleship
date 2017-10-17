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
public interface IDataCom {
    public void addNewGame(StatGame game);
    public void setEnnemyShips(ArrayList<Ship> ships);
    public void forwardCoordinates(Mine mine);
    public void opponentHasLeftGame();
    public void connectionLostWithOpponent();
    public boolean addConnectedUser();
    public boolean removeConnectedUser();
    public void forwardMessage(Message msg);
    public PublicUser getMyPublicUserProfile();
    public void updateGameList(LightPublicUser user, UID id, String role);
}
