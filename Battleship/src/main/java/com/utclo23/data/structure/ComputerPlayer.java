/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.utclo23.data.configuration.Configuration;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javafx.util.Pair;

/**
 *
 * @author lucillefargeau
 */
public class ComputerPlayer extends Player {

    private Stack<Coordinate> stackFocus;

    private int dx;
    private int dy;

    public void loseFocus(Ship ship) {

        List<Coordinate> list = new ArrayList<>();
        for (Coordinate cf : this.stackFocus) {
            for (Coordinate c : ship.getListCoord()) {

                if (cf.getX() == c.getX() && c.getY() == cf.getY()) {
                    list.add(cf);
                }
            }

        }

        dx = 0;
        dy = 0;

        for (Coordinate cf : list) {
            this.stackFocus.remove(cf);
            System.out.println("remove focus");
        }

    }

    public ComputerPlayer() {
        super(LightPublicUser.generateComputerProfile());
        System.out.println("new Com player");

        dx = 0;
        dy = 0;

        this.stackFocus = new Stack<Coordinate>();

        this.setComputer(true);

    }

    public Coordinate getFocus() {
        if (!this.stackFocus.isEmpty()) {
            return this.stackFocus.peek();
        }
        return null;
    }

    public void setFocus(Coordinate focus) {
        if (focus != null) {
            if (!this.stackFocus.isEmpty()) {
                Coordinate oldFocus = this.stackFocus.peek();
                if (oldFocus.getX() > focus.getX()) {
                    dx = -1;
                    dy = 0;
                }

                if (oldFocus.getX() < focus.getX()) {
                    dx = -1;
                    dy = 0;
                }

                if (oldFocus.getY() > focus.getY()) {
                    dy = -1;
                    dx = 0;
                }

                if (oldFocus.getY() < focus.getY()) {
                    dy = 1;
                    dx = 0;
                }

            } else {

            }
            this.stackFocus.push(focus);
        }
    }

    @Override
    public void setShips(List<Ship> ships) {
        System.out.println("Data | set ships IA");
        int[][] tab = new int[10][10];
        for (int i = 0; i < Configuration.WIDTH; ++i) {
            for (int j = 0; j < Configuration.WIDTH; ++j) {
                tab[i][j] = 0; //empty
            }
        }

        int x, y;
        for (Ship ship : ships) {

            Random r = new Random();
            boolean valid = true;
            do {

                //choose a new location until empty
                x = r.nextInt(Configuration.HEIGHT);
                y = r.nextInt(Configuration.WIDTH);

                if (tab[x][y] != 0 || (x + ship.getSize() >= Configuration.WIDTH && y + ship.getSize() >= Configuration.WIDTH)) {
                    // System.out.println("("+x+","+y+") NON VALIDE");
                    valid = false;
                } else {
                    //check witdh
                    for (int i = 0; i < ship.getSize(); ++i) {
                        if ((x + i >= Configuration.WIDTH) || tab[x + i][y] != 0) {
                            valid = false;

                            //System.out.println("("+x+","+y+") NV"+valid);
                        }
                    }

                    if (!valid) {
                        valid = true;
                        //check height
                        for (int i = 0; i < ship.getSize(); ++i) {
                            if ((y + i >= Configuration.WIDTH) || tab[x][y + i] != 0) {
                                valid = false;

                                //System.out.println("("+x+","+y+") "+valid);
                            }
                        }
                    }

                }

            } while (!valid);

            System.out.println("Data | IA setting ships - start pos " + x + "," + y);
            System.out.println("Data | size : " + ship.getSize());

            //fill 
            if ((x + ship.getSize() < Configuration.WIDTH)) {
                for (int i = 0; i < ship.getSize(); ++i) {
                    tab[x + i][y] = 1;
                    Coordinate coord = new Coordinate(x + i, y);
                    ship.getListCoord().add(coord);

                }
            } else {
                for (int i = 0; i < ship.getSize(); ++i) {
                    tab[x][y + i] = 1;
                    Coordinate coord = new Coordinate(x, y + i);
                    ship.getListCoord().add(coord);

                }
            }

            System.out.println("Data | IA add ship");
            this.getShips().add(ship); //add ship

            for (int i = 0; i < Configuration.WIDTH; ++i) {
                for (int j = 0; j < Configuration.WIDTH; ++j) {
                    if (tab[j][i] == 0) {
                        System.out.print("~ ");
                    } else {
                        System.out.print("@ ");
                    }
                }
                System.out.println();
            }
        }

        System.out.println("Data | IA finishes to set up");
        for (int i = 0; i < Configuration.WIDTH; ++i) {
            for (int j = 0; j < Configuration.WIDTH; ++j) {
                if (tab[j][i] == 0) {
                    System.out.print("~ ");
                } else {
                    System.out.print("@ ");
                }
            }
            System.out.println();
        }
    }

