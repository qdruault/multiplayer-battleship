/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.facade;

import com.utclo23.data.facade.IDataIHMMain;
import com.utclo23.ihmmain.IHMMain;
import com.utclo23.ihmmain.constants.SceneName;
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
    public void refreshPlayerList() throws IOException {
        ihmmain.controllerMap.get(SceneName.PlayerList).refresh();
    }

    @Override
    public void refreshGameList() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
