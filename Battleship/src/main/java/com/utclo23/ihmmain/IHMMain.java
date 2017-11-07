/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain;

import com.utclo23.ihmmain.constants.SceneName;
import com.utclo23.ihmmain.controller.AbstractController;
import com.utclo23.ihmmain.facade.IHMMainFacade;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Initialize IHM-Main scene and Contains all the functions to jump into scenes
 * @author Linxuhao
 */
public class IHMMain {
    
    public Stage primaryStage;
    public Map<String,Scene> sceneMap;
    public String styleFile = "/styles/Styles.css";
    public IHMMainFacade facade;
    
    public void start(IHMMainFacade facade,Stage stage) throws Exception {
        sceneMap = new HashMap<String,Scene>();
        primaryStage = stage;
        //load all scenes when app starts
        for(SceneName scenename : SceneName.values()){
            String scenenameString = scenename.toString();
            Scene scene = loadPageAndGenerateControllers(facade,scenenameString);
            sceneMap.put(scenenameString,scene);
        }
        
        stage.setTitle("Login");
        stage.setScene(sceneMap.get(SceneName.Login.toString()));
        stage.show();
    }
    
    public void toLogin() throws IOException{
        toScene(SceneName.Login);
    }
    
    public void toMenu() throws IOException{
        toScene(SceneName.Menu);
    }
    
    public void toPlayerProfile() throws IOException{
        toScene(SceneName.PlayerProfile);
    }
    
    public void toPlayerList() throws IOException{
        toScene(SceneName.PlayerList);
    }
    
    public void toCreateUser() throws IOException{
         toScene(SceneName.CreateUser);
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
            primaryStage.setTitle(scenename);
            primaryStage.setScene(sceneMap.get(scenename));
        }else{
        throw new IOException("the scene you asked " + scenename + " does not exist");
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

        return scene;
    }
}
