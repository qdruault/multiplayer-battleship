/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.utclo23.data.configuration.Configuration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;


/**
 *
 * @author lucillefargeau
 */
public class ComputerPlayer extends Player {

    private Deque<Coordinate> stackFocus;

    private int dx;
    private int dy;

    /**
     *
     * @param ship
     */
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
        }

    }

    /**
     *
     */
    public ComputerPlayer() {
        super(LightPublicUser.generateComputerProfile());

        dx = 0;
        dy = 0;

        this.stackFocus = new ArrayDeque<>();

        this.setComputer(true);

    }

    /**
     *
     * @return
     */
    public Coordinate getFocus() {
        if (!this.stackFocus.isEmpty()) {
            return this.stackFocus.peek();
        }
        return null;
    }

    /**
     *
     * @param focus
     */
    public void setFocus(Coordinate focus) {
        if (focus != null) {
            if (!this.stackFocus.isEmpty()) {
                Coordinate oldFocus = this.stackFocus.peek();
                if (oldFocus.getX() > focus.getX()) {
                    dx = -1;
                    dy = 0;
                }

                else if (oldFocus.getX() < focus.getX()) {
                    dx = -1;
                    dy = 0;
                }

                else if (oldFocus.getY() > focus.getY()) {
                    dy = -1;
                    dx = 0;
                }

                else if (oldFocus.getY() < focus.getY()) {
                    dy = 1;
                    dx = 0;
                }

            }
            
            this.stackFocus.push(focus);
        }
    }

    /**
     *
     * @param ships
     */
    @Override
    public void setShips(List<Ship> ships) {
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

            this.getShips().add(ship); //add ship

            
        }

       
    }

    /**
     *
     * @param shipsEnnemy
     * @param game
     * @return
     */
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


        int[][] tab = new int[10][10];

        for (int i = 0; i < Configuration.WIDTH; ++i) {
            for (int j = 0; j < Configuration.WIDTH; ++j) {
                tab[i][j] = 0; //empty
            }
        }

        for (Mine mine : this.getMines()) {

            tab[mine.getCoord().getX()][mine.getCoord().getY()] = 1;

        }

      

        int x = -1, y = -1;
        boolean valid = true;

        if (this.stackFocus.isEmpty()) {
            //System.out.println("random method");
            Random r = new Random();

            do {
                valid = true;
                //choose a new location until empty
                x = r.nextInt(Configuration.WIDTH);
                y = r.nextInt(Configuration.WIDTH);

                int a, b;
                a = x - y;
                a = a % max;
                b = y - x;
                b = b % max;

                if (tab[x][y] != 0 || (x >= Configuration.WIDTH || y >= Configuration.WIDTH) || (a != 0 || b != 0)) {
                    valid = false;
                }

                //still valid
                if (valid) {
                    boolean validLine = true;
                    if (x + max < Configuration.WIDTH && y < Configuration.WIDTH) {
                        for (int i = 0; i < max; ++i) {
                            if (tab[x + i][y] != 0) {
                                validLine = false;
                                break;
                            }
                        }
                    }

                    if (!validLine) {
                        valid = false;
                    } else {
                        valid = true;
                    }

                    if (!valid) {
                        validLine = true;
                        if (x < Configuration.WIDTH && y + max < Configuration.WIDTH) {
                            for (int i = 0; i < max; ++i) {
                                if (tab[x][y + i] != 0) {
                                    validLine = false;
                                    break;
                                }
                            }
                        }

                        if (!validLine) {
                            valid = false;
                        } else {
                            valid = true;
                        }

                        if (!valid) {
                            validLine = true;
                            if (x - max >= 0 && y < Configuration.WIDTH) {
                                for (int i = 0; i < max; ++i) {
                                    if (tab[x - i][y] != 0) {
                                        validLine = false;
                                        break;
                                    }
                                }
                            }

                            if (!validLine) {
                                valid = false;
                            } else {
                                valid = true;
                            }

                            if (!valid) {
                                validLine = true;
                                if (y - max >= 0 && x < Configuration.WIDTH) {
                                    for (int i = 0; i < max; ++i) {
                                        if (tab[x][y - i] != 0) {
                                            validLine = false;
                                            break;
                                        }
                                    }
                                }

                                if (!validLine) {
                                    valid = false;
                                } else {
                                    valid = true;
                                }

                            }

                        }

                    }

                }

            } while (!valid);

        } else {

            do {

                
                Coordinate focus = this.stackFocus.peek();
                if ((focus.getX() + dx >= Configuration.WIDTH || (focus.getY() + dy < 0) || (focus.getX() + dx < 0) || focus.getY() + dy >= Configuration.WIDTH) || tab[focus.getX() + dx][focus.getY() + dy] != 0) {

                    if ((focus.getX() + 1 >= Configuration.WIDTH || focus.getY() >= Configuration.WIDTH) || tab[focus.getX() + 1][focus.getY()] != 0) {
                        if ((focus.getX() - 1 < 0 || focus.getY() >= Configuration.WIDTH) || tab[focus.getX() - 1][focus.getY()] != 0) {
                            if ((focus.getX() >= Configuration.WIDTH || focus.getY() + 1 >= Configuration.WIDTH) || tab[focus.getX()][focus.getY() + 1] != 0) {
                                if ((focus.getX() >= Configuration.WIDTH || focus.getY() - 1 < 0) || tab[focus.getX()][focus.getY() - 1] != 0) { //this.focus = null;
                                    //this.oldFocus = null;
                                                                        //this.focus = null;
                                    //this.oldFocus = null;

                                    this.stackFocus.pop();

                                } else {
                                    x = focus.getX();
                                    y = focus.getY() - 1;
                                }

                            } else {
                                x = focus.getX();
                                y = focus.getY() + 1;
                            }
                        } else {

                            x = focus.getX() - 1;
                            y = focus.getY();
                        }
                    } else {
                        x = focus.getX() + 1;
                        y = focus.getY();
                    }
                } else {
                    x = focus.getX() + dx;
                    y = focus.getY() + dy;
                }

                
                
                
            } while (x == -1 && y == -1 && !this.stackFocus.isEmpty());
            
            //
                  
            if (this.stackFocus.isEmpty() || (x == -1 && y == -1)) {

                Random r = new Random();

                do {
                    valid = true;
                    //choose a new location until empty
                    x = r.nextInt(Configuration.WIDTH);
                    y = r.nextInt(Configuration.WIDTH);

                    int a, b;
                    a = x - y;
                    a = a % max;
                    b = y - x;
                    b = b % max;

                    if (tab[x][y] != 0 || (x >= Configuration.WIDTH || y >= Configuration.WIDTH) || (a != 0 || b != 0)) {
                        valid = false;
                    }

                    //still valid
                    if (valid) {
                        boolean validLine = true;
                        if (x + max < Configuration.WIDTH && y < Configuration.WIDTH) {
                            for (int i = 0; i < max; ++i) {
                                if (tab[x + i][y] != 0) {
                                    validLine = false;
                                    break;
                                }
                            }
                        }

                        if (!validLine) {
                            valid = false;
                        } else {
                            valid = true;
                        }

                        if (!valid) {
                            validLine = true;
                            if (x < Configuration.WIDTH && y + max < Configuration.WIDTH) {
                                for (int i = 0; i < max; ++i) {
                                    if (tab[x][y + i] != 0) {
                                        validLine = false;
                                        break;
                                    }
                                }
                            }

                            if (!validLine) {
                                valid = false;
                            } else {
                                valid = true;
                            }

                            if (!valid) {
                                validLine = true;
                                if (x - max >= 0 && y < Configuration.WIDTH) {
                                    for (int i = 0; i < max; ++i) {
                                        if (tab[x - i][y] != 0) {
                                            validLine = false;
                                            break;
                                        }
                                    }
                                }

                                if (!validLine) {
                                    valid = false;
                                } else {
                                    valid = true;
                                }

                                if (!valid) {
                                    validLine = true;
                                    if (y - max >= 0 && x < Configuration.WIDTH) {
                                        for (int i = 0; i < max; ++i) {
                                            if (tab[x][y - i] != 0) {
                                                validLine = false;
                                                break;
                                            }
                                        }
                                    }

                                    if (!validLine) {
                                        valid = false;
                                    } else {
                                        valid = true;
                                    }

                                }

                            }

                        }

                    }

                } while (!valid);
            }

        }
        Coordinate coordinate = new Coordinate(x, y);

        Mine mine = new Mine(this, coordinate);
        this.getMines().add(mine);
        return mine;

    }

    /**
     *
     * @return
     */
    public Deque<Coordinate> getStackFocus() {
        return stackFocus;
    }

    /**
     *
     * @param stackFocus
     */
    public void setStackFocus(Deque<Coordinate> stackFocus) {
        this.stackFocus = stackFocus;
    }

}
