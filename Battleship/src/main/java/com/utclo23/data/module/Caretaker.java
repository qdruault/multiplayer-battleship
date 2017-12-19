/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.module;

import com.utclo23.data.structure.SerializableEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *Caretaker
 * store mementos and reload it into game
 * @author Davy
 */
public class Caretaker extends SerializableEntity{
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

    public List<Memento> getMementoList() {
        return mementoList;
    }

    public void setMementoList(List<Memento> mementoList) {
        this.mementoList = mementoList;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
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

