/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.Owner;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Quentin
 */
public class UserProfileTest {

    //test variables
    private static final String LOGGER_NAME = "Data tests for user profile";
    private final static String PLAYER_NAME = "DAVIDK";
    private final static String NEW_PLAYER_NAME = "DAVID";
    private final static String PLAYER_PASSWORD = "PASSWORD";
    private final static String NEW_FIRSTNAME = "TEST";
    private final static String NEW_LASTNAME = "TEST_L";
    private final static Date NEW_BIRTHDATE = Calendar.getInstance().getTime();
    private final static String NEW_IMAGE = "C/Image";

    public UserProfileTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        //before the lauch of the test we create a profile
        //if it already exists, we catch the exception
        DataFacade df = new DataFacade();
        df.setTestMode(true);
        try {
            df.createUser(PLAYER_NAME, PLAYER_PASSWORD, "", "", new Date(), "");
        } catch (DataException e) {
            e.printStackTrace();
        }
    }

    /**
     * function which is called after all tests
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * function which is called before each test
     */
    @Before
    public void setUp() {
    }

    /**
     * function which is called after each test
     */
    @After
    public void tearDown() {
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Test of signin method Must return dataexception when wrong credentials
     * are entered;
     * @throws java.lang.Exception
     */
    @org.junit.Test
    public void testWrongSignin() throws Exception {
        expectedException.expect(DataException.class);

        DataFacade df = new DataFacade();
        df.setTestMode(true);
        df.signin("David", "mon mot de passe");

    }

    /**
     * test when we enter empty string for player name in creation step
     * @throws com.utclo23.data.module.DataException
     */
    @org.junit.Test
    public void testBlankPlayerNameRegister() throws DataException {
        expectedException.expect(DataException.class);

        DataFacade df = new DataFacade();
        df.setTestMode(true);
        df.createUser("", PLAYER_PASSWORD, "", "", new Date(), "");

    }

    /**
     * test when we enter empty string for password in creation step
     * @throws com.utclo23.data.module.DataException
     */
    @org.junit.Test
    public void testBlankPasswordRegister() throws DataException {
        expectedException.expect(DataException.class);

        DataFacade df = new DataFacade();
        df.setTestMode(true);
        df.createUser(PLAYER_NAME, "", "", "", new Date(), "");

    }

    /**
     * Test of signin method Must return dataexception when wrong credentials
     */
    @org.junit.Test
    public void testRightSigninSignOut() {
        try {
            DataFacade df = new DataFacade();
            df.setTestMode(true);
            df.signin(PLAYER_NAME, PLAYER_PASSWORD);

            if (df.getMyOwnerProfile() == null) {
                fail();
            }

            df.signOut();

            if (df.getMyOwnerProfile() != null) {
                fail();
            }

        } catch (DataException e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * Test update password
     * @throws com.utclo23.data.module.DataException
     */
    @org.junit.Test
    public void testUpdatePasswordUser() throws DataException {
        expectedException.expect(DataException.class);

        DataFacade df = new DataFacade();
        df.setTestMode(true);
        df.signin(PLAYER_NAME, PLAYER_PASSWORD);

        if (df.getMyOwnerProfile() == null) {
            fail();
        }

        Owner owner = df.getMyOwnerProfile();
        df.updatePassword("");

        if (!owner.getUserIdentity().getFirstName().equals(NEW_FIRSTNAME)) {
            fail();
        }

        df.updatePassword(owner.getPassword());

        df.signOut();

        if (df.getMyOwnerProfile() != null) {
            fail();
        }
    }
    
    public void testUpdatePlayernameUser() throws DataException {
        expectedException.expect(DataException.class);

        DataFacade df = new DataFacade();
        df.setTestMode(true);
        df.signin(PLAYER_NAME, PLAYER_PASSWORD);

        if (df.getMyOwnerProfile() == null) {
            fail();
        }

        Owner owner = df.getMyOwnerProfile();
        df.updatePlayername("");

        if (!owner.getUserIdentity().getPlayerName().equals(NEW_PLAYER_NAME)) {
            fail();
        }

        df.updatePlayername(NEW_PLAYER_NAME);

        df.signOut();

        if (df.getMyOwnerProfile() != null) {
            fail();
        }
    }

    /**
     * Test update firstname
     */
    @org.junit.Test
    public void testUpdateFirstnameUser() {
        try {
            DataFacade df = new DataFacade();
            df.setTestMode(true);
            df.signin(PLAYER_NAME, PLAYER_PASSWORD);

            if (df.getMyOwnerProfile() == null) {
                fail();
            }

            Owner owner = df.getMyOwnerProfile();
            df.updateFirstname(NEW_FIRSTNAME);

            if (!owner.getUserIdentity().getFirstName().equals(NEW_FIRSTNAME)) {
                fail();
            }

            df.updateFirstname(PLAYER_NAME);

            df.signOut();

            if (df.getMyOwnerProfile() != null) {
                fail();
            }

        } catch (DataException e) {
            e.printStackTrace();
            fail();
        }

    }

    
    public void testUpdateLastnameUser() {
        try {
            DataFacade df = new DataFacade();
            df.setTestMode(true);
            df.signin(PLAYER_NAME, PLAYER_PASSWORD);

            if (df.getMyOwnerProfile() == null) {
                fail();
            }

            Owner owner = df.getMyOwnerProfile();
            df.updateLastname(NEW_LASTNAME);

            if (!owner.getUserIdentity().getLastName().equals(NEW_LASTNAME)) {
                fail();
            }

            df.signOut();

            if (df.getMyOwnerProfile() != null) {
                fail();
            }

        } catch (DataException e) {
            e.printStackTrace();
            fail();
        }

    }
    
     public void testUpdateBirthdateUser() {
        try {
            DataFacade df = new DataFacade();
            df.setTestMode(true);
            df.signin(PLAYER_NAME, PLAYER_PASSWORD);

            if (df.getMyOwnerProfile() == null) {
                fail();
            }

            Owner owner = df.getMyOwnerProfile();
            df.updateBirthdate(NEW_BIRTHDATE);

            if (!owner.getUserIdentity().getBirthDate().equals(NEW_BIRTHDATE)) {
                fail();
            }
            df.signOut();

            if (df.getMyOwnerProfile() != null) {
                fail();
            }

        } catch (DataException e) {
            e.printStackTrace();
            fail();
        }

    }
     
     public void testUpdateFileImageUser() {
        try {
            DataFacade df = new DataFacade();
            df.setTestMode(true);
            df.signin(PLAYER_NAME, PLAYER_PASSWORD);

            if (df.getMyOwnerProfile() == null) {
                fail();
            }

            Owner owner = df.getMyOwnerProfile();
            df.updateFileImage(NEW_IMAGE);

            if (!owner.getUserIdentity().getAvatar().equals(NEW_IMAGE)) {
                fail();
            }
            df.signOut();

            if (df.getMyOwnerProfile() != null) {
                fail();
            }

        } catch (DataException e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * Test for update
     *
     * @throws com.utclo23.data.module.DataException
     */
    @org.junit.Test
    public void testAlreadyConnected() throws DataException {
        expectedException.expect(DataException.class);
        DataFacade df = new DataFacade();
        df.setTestMode(true);
        df.signin(PLAYER_NAME, PLAYER_PASSWORD);
        df.signin("david", "mdp");
    }

    /**
     * Test of signout before signin
     *
     * @throws java.lang.Exception
     */
    @org.junit.Test
    public void testWrongSignout() throws Exception {
        expectedException.expect(DataException.class);

        DataFacade df = new DataFacade();
        df.setTestMode(true);
        df.signOut();

    }
    
    /**
     * Test update discoveryNodes
     */
    @org.junit.Test
    public void testUpdateDiscoveryNodes() {
        try {            
            DataFacade df = new DataFacade();
            df.setTestMode(true);
            df.signin(PLAYER_NAME, PLAYER_PASSWORD);
            
            
            if (df.getMyOwnerProfile() == null) {
                fail();
            }

            Owner owner = df.getMyOwnerProfile();

            List<String> new_DiscoveryNodes = Arrays.asList("IP1", "IP2", "IP3");
            
            df.setIPDiscovery(new_DiscoveryNodes);

            //Check JSON has been impacting by reconnecting        
            df.signOut();            
            df.signin(PLAYER_NAME, PLAYER_PASSWORD);
           if(!owner.getDiscoveryNodes().equals(new_DiscoveryNodes)){
                fail();
            }

            df.signOut();
        } catch (DataException e) {
            fail();
        }

    }

}
