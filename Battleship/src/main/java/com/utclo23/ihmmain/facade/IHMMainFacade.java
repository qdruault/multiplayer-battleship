/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.facade;

import com.utclo23.data.facade.IDataIHMMain;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
import com.utclo23.ihmmain.IHMMain;
import com.utclo23.ihmmain.constants.SceneName;
import com.utclo23.ihmmain.controller.GameListController;
import com.utclo23.ihmmain.controller.PlayerProfileController;
import com.utclo23.ihmtable.IIHMTableToIHMMain;

import java.io.IOException;
import javafx.stage.Stage;
/**
 *IHM Facade, provide interfaces
 * @author Linxuhao
 */
public class IHMMainFacade implements IHMMainToIhmTable, IHMMainToData{
    
    private IHMMain ihmmain;
    public IDataIHMMain iDataIHMMain;
    public IIHMTableToIHMMain iIHMTableToIHMMain;
    
    /**
     * create IHM Main facade and instanciate ihm main components
     * 
     * @param iDataIHMMain
     * @param iIHMTableToIHMMain
     * @param stage
     * @throws Exception 
     */
    public IHMMainFacade (IDataIHMMain iDataIHMMain, IIHMTableToIHMMain iIHMTableToIHMMain,Stage stage ) throws Exception{
        
        this.iDataIHMMain = iDataIHMMain;
        this.iIHMTableToIHMMain = iIHMTableToIHMMain;
        ihmmain = new IHMMain();
        ihmmain.start(this, stage);
    }
    
    @Override
    public void toMenu() throws IOException{
        ihmmain.toMenu();
    }
    
    @Override
    public Stage getPrimaryStage(){
        return ihmmain.primaryStage;
    }
    
    
    @Override
    public void refreshUserList() throws IOException {
        ihmmain.controllerMap.get(SceneName.PLAYER_LIST).refresh();
    }

    @Override
    public void refreshGameList() throws IOException {
        ihmmain.controllerMap.get(SceneName.GAME_LIST).refresh();
    }
    @Override
    public void recievePublicUserProfile(PublicUser player) throws IOException {
       PlayerProfileController controller =(PlayerProfileController)ihmmain.controllerMap.get(SceneName.PLAYER_PROFILE.toString());
       controller.recievePublicUser(player);
    }
    @Override
    public void receptionGame(Game game){
       GameListController controller =(GameListController)ihmmain.controllerMap.get(SceneName.GAME_LIST.toString());
       controller.receptionGame(game);
    }
    public void connectionImpossible(){
       System.out.println("Not supported yet");
    }

}   