/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.ShipType;
import com.utclo23.data.structure.Mine;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;

/**
 * Test the function attack
 * @author wuxiaodan
 */
public class AttackTest {

    @org.junit.Test
    public void testCasePlayer() throws DataException {

        DataFacade df = new DataFacade();
        df.setTestMode(true);
        try {
            df.createUser("Xiaodan", "123", "", "", new Date(), "");        
        } catch (Exception e) {    
            e.printStackTrace();       
        }
         df.signin("Xiaodan", "123");

         df.createGame("TestAttack", false, true, true, GameType.CLASSIC);
         
         //Add another player
         LightPublicUser lightPublicUserB = new LightPublicUser("456", "Toto");
         df.getGameMediator().getCurrentGame().addUser(lightPublicUserB, "player");
         
         
         //Coordinate of A's ship
         Coordinate coorShipA11 = new Coordinate(1,1);
         Coordinate coorShipA12 = new Coordinate(2,1);
         Coordinate coorShipA13 = new Coordinate(3,1);
         List<Coordinate> coordShipA1 = new ArrayList<>();
         coordShipA1.add(coorShipA11);
         coordShipA1.add(coorShipA12);
         coordShipA1.add(coorShipA13);
         //set A's ship
         Player playerA = df.getGameMediator().getCurrentGame().getPlayer(df.getUserMediator().getMyPublicUserProfile().getId());
         Player playerB = df.getGameMediator().getCurrentGame().ennemyOf(playerA);
         
         Ship shipA1 = new Ship(ShipType.SUBMARINE, playerA, coordShipA1, 3);
         List<Ship> shipsA = new ArrayList<>();
         shipsA.add(shipA1);
         playerA.setShips(shipsA);
         
         //Coordinate of B's ship
         Coordinate coorShipB11 = new Coordinate(11,2);
         Coordinate coorShipB12 = new Coordinate(12,2);
         Coordinate coorShipB13 = new Coordinate(13,2);
         List<Coordinate> coordShipB1 = new ArrayList<>();
         coordShipB1.add(coorShipB11);
         coordShipB1.add(coorShipB12);
         coordShipB1.add(coorShipB13);
         //set B's ship
         Ship shipB1 = new Ship(ShipType.SUBMARINE, playerB, coordShipB1, 3);
         List<Ship> shipsB = new ArrayList<>();
         shipsB.add(shipB1);
         playerB.setShips(shipsB);
         
        
         //Test of player A and player B
         Coordinate coorTestA1 = new Coordinate(11,2); //case de B
         Pair<Integer, Ship> attackA1 = df.attack(coorTestA1, true);
         
         //test of function getRecentMine
         Mine mineTest = df.getGameMediator().getCurrentGame().getRecentMine(coorTestA1);
         
         Coordinate coorTestB1 = new Coordinate(2,2); 
         Pair<Integer, Ship> attackB1 = df.attack(coorTestB1, true);
         Coordinate coorTestA2 = new Coordinate(12,2); //case de B
         Pair<Integer, Ship> attackA2 = df.attack(coorTestA2, true);
         Coordinate coorTestB2 = new Coordinate(9,2); 
         Pair<Integer, Ship> attackB2 = df.attack(coorTestB2, true);
         Coordinate coorTestA3 = new Coordinate(13,2); //case de B
         Pair<Integer, Ship> attackA3 = df.attack(coorTestA3, true);
         Coordinate coorTestB3 = new Coordinate(1,2); 
         Pair<Integer, Ship> attackB3 = df.attack(coorTestB3, true);
         
         //Test of a spectator
        // Coordinate coorTestU3 = new Coordinate(2,1); 
         //Pair<Integer, Ship> attackU3 = df.attack(coorTestU3, false);
         
         //When a mine is placed in a place where already has a mine
         //Pair<Integer, Ship> attackA4 = df.attack(coorTestA3, true);



    }
    
