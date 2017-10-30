/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.module;

import com.utclo23.data.structure.Event;
import java.util.List;

/**
 * Memento
 * Keep a state of a game
 * @author Davy
 */
public class Memento {
    /**
     * list of events (messages, ships)
     */
    private List<Event> state;

    /**
     * constructor
     * @param state 
     */
    public Memento(List<Event> state) {
        this.state = state;
    }

    /**
     * get state
     * @return  state
     */
    public List<Event> getState() {
        return state;
    }

    /**
     * set state of the memento
     * @param state 
     */
    public void setState(List<Event> state) {
        this.state = state;
    }
    
}
