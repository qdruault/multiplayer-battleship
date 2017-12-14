/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable.structure;

import com.utclo23.data.structure.Coordinate;
import java.util.List;

/**
 *
 * @author gum
 */
public class CoordinatesGenerator {

    /**
     *  This function adds coordinates between a starting/ending point
     * @param startPosition Start position of the ship
     * @param endPosition End position of the ship
     * @param list List in which coordinates have to be added
     */
    public static void generate(Coordinate startPosition, Coordinate endPosition, List<Coordinate> list) {

        // For 1-cell ships.
        if (startPosition.getX() == endPosition.getX() &&
            startPosition.getY() == endPosition.getY()
        ) {
            list.add(endPosition);
        } else {
            // For more-tan-two-cell ship.
            list.add(startPosition);

            // Add the coordinates between the two cases.
            Coordinate diff = new Coordinate(endPosition.getX() - startPosition.getX(), endPosition.getY() - startPosition.getY());
            int xDirection = (diff.getX() >= 0) ? 1 : -1;
            int yDirection = (diff.getY() >= 0) ? 1 : -1;

            for (int x = 1; x < Math.abs(diff.getX()); ++x) {
                list.add(new Coordinate(startPosition.getX() + xDirection * x, startPosition.getY()));
            }
            for (int y = 1; y < Math.abs(diff.getY()); ++y) {
                list.add(new Coordinate(startPosition.getX(), startPosition.getY() + yDirection * y));
            }

            list.add(endPosition);
        }
    }
}