    public Mine randomMine(List<Ship> shipsEnnemy, Game game) {
        //get max size of remaining ships
        int max = 0;
        for (Ship ship : shipsEnnemy) {
            int s = ship.getSize();
            if (s > max && !game.isShipDestroyed(ship, this.getMines())) {
                max = s;
            }
        }

        max = Math.min(max, 4);

        System.out.println("max ship " + max);

        System.out.println("computer plays... " + this.getMines().size());
        int[][] tab = new int[10][10];

        for (int i = 0; i < Configuration.WIDTH; ++i) {
            for (int j = 0; j < Configuration.WIDTH; ++j) {
                tab[i][j] = 0; //empty
            }
        }

        for (Mine mine : this.getMines()) {

            tab[mine.getCoord().getX()][mine.getCoord().getY()] = 1;

        }

        System.out.println("IA mines ");
        for (int i = 0; i < Configuration.WIDTH; ++i) {
            for (int j = 0; j < Configuration.WIDTH; ++j) {
                if (tab[j][i] == 0) {
                    System.out.print("~ ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }

        int x = 0, y = 0;
        boolean valid = true;

        if (this.stackFocus.isEmpty()) {
            //System.out.println("random method");
            Random r = new Random();

            do {
                valid = true;
                //choose a new location until empty
                x = r.nextInt(Configuration.WIDTH);
                y = r.nextInt(Configuration.WIDTH);

                if (tab[x][y] != 0 || (x >= Configuration.WIDTH || y >= Configuration.WIDTH) || (x % max != 0 && y % max != 0)) {
                    valid = false;
                }

                System.out.print(" (" + x + "," + y + ")=" + tab[x][y] + " " + valid + " " + (x % max != 0 && y % max != 0));
            } while (!valid);

        } else {

            do {

                Coordinate focus = this.stackFocus.peek();

                System.out.println("FOCUS method");
                if ((focus.getX() + dx >= Configuration.WIDTH || (focus.getY() + dy < 0)|| (focus.getX() + dx < 0) || focus.getY() + dy >= Configuration.WIDTH) || tab[focus.getX() + dx][focus.getY() + dy] != 0) {

                    if ((focus.getX() + 1 >= Configuration.WIDTH || focus.getY() >= Configuration.WIDTH) || tab[focus.getX() + 1][focus.getY()] != 0) {
                        System.out.println("no x + 1");
                        if ((focus.getX() - 1 < 0 || focus.getY() >= Configuration.WIDTH) || tab[focus.getX() - 1][focus.getY()] != 0) {
                            System.out.println("no x-1");
                            if ((focus.getX() >= Configuration.WIDTH || focus.getY() + 1 >= Configuration.WIDTH) || tab[focus.getX()][focus.getY() + 1] != 0) {
                                System.out.println("no y+1");
                                if ((focus.getX() >= Configuration.WIDTH || focus.getY() - 1 < 0) || tab[focus.getX()][focus.getY() - 1] != 0) {
                                    System.out.println("no y-1");
                                    //this.focus = null;
                                    //this.oldFocus = null;

                                    this.stackFocus.pop();

                                } else {
                                    System.out.println("y-1");
                                    x = focus.getX();
                                    y = focus.getY() - 1;
                                }

                            } else {
                                System.out.println("y+1");
                                x = focus.getX();
                                y = focus.getY() + 1;
                            }
                        } else {
                            System.out.println("x-1");

                            x = focus.getX() - 1;
                            y = focus.getY();
                        }
                    } else {
                        System.out.println("x+1");
                        x = focus.getX() + 1;
                        y = focus.getY();
                    }
                } else {
                    System.out.println("dx dy");
                    x = focus.getX() + dx;
                    y = focus.getY() +dy;
                }

            } while (x == 0 && y == 0 && !this.stackFocus.isEmpty());

            if (this.stackFocus.isEmpty() || (x == 0 && y == 0)) {

                Random r = new Random();

                do {

                    valid = true;
                    //choose a new location until empty
                    x = r.nextInt(Configuration.HEIGHT);
                    y = r.nextInt(Configuration.WIDTH);

                    if (tab[x][y] != 0 || (x >= Configuration.WIDTH || y >= Configuration.WIDTH) || (x % max != 0 && y % max != 0)) {

                        valid = false;
                    }

                    System.out.print(" (" + x + "," + y + ")=" + tab[x][y] + " ");
                } while (!valid);
            }

        }
        Coordinate coordinate = new Coordinate(x, y);

        System.out.println("IA chooses " + x + "," + y);
        Mine mine = new Mine(this, coordinate);
        this.getMines().add(mine);
        return mine;

    }

    public Stack<Coordinate> getStackFocus() {
        return stackFocus;
    }

    public void setStackFocus(Stack<Coordinate> stackFocus) {
        this.stackFocus = stackFocus;
    }

}
