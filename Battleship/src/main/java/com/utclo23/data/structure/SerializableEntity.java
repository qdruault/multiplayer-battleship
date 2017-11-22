/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;

/**
 * Class that allows serialization by Jackson (Json)
 * @author Davy
 */
@JsonTypeInfo(use = Id.CLASS,
include = JsonTypeInfo.As.EXISTING_PROPERTY,
property = "className")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@obj-id")
public abstract class SerializableEntity implements Serializable {
    
    /**
     * get a string that contains the class name (useful for jackson when we need to restore an object from a json file)
     * @return class name
     */
    public String getClassName()
    {
        return this.getClass().getName();
    }    
}
