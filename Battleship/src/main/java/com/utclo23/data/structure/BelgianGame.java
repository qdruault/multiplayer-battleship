/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;


import java.util.List;

/**
 * Belgian game
 * it extends game
 * @author Davy
 */

public class BelgianGame extends Game{

    public BelgianGame(StatGame statGame, List<Player> players, List<LightPublicUser> spectators, List<Message> messages) {
        super(statGame, players, spectators, messages);
    }
}
