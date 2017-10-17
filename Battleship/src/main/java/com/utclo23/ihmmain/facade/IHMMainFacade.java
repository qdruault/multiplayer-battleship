/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.facade;

import  com.utclo23.ihmmain.IHMMain;
import java.io.IOException;
/**
 *IHM Facade, provide interfaces
 * @author Linxuhao
 */
public class IHMMainFacade implements IHMMainToIhmTable{
    IHMMain ihmmain;
    
    public IHMMainFacade(IHMMain ihmmain){
        this.ihmmain = ihmmain;
    }
    @Override
    public void toMenu() throws IOException{
        ihmmain.toMenu();
    }
}
