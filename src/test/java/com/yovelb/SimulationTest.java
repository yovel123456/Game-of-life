package com.yovelb;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {

    Simulation simulation = new Simulation(8, 5);

    @BeforeAll
    static void initAll() {
        Simulation simulation = new Simulation(8, 5);

        simulation.setAlive(2, 2);
        simulation.setAlive(3, 2);
        simulation.setAlive(4, 2);
        System.out.println(simulation.countAliveNeighbours(3, 2));

        simulation.printBoard();

        simulation.step();
        simulation.printBoard();

        simulation.step();
        simulation.printBoard();

        simulation.step();
        simulation.printBoard();


    }

    @Test
    void setAlive() {
        simulation.setAlive(0, 0);
        assertEquals(simulation.getState(0, 0), 1);
    }

    @Test
    void setDead() {
    }

    @Test
    void getCellStatus() {
        assertEquals(simulation.getState(0, 0), 0);
        assertEquals(simulation.getState(0, 1), 0);
        assertEquals(simulation.getState(3, 2), 0);
        assertEquals(simulation.getState(2, 3), 0);
    }

    @Test
    void printBoard() {

    }
}
