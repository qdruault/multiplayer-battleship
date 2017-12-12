/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable;

import com.utclo23.data.structure.Coordinate;
import com.utclo23.ihmtable.structure.CoordinatesGenerator;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 *
 * @author gum
 */
public class CoordinatesGenerationTest {
        
    private static CoordinatesGenerator generator = new CoordinatesGenerator();
    
    @org.junit.Test
    public void testCoordinateGenerator1() throws Exception {
        Coordinate startPoint = new Coordinate(1, 1);
        Coordinate endPoint = new Coordinate(5, 1);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        CoordinatesGenerator.generate(startPoint, endPoint, coordinates);
        
        assertTrue("Must contains (1,1)", equal(new Coordinate(1, 1), coordinates.get(0)));
        assertTrue("Must contains (2,1)", equal(new Coordinate(2, 1), coordinates.get(1)));
        assertTrue("Must contains (3,1)", equal(new Coordinate(3, 1), coordinates.get(2)));
        assertTrue("Must contains (4,1)", equal(new Coordinate(4, 1), coordinates.get(3)));
        assertTrue("Must contains (5,1)", equal(new Coordinate(5, 1), coordinates.get(4)));
    }
    
    @org.junit.Test
    public void testCoordinateGenerator2() throws Exception {
        Coordinate startPoint = new Coordinate(5, 1);
        Coordinate endPoint = new Coordinate(1, 1);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        CoordinatesGenerator.generate(startPoint, endPoint, coordinates);
        
        assertTrue("Must contains (5,1)", equal(new Coordinate(5, 1), coordinates.get(0)));
        assertTrue("Must contains (4,1)", equal(new Coordinate(4, 1), coordinates.get(1)));
        assertTrue("Must contains (3,1)", equal(new Coordinate(3, 1), coordinates.get(2)));
        assertTrue("Must contains (2,1)", equal(new Coordinate(2, 1), coordinates.get(3)));
        assertTrue("Must contains (1,1)", equal(new Coordinate(1, 1), coordinates.get(4)));
    }
    
    @org.junit.Test
    public void testCoordinateGenerator3() throws Exception {
        Coordinate startPoint = new Coordinate(1, 5);
        Coordinate endPoint = new Coordinate(1, 1);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        CoordinatesGenerator.generate(startPoint, endPoint, coordinates);
        
        assertTrue("Must contains (1,5)", equal(new Coordinate(1, 5), coordinates.get(0)));
        assertTrue("Must contains (1,4)", equal(new Coordinate(1, 4), coordinates.get(1)));
        assertTrue("Must contains (1,3)", equal(new Coordinate(1, 3), coordinates.get(2)));
        assertTrue("Must contains (1,2)", equal(new Coordinate(1, 2), coordinates.get(3)));
        assertTrue("Must contains (1,1)", equal(new Coordinate(1, 1), coordinates.get(4)));
    }

    
    @org.junit.Test
    public void testCoordinateGenerator4() throws Exception {
        Coordinate startPoint = new Coordinate(1, 1);
        Coordinate endPoint = new Coordinate(1, 5);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        CoordinatesGenerator.generate(startPoint, endPoint, coordinates);
        
        assertTrue("Must contains (1,1)", equal(new Coordinate(1, 1), coordinates.get(0)));
        assertTrue("Must contains (1,2)", equal(new Coordinate(1, 2), coordinates.get(1)));
        assertTrue("Must contains (1,3)", equal(new Coordinate(1, 3), coordinates.get(2)));
        assertTrue("Must contains (1,4)", equal(new Coordinate(1, 4), coordinates.get(3)));
        assertTrue("Must contains (1,5)", equal(new Coordinate(1, 5), coordinates.get(4)));
    }
    
        @org.junit.Test
    public void testCoordinateGenerator5() throws Exception {
        Coordinate startPoint = new Coordinate(1, 1);
        Coordinate endPoint = new Coordinate(2, 1);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        CoordinatesGenerator.generate(startPoint, endPoint, coordinates);
        
        assertTrue("Must contains (1,1)", equal(new Coordinate(1, 1), coordinates.get(0)));
        assertTrue("Must contains (2,1)", equal(new Coordinate(2, 1), coordinates.get(1)));
    }
    
    @org.junit.Test
    public void testCoordinateGenerator6() throws Exception {
        Coordinate startPoint = new Coordinate(2, 1);
        Coordinate endPoint = new Coordinate(1, 1);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        CoordinatesGenerator.generate(startPoint, endPoint, coordinates);
        
        assertTrue("Must contains (2,1)", equal(new Coordinate(2, 1), coordinates.get(0)));
        assertTrue("Must contains (1,1)", equal(new Coordinate(1, 1), coordinates.get(1)));
    }
    
    @org.junit.Test
    public void testCoordinateGenerator7() throws Exception {
        Coordinate startPoint = new Coordinate(1, 2);
        Coordinate endPoint = new Coordinate(1, 1);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        CoordinatesGenerator.generate(startPoint, endPoint, coordinates);
        
        assertTrue("Must contains (1,2)", equal(new Coordinate(1, 2), coordinates.get(0)));
        assertTrue("Must contains (1,1)", equal(new Coordinate(1, 1), coordinates.get(1)));
    }

    
    @org.junit.Test
    public void testCoordinateGenerator8() throws Exception {
        Coordinate startPoint = new Coordinate(1, 1);
        Coordinate endPoint = new Coordinate(1, 2);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        CoordinatesGenerator.generate(startPoint, endPoint, coordinates);
        
        assertTrue("Must contains (1,1)", equal(new Coordinate(1, 1), coordinates.get(0)));
        assertTrue("Must contains (1,2)", equal(new Coordinate(1, 2), coordinates.get(1)));
    }

    public boolean equal(Coordinate c1, Coordinate c2) {
        return c1.getX() == c2.getX() && c1.getY() == c2.getY();
    }
}