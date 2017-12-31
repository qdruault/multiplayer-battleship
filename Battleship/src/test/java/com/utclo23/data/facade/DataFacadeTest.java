/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.facade;

import com.utclo23.com.ComFacade;
import com.utclo23.data.module.DataException;
import com.utclo23.data.module.GameMediator;
import com.utclo23.data.module.UserMediator;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Event;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Message;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.Owner;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.StatGame;
import com.utclo23.ihmmain.facade.IHMMainFacade;
import com.utclo23.ihmtable.IIHMTableToData;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.Date;
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
public class DataFacadeTest {
    
    public DataFacadeTest() {
        
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        //before the lauch of the test we create a profile
        //if it already exists, we catch the exception
        DataFacade df = new DataFacade();
        df.setTestMode(true);
        try {
            df.createUser("DAVIDK", "PASSWORD", "", "", new Date(), "");
        } catch (DataException e) {
            e.printStackTrace();
        }
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
     * Test of isTestMode method, of class DataFacade.
     */
    @Test
    public void testIsTestMode() {
        System.out.println("isTestMode");
       
        DataFacade instance = new DataFacade();
        instance.setTestMode(true);
        boolean expResult = true;
        boolean result = instance.isTestMode();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setTestMode method, of class DataFacade.
     */
    @Test
    public void testSetTestMode() {
        System.out.println("setTestMode");
        boolean testMode = true;
        DataFacade instance = new DataFacade();
        instance.setTestMode(testMode);
        
    }

    /**
     * Test of getIhmMainFacade method, of class DataFacade.
     */
    @Test
    public void testGetIhmMainFacade() {
        System.out.println("getIhmMainFacade");
        DataFacade instance = new DataFacade();
        IHMMainFacade expResult = null;
        IHMMainFacade result = instance.getIhmMainFacade();
        assertEquals(expResult, result);
  ;
    }

    /**
     * Test of getIhmTablefacade method, of class DataFacade.
     */
    @Test
    public void testGetIhmTablefacade() {
        System.out.println("getIhmTablefacade");
        DataFacade instance = new DataFacade();
        IIHMTableToData expResult = null;
        IIHMTableToData result = instance.getIhmTablefacade();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getComfacade method, of class DataFacade.
     */
    @Test
    public void testGetComfacade() {
        System.out.println("getComfacade");
        DataFacade instance = new DataFacade();
        ComFacade expResult = null;
        ComFacade result = instance.getComfacade();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setFacadeLinks method, of class DataFacade.
     */
    @Test
    public void testSetFacadeLinks() {
        System.out.println("setFacadeLinks");
        ComFacade comFacade = null;
        IIHMTableToData ihmTableToData = null;
        IHMMainFacade ihmMainFacade = null;
        DataFacade instance = new DataFacade();
        instance.setFacadeLinks(comFacade, ihmTableToData, ihmMainFacade);
        
    }

    /**
     * Test of updatePlayername method, of class DataFacade.
     */
    @Test
    public void testUpdatePlayername() throws Exception {
        System.out.println("updatePlayername");
        String playername = "DAVID";
        DataFacade instance = new DataFacade();
        instance.signin("DAVIDK", "PASSWORD");
        
        instance.updatePlayername(playername);
        
    }

    /**
     * Test of updateFirstname method, of class DataFacade.
     */
    @Test
    public void testUpdateFirstname() throws Exception {
        System.out.println("updateFirstname");
        String firstName = "DAVID";
        DataFacade instance = new DataFacade();
        
         instance.signin("DAVIDK", "PASSWORD");
        instance.updateFirstname(firstName);
        
    }

    /**
     * Test of updateLastname method, of class DataFacade.
     */
    @Test
    public void testUpdateLastname() throws Exception {
        System.out.println("updateLastname");
        String lastName = "KONAM";
        DataFacade instance = new DataFacade();
        
         instance.signin("DAVIDK", "PASSWORD");
        instance.updateLastname(lastName);
       
    }

    /**
     * Test of updateBirthdate method, of class DataFacade.
     */
    @Test
    public void testUpdateBirthdate() throws Exception {
        System.out.println("updateBirthdate");
        Date birthdate = new Date();
        DataFacade instance = new DataFacade();
        
        instance.signin("DAVIDK", "PASSWORD");
        instance.updateBirthdate(birthdate);
        
    }

    /**
     * Test of updateFileImage method, of class DataFacade.
     */
    @Test
    public void testUpdateFileImage() throws Exception {
        System.out.println("updateFileImage");
        String fileImage = "";
        DataFacade instance = new DataFacade();
        
       
    }

    /**
     * Test of updatePassword method, of class DataFacade.
     */
    @Test
    public void testUpdatePassword() throws Exception {
        System.out.println("updatePassword");
        String password = "";
        DataFacade instance = new DataFacade();
        
         instance.signin("DAVIDK", "PASSWORD");
        instance.updatePassword("PASSWORD");
        
    }

    /**
     * Test of getUserMediator method, of class DataFacade.
     */
    @Test
    public void testGetUserMediator() {
        System.out.println("getUserMediator");
        DataFacade instance = new DataFacade();
        UserMediator expResult = null;
        UserMediator result = instance.getUserMediator();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of getGameMediator method, of class DataFacade.
     */
    @Test
    public void testGetGameMediator() {
        System.out.println("getGameMediator");
        DataFacade instance = new DataFacade();
        GameMediator expResult = null;
        GameMediator result = instance.getGameMediator();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of addNewGame method, of class DataFacade.
     */
    @Test
    public void testAddNewGame() {
        System.out.println("addNewGame");
        StatGame game = new StatGame();
        DataFacade instance = new DataFacade();
        instance.setTestMode(true);
        instance.addNewGame(game);
       
    }

    

   
   
    /**
     * Test of getMyOwnerProfile method, of class DataFacade.
     */
    @Test
    public void testGetMyOwnerProfile() throws DataException {
        System.out.println("getMyOwnerProfile");
        DataFacade instance = new DataFacade();
        instance.signin("DAVIDK", "PASSWORD");
        Owner expResult = null;
        Owner result = instance.getMyOwnerProfile();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of getMyPublicUserProfile method, of class DataFacade.
     */
    @Test
    public void testGetMyPublicUserProfile() throws DataException {
        System.out.println("getMyPublicUserProfile");
        DataFacade instance = new DataFacade();
        instance.signin("DAVIDK", "PASSWORD");
        
        PublicUser expResult = null;
        PublicUser result = instance.getMyPublicUserProfile();
        assertNotEquals(expResult, result);
        
    }

    

    /**
     * Test of getConnectedUsers method, of class DataFacade.
     */
    @Test
    public void testGetConnectedUsers() {
        System.out.println("getConnectedUsers");
        DataFacade instance = new DataFacade();
        List<LightPublicUser> expResult = null;
        List<LightPublicUser> result = instance.getConnectedUsers();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of getIPDiscovery method, of class DataFacade.
     */
    @Test
    public void testGetIPDiscovery() throws DataException {
        System.out.println("getIPDiscovery");
        DataFacade instance = new DataFacade();
        List<String> expResult = null;
        
        instance.signin("DAVIDK", "PASSWORD");
        
        List<String> result = instance.getIPDiscovery();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of setIPDiscovery method, of class DataFacade.
     */
    @Test
    public void testSetIPDiscovery() throws Exception {
        System.out.println("setIPDiscovery");
        List<String> discoveryNodes = new ArrayList<>();
        discoveryNodes.add("127.0.0.1");
        
        DataFacade instance = new DataFacade();
        instance.signin("DAVIDK", "PASSWORD");
        instance.setIPDiscovery(discoveryNodes);
       
    }

       

   
    
    /**
     * Test of getNumberVictories method, of class DataFacade.
     */
    @Test
    public void testGetNumberVictories() throws Exception {
        System.out.println("getNumberVictories");
        DataFacade instance = new DataFacade();
        instance.signin("DAVIDK", "PASSWORD");
        int expResult = 0;
        int result = instance.getNumberVictories();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getNumberDefeats method, of class DataFacade.
     */
    @Test
    public void testGetNumberDefeats() throws Exception {
        System.out.println("getNumberDefeats");
        DataFacade instance = new DataFacade();
        instance.signin("DAVIDK", "PASSWORD");
        int expResult = 0;
        int result = instance.getNumberDefeats();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getNumberAbandons method, of class DataFacade.
     */
    @Test
    public void testGetNumberAbandons() throws Exception {
        System.out.println("getNumberAbandons");
        DataFacade instance = new DataFacade();
        instance.signin("DAVIDK", "PASSWORD");
        int expResult = 0;
        int result = instance.getNumberAbandons();
        assertEquals(expResult, result);
        
    }
    
}