    @org.junit.Test
    public void testForwardCoordinates() throws DataException {
        DataFacade df = new DataFacade();
        df.setTestMode(true);
        try {
            df.createUser("Xiaodan", "123", "", "", new Date(), "");
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        df.signin("Xiaodan", "123"); 
        
        df.createGame("TestAttack", false, true, true, GameType.CLASSIC);
         
        //Add another player
        LightPublicUser lightPublicUserB = new LightPublicUser("456", "Toto");
        df.getGameMediator().getCurrentGame().addUser(lightPublicUserB, "player");
         
         
        //Coordinate of A's ship
         Coordinate coorShipA11 = new Coordinate(1,1);
         Coordinate coorShipA12 = new Coordinate(2,1);
         Coordinate coorShipA13 = new Coordinate(3,1);
         List<Coordinate> coordShipA1 = new ArrayList<>();
         coordShipA1.add(coorShipA11);
         coordShipA1.add(coorShipA12);
         coordShipA1.add(coorShipA13);
         //set A's ship
         Player playerA = df.getGameMediator().getCurrentGame().getPlayer(df.getUserMediator().getMyPublicUserProfile().getId());
         Player playerB = df.getGameMediator().getCurrentGame().ennemyOf(playerA);
         
         Ship shipA1 = new Ship(ShipType.SUBMARINE, playerA, coordShipA1, 3);
         List<Ship> shipsA = new ArrayList<>();
         shipsA.add(shipA1);
         playerA.setShips(shipsA);
         
         //Coordinate of B's ship
         Coordinate coorShipB11 = new Coordinate(11,2);
         Coordinate coorShipB12 = new Coordinate(12,2);
         Coordinate coorShipB13 = new Coordinate(13,2);
         List<Coordinate> coordShipB1 = new ArrayList<>();
         coordShipB1.add(coorShipB11);
         coordShipB1.add(coorShipB12);
         coordShipB1.add(coorShipB13);
         //set B's ship
         Ship shipB1 = new Ship(ShipType.SUBMARINE, playerB, coordShipB1, 3);
         List<Ship> shipsB = new ArrayList<>();
         shipsB.add(shipB1);
         playerB.setShips(shipsB);
         
         //Coordinate of B's ship
         Coordinate coorMineB1 = new Coordinate(2,1);
         Mine mine = new Mine(playerB, coorMineB1); 
         
         df.forwardCoordinates(mine);
         
     

    }
    
    
    
    @org.junit.Test
    public void testEndGame() throws DataException {
        DataFacade df = new DataFacade();
        df.setTestMode(true);
        try {
            df.createUser("Xiaodan", "123", "", "", new Date(), "");
        } catch (Exception e) {
            e.printStackTrace();            
        }
        df.signin("Xiaodan", "123");
        
        df.createGame("TestAttack", false, true, true, GameType.CLASSIC);
         
        //Add another player
        LightPublicUser lightPublicUserB = new LightPublicUser("456", "Toto");
        df.getGameMediator().getCurrentGame().addUser(lightPublicUserB, "player");
         
         
        //Coordinate of A's ship
         Coordinate coorShipA11 = new Coordinate(1,1);
         Coordinate coorShipA12 = new Coordinate(2,1);
         Coordinate coorShipA13 = new Coordinate(3,1);
         List<Coordinate> coordShipA1 = new ArrayList<>();
         coordShipA1.add(coorShipA11);
         coordShipA1.add(coorShipA12);
         coordShipA1.add(coorShipA13);
         //set A's ship
         Player playerA = df.getGameMediator().getCurrentGame().getPlayer(df.getUserMediator().getMyPublicUserProfile().getId());
         Player playerB = df.getGameMediator().getCurrentGame().ennemyOf(playerA);
         
         Ship shipA1 = new Ship(ShipType.SUBMARINE, playerA, coordShipA1, 3);
         List<Ship> shipsA = new ArrayList<>();
         shipsA.add(shipA1);
         playerA.setShips(shipsA);
         
         //Coordinate of B's ship
         Coordinate coorShipB11 = new Coordinate(11,2);
         Coordinate coorShipB12 = new Coordinate(12,2);
         Coordinate coorShipB13 = new Coordinate(13,2);
         List<Coordinate> coordShipB1 = new ArrayList<>();
         coordShipB1.add(coorShipB11);
         coordShipB1.add(coorShipB12);
         coordShipB1.add(coorShipB13);
         //set B's ship
         Ship shipB1 = new Ship(ShipType.SUBMARINE, playerB, coordShipB1, 3);
         List<Ship> shipsB = new ArrayList<>();
         shipsB.add(shipB1);
         playerB.setShips(shipsB);
         
         //Coordinate of A's mines
         Coordinate coorMineA1 = new Coordinate(11,12);
         Mine mineA1 = new Mine(playerA, coorMineA1); 
         Coordinate coorMineA2 = new Coordinate(12,2);
         Mine mineA2 = new Mine(playerA, coorMineA2); 
         Coordinate coorMineA3 = new Coordinate(13,2);
         Mine mineA3 = new Mine(playerA, coorMineA3); 
         
         //Coordinate of B's mines
         Coordinate coorMineB1 = new Coordinate(1,1);
         Mine mineB1 = new Mine(playerB, coorMineB1); 
         Coordinate coorMineB2 = new Coordinate(2,1);
         Mine mineB2 = new Mine(playerB, coorMineB2); 
         Coordinate coorMineB3 = new Coordinate(3,1);
         Mine mineB3 = new Mine(playerB, coorMineB3); 
         
         df.attack(coorMineA1, true);
         df.forwardCoordinates(mineA1);  
         df.attack(coorMineB1, true);
         df.forwardCoordinates(mineB1); 
         df.attack(coorMineA2, true);
         df.forwardCoordinates(mineA2);    
         df.attack(coorMineB2, true); 
         df.forwardCoordinates(mineB2);
         df.attack(coorMineA3, true);
         df.forwardCoordinates(mineA3); 
         df.attack(coorMineB3, true);
         boolean b = df.getGameMediator().getCurrentGame().isGameFinishedByEnnemy() ;
         df.forwardCoordinates(mineB3);    
         
         
         int nbVict = df.getNumberVictories() ;
         int nbDefeats = df.getNumberDefeats();
         int nbAbandons = df.getNumberAbandons() ;

    }
}
