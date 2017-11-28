/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.module;

import java.util.ArrayList;
import java.util.List;

/**
 *Caretaker
 * store mementos and reload it into game
 * @author Davy
 */
public class Caretaker {
    /**
     * list of memento
     */
    private List<Memento> mementoList;
    /**
     * current position
     */
    private int current;

    /**
     * constructor
     * @param mementoList 
     */
    public Caretaker() {
        this.mementoList = new ArrayList<>();
        this.current = 0;
    }
    
    
    /**
     * add memento
     * @param mem 
     */
    public void add(Memento mem)
    {
        this.mementoList.add(mem);
    }
    
    /**
     * get memento
     * @return 
     */
    public Memento getMemento()
    {
        if(this.mementoList.size() > this.current)
        {
            return this.mementoList.get(current);
        }
        else
        {
            return null;
        }
    }
    
    /**
     * move to the next memento
     */
    public void next()
    {
        this.current++;
    }
    
    /**
     * move to the next memento
     */
    public void prev()
    {
        this.current--;
    }
}

