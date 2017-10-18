/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.module;

import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public class Caretaker {
    private ArrayList<Memento> mementoList;
    private int current;

    public Caretaker(ArrayList<Memento> mementoList) {
        this.mementoList = mementoList;
        this.current = 0;
    }
    
    
    
    public void add(Memento mem)
    {
        this.mementoList.add(mem);
    }
    
    public Memento getMemento()
    {
        if(this.mementoList.size() > this.current)
        {
            return this.mementoList.get(current);
        }
        else
        {
            //TODO
            return null;
        }
    }
    
    public void next()
    {
        this.current++;
    }
}

