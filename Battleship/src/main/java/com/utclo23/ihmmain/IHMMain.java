/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain;

import com.utclo23.data.structure.PublicUser;
import com.utclo23.ihmmain.constants.SceneName;
import com.utclo23.ihmmain.controller.AbstractController;
import com.utclo23.ihmmain.controller.PlayerProfileController;
import com.utclo23.ihmmain.controller.PlayerListController;
import com.utclo23.ihmmain.facade.IHMMainFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Contains all mains scene and controllers. 
 * Contains primary stage.
 * Contains activeScene's name to get it quickly;
 * @author Linxuhao
 */
public class IHMMain {
    
    public Stage primaryStage;
    public Map<String,Scene> sceneMap;
    public Map<String,AbstractController> controllerMap;
    public String activeSceneName;
    public String styleFile = "/styles/Styles.css";
    public IHMMainFacade facade;
    
    public void start(IHMMainFacade facade,Stage stage) throws Exception {

        sceneMap = new HashMap<>();
        controllerMap = new HashMap<>();
        activeSceneName = null;
        this.facade = facade;
        primaryStage = stage;
        primaryStage.setWidth(1300);
        primaryStage.setMinWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setMinHeight(600);

        // Load the font for the css
        try {
            Font.loadFont(new FileInputStream(new File("./target/classes/styles/space_age.ttf")), 10);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayerListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //load all scenes when app starts
        for(SceneName scenename : SceneName.values()){
            String scenenameString = scenename.toString();
            Scene scene = loadPageAndGenerateControllers(facade,scenenameString);
            sceneMap.put(scenenameString,scene);
        }
        
        primaryStage.setTitle("Battle Ship");
        toNetworkInterfaceChoice();
        stage.show();
        
        //add onClose event handler which handle the event when user clicks X
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
               we.consume();
               exit(); 
            }
        });
    }
    
    public void toNetworkInterfaceChoice() throws IOException{
        toScene(SceneName.NETWORK_INTERFACE);
    }
    
    public void toLogin() throws IOException{
        toScene(SceneName.LOGIN);
    }
    
    public void toMenu() throws IOException{
        primaryStage.setTitle("Battle Ship");
        toScene(SceneName.MENU);
    }
    
    public void toPlayerProfile() throws IOException{
        PlayerProfileController controller;
        controller = (PlayerProfileController) controllerMap.get(SceneName.PLAYER_PROFILE.toString());
        controller.displayMe();
        toScene(SceneName.PLAYER_PROFILE);

    }
    
    public void toOthersPlayerProfile(PublicUser other) throws IOException{
        PlayerProfileController controller;
        controller = (PlayerProfileController) controllerMap.get(SceneName.PLAYER_PROFILE.toString());
        controller.displayOther(other);
        toScene(SceneName.PLAYER_PROFILE);
    }
    
    public void toPlayerList() throws IOException{
        toScene(SceneName.PLAYER_LIST);
    }
    
    public void toCreateUser() throws IOException{
         toScene(SceneName.CREATE_USER);
    }
    
    public void toIpList() throws IOException{
        toScene(SceneName.IP_LIST);
    }
    

    public void toCreateGame() throws IOException{
        toScene(SceneName.CREATE_GAME);
    }

    public void toGameList() throws IOException{
        toScene(SceneName.GAME_LIST);
    }
    
    public void toSavedGameList() throws IOException{
        toScene(SceneName.SAVED_GAME_LIST);
    }
    
    /**
     * use this carefully,  it throws a IOException if scene no found!
     * @param scenename
     * @throws IOException 
     */
    public void toScene(SceneName scenename)throws IOException{
        toScene(scenename.toString());
    }
    
    /**
     * use this carefully,  it throws a IOException if scene no found!
     * @param scenename
     * @throws IOException 
     */
    public void toScene(String scenename)throws IOException{
        
        if(sceneMap.containsKey(scenename)){
            //stop all controllers
            if(activeSceneName != null){
                controllerMap.get(activeSceneName).stop();
            }
            primaryStage.setScene(sceneMap.get(scenename));
            activeSceneName = scenename;
            controllerMap.get(activeSceneName).run();
            controllerMap.get(activeSceneName).start();
            
        }else{
        throw new IOException("[IHM-MAIN] - the scene you asked " + scenename + " does not exist");
                }
    }
    
    /**
     * load, return the scene and generate its controller, used only when app starts
     * @param fxml
     * @return
     * @throws Exception 
     */
    private Scene loadPageAndGenerateControllers(IHMMainFacade facade, String fxml) throws Exception {
        String ressourceLocation = "/fxml/ihmmain/" + fxml + ".fxml";
        
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource(ressourceLocation));
        Parent pane = paneLoader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(styleFile);
        
        AbstractController controller = (AbstractController) paneLoader.getController();
        controller.setIhmmain(this);
        controller.setFacade(facade);
        controller.stop();
        controllerMap.put(fxml, controller);

        return scene;
    }
    
    public void exit(){
        try{
            facade.iDataIHMMain.signOut();
        }catch (Exception e) {
            //not displaying anything, the app is closing
        }
        System.exit(0);
    }
}
