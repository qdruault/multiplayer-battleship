/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.StatGame;
import java.util.List;
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
public class StatGameTest {
    
    public StatGameTest() {
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
     * Test of getType method, of class StatGame.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        StatGame instance = new StatGame();
        GameType expResult = null;
        GameType result = instance.getType();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of getName method, of class StatGame.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        StatGame instance = new StatGame();
        instance.setName("");
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of getPlayers method, of class StatGame.
     */
    @Test
    public void testGetPlayers() {
        System.out.println("getPlayers");
        StatGame instance = new StatGame();
        List<Player> expResult = null;
        List<Player> result = instance.getPlayers();
        assertEquals(expResult, result);
       
        
    }

    /**
     * Test of isSpectator method, of class StatGame.
     */
    @Test
    public void testIsSpectator() {
        System.out.println("isSpectator");
        StatGame instance = new StatGame();
        boolean expResult = false;
        boolean result = instance.isSpectator();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of isSpectatorChat method, of class StatGame.
     */
    @Test
    public void testIsSpectatorChat() {
        System.out.println("isSpectatorChat");
        StatGame instance = new StatGame();
        instance.setSpectatorChat(false);
        boolean expResult = false;
        boolean result = instance.isSpectatorChat();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getWinner method, of class StatGame.
     */
    @Test
    public void testGetWinner() {
        System.out.println("getWinner");
        StatGame instance = new StatGame();
        LightPublicUser expResult = null;
        LightPublicUser result = instance.getWinner();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getCreator method, of class StatGame.
     */
    @Test
    public void testGetCreator() {
        System.out.println("getCreator");
        StatGame instance = new StatGame();
        LightPublicUser expResult = null;
        LightPublicUser result = instance.getCreator();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of getRealGame method, of class StatGame.
     */
    @Test
    public void testGetRealGame() {
        System.out.println("getRealGame");
        StatGame instance = new StatGame();
        Game expResult = null;
        Game result = instance.getRealGame();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of isGameAbandonned method, of class StatGame.
     */
    @Test
    public void testIsGameAbandonned() {
        System.out.println("isGameAbandonned");
        StatGame instance = new StatGame();
        boolean expResult = false;
        boolean result = instance.isGameAbandonned();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of isComputerMode method, of class StatGame.
     */
    @Test
    public void testIsComputerMode() {
        System.out.println("isComputerMode");
        StatGame instance = new StatGame();
        boolean expResult = false;
        boolean result = instance.isComputerMode();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of setComputerMode method, of class StatGame.
     */
    @Test
    public void testSetComputerMode() {
        System.out.println("setComputerMode");
        boolean computerMode = false;
        StatGame instance = new StatGame();
        instance.setComputerMode(computerMode);
       
        
    }

    /**
     * Test of setType method, of class StatGame.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        GameType type = null;
        StatGame instance = new StatGame();
        instance.setType(type);
       
        
    }

    /**
     * Test of setName method, of class StatGame.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        StatGame instance = new StatGame();
        instance.setName(name);
        
        
    }

    /**
     * Test of setPlayers method, of class StatGame.
     */
    @Test
    public void testSetPlayers() {
        System.out.println("setPlayers");
        List<Player> players = null;
        StatGame instance = new StatGame();
        instance.setPlayers(players);
        
        
    }

    /**
     * Test of setSpectator method, of class StatGame.
     */
    @Test
    public void testSetSpectator() {
        System.out.println("setSpectator");
        boolean spectator = false;
        StatGame instance = new StatGame();
        instance.setSpectator(spectator);
        
        
    }

    /**
     * Test of setSpectatorChat method, of class StatGame.
     */
    @Test
    public void testSetSpectatorChat() {
        System.out.println("setSpectatorChat");
        boolean spectatorChat = false;
        StatGame instance = new StatGame();
        instance.setSpectatorChat(spectatorChat);
       
        
    }

    /**
     * Test of setWinner method, of class StatGame.
     */
    @Test
    public void testSetWinner() {
        System.out.println("setWinner");
        LightPublicUser winner = null;
        StatGame instance = new StatGame();
        instance.setWinner(winner);
       
        
    }

    /**
     * Test of setCreator method, of class StatGame.
     */
    @Test
    public void testSetCreator() {
        System.out.println("setCreator");
        LightPublicUser creator = null;
        StatGame instance = new StatGame();
        instance.setCreator(creator);
        
        
    }

    /**
     * Test of setRealGame method, of class StatGame.
     */
    @Test
    public void testSetRealGame() {
        System.out.println("setRealGame");
        Game realGame = null;
        StatGame instance = new StatGame();
        instance.setRealGame(realGame);
       
    }

    /**
     * Test of setGameAbandonned method, of class StatGame.
     */
    @Test
    public void testSetGameAbandonned() {
        System.out.println("setGameAbandonned");
        boolean gameAbandonned = false;
        StatGame instance = new StatGame();
        instance.setGameAbandonned(gameAbandonned);
        
    }

    /**
     * Test of getId method, of class StatGame.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        StatGame instance = new StatGame();
        instance.setId("");
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
       
        
    }

    /**
     * Test of setId method, of class StatGame.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "";
        StatGame instance = new StatGame();
        instance.setId(id);
        
    }
    
}
