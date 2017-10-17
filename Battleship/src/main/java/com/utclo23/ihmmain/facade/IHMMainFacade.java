/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.facade;

import  com.utclo23.ihmmain.IHMMain;
/**
 *
 * @author Linxuhao
 */
public class IHMMainFacade implements IHMMainToIhmTable{
    IHMMain ihmmain;
    
    @Override
    public void returnMenu(){
        ihmmain.toMenu();
    }
}
