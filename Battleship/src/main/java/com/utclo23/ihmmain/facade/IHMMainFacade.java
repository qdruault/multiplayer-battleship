/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.facade;

import com.utclo23.data.facade.IDataIHMMain;
import com.utclo23.ihmmain.IHMMain;
import com.utclo23.ihmtable.IIHMTableToIHMMain;

import java.io.IOException;
import javafx.stage.Stage;
/**
 *IHM Facade, provide interfaces
 * @author Linxuhao
 */
public class IHMMainFacade implements IHMMainToIhmTable{
    IHMMain ihmmain;
    
    public IHMMainFacade (
            IDataIHMMain iDataIHMMain, 
            IIHMTableToIHMMain iIHMTableToIHMMain,
            Stage stage
    ) throws Exception{
        ihmmain = new IHMMain();
        ihmmain.start(stage);
    }
    @Override
    public void toMenu() throws IOException{
        ihmmain.toMenu();
    }
}
