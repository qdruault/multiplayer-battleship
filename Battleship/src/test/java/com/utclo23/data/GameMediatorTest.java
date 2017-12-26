/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.module.GameMediator;
import com.utclo23.data.structure.ClassicGame;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Message;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.StatGame;
import java.util.List;
import javafx.util.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Davy
 */
public class GameMediatorTest {

    public GameMediatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCurrentGame method, of class GameMediator.
     */
    @Test
    public void testGetCurrentGame() {
        System.out.println("getCurrentGame");
        DataFacade df = new DataFacade();
        GameMediator instance = df.getGameMediator();
        Game expResult = null;
        Game result = instance.getCurrentGame();
        assertEquals(expResult, result);

    }

    /**
     * Test of setCurrentGame method, of class GameMediator.
     */
    @Test
    public void testSetCurrentGame() {
        System.out.println("setCurrentGame");
        Game currentGame = new ClassicGame();
        DataFacade df = new DataFacade();
        GameMediator instance = df.getGameMediator();
        instance.setCurrentGame(currentGame);

    }

    /**
     * Test of createGame method, of class GameMediator.
     */
    @Test
    public void testCreateGame() throws Exception {
        System.out.println("createGame");
        String name = "GAME";
        boolean computerMode = true;
        boolean spectator = false;
        boolean spectatorChat = false;
        GameType type = GameType.CLASSIC;
        DataFacade df = new DataFacade();
        GameMediator instance = df.getGameMediator();
        
        df.signin("DAVIDK", "PASSWORD");
        
        Game expResult = null;
        Game result = instance.createGame(name, computerMode, spectator, spectatorChat, type);
        assertNotEquals(expResult, result);

    }

    /**
     * Test of addNewGame method, of class GameMediator.
     */
    @Test
    public void testAddNewGame() {
        System.out.println("addNewGame");
        StatGame statgame = new StatGame();
        DataFacade df = new DataFacade();
        df.setTestMode(true);
        GameMediator instance = df.getGameMediator();
        instance.addNewGame(statgame);

    }

    /**
     * Test of getGamesList method, of class GameMediator.
     */
    @Test
    public void testGetGamesList() {
        System.out.println("getGamesList");
        DataFacade df = new DataFacade();
        GameMediator instance = df.getGameMediator();
        List<StatGame> expResult = null;
        List<StatGame> result = instance.getGamesList();
        assertNotEquals(expResult, result);

    }

    /**
     * Test of setPlayerShip method, of class GameMediator.
     */
    @Test
    public void testSetPlayerShip() throws Exception {
        System.out.println("setPlayerShip");

        Game game = new ClassicGame();

        Ship ship = new Ship();
        ship.getListCoord().add(new Coordinate(0, 0));
        ship.setSize(1);
        DataFacade df = new DataFacade();
        df.signin("DAVIDK", "PASSWORD");

        Player player = new Player(df.getMyOwnerProfile().getUserIdentity().getLightPublicUser());
        game.getPlayers().add(player);

        GameMediator instance = df.getGameMediator();
        instance.setCurrentGame(game);
        instance.setPlayerShip(ship);

    }

    /**
     * Test of updateGameList method, of class GameMediator.
     */
    @Test
    public void testUpdateGameList() throws Exception {
        System.out.println("updateGameList");
        LightPublicUser user = null;

        Game game = new ClassicGame();
        StatGame statGame = new StatGame();
        game.setStatGame(statGame);

        String id = game.getId();

        String role = "player";
        DataFacade df = new DataFacade();
        df.setTestMode(true);
        df.signin("DAVIDK", "PASSWORD");
        user = df.getMyPublicUserProfile().getLightPublicUser();
       
        GameMediator instance = df.getGameMediator();
        instance.setCurrentGame(game);
        instance.updateGameList(user, id, role);

    }

   
}
