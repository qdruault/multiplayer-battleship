///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.utclo23.ihmtable;
//
//import com.utclo23.data.structure.Coordinate;
//import com.utclo23.data.structure.StatGame;
//import java.rmi.server.UID;
//import java.util.logging.Logger;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import static org.junit.Assert.fail;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Rule;
//import org.junit.rules.ExpectedException;
//
///**
// *
// * @author Quentin
// */
//public class IHMTableFacadeTest {
//    
//    private static final String LOGGER_NAME = "IHM Table Facade Test";
//    private static final String EXCEPTION_FAIL = "An exception should have been thrown";
//    
//    public IHMTableFacadeTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//    
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    /**
//     * Test of showSavedGameWithId method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testShowSavedGameWithId() throws Exception {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("showSavedGameWithId");
//        int id = 0;
//        IHMTableFacade instance = new IHMTableFacade();
//        instance.showSavedGameWithId(id);
//    }
//
//    /**
//     * Test of createInGameGUI method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    
//    public void testCreateInGameGUI() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("createInGameGUI");
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.createInGameGUI();
//    }
//
//    /**
//     * Test of stopTimer method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testStopTimer() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("stopTimer");
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.stopTimer();
//    }
//
//    /**
//     * Test of showGame method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testShowGame() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("showGame");
//        UID guid = null;
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.showGame(guid);
//    }
//
//    /**
//     * Test of notifyGameReady method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testNotifyGameReady() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("notifyGameReady");
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.notifyGameReady();
//    }
//
//    /**
//     * Test of printMessage method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testPrintMessage() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("printMessage");
//        String message = "";
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.printMessage(message);
//    }
//
//    /**
//     * Test of feedBack method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testFeedBack() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("feedBack");
//        Coordinate coord = null;
//        boolean bool = false;
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.feedBack(coord, bool);
//    }
//
//    /**
//     * Test of finishGame method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testFinishGame() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("finishGame");
//        StatGame stGame = null;
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.finishGame(stGame);
//    }
//
//    /**
//     * Test of opponentHasLeftGame method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testOpponentHasLeftGame() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("opponentHasLeftGame");
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.opponentHasLeftGame();
//    }
//
//    /**
//     * Test of connectionLostWithOpponent method, of class IHMTableFacade.
//     */
//    @org.junit.Test
//    public void testConnectionLostWithOpponent() {
//        expectedException.expect(UnsupportedOperationException.class);
//        expectedException.expectMessage(IHMTableFacade.EXCEPTION_MESSAGE);
//        
//        Logger.getLogger(LOGGER_NAME).info("connectionLostWithOpponent");
//        IHMTableFacade instance = new IHMTableFacade();
//        
//        instance.connectionLostWithOpponent();
//    }
//}
