/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.module;

import com.utclo23.data.structure.Event;
import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public class Memento {
    private ArrayList<Event> state;

    public Memento(ArrayList<Event> state) {
        this.state = state;
    }

    public ArrayList<Event> getState() {
        return state;
    }

    public void setState(ArrayList<Event> state) {
        this.state = state;
    }
    
}
