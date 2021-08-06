package com.klasevich.task.prince.creator;


import com.klasevich.task.prince.reader.InputDataReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LabyrinthTest {
    InputDataReader inputDataReader = new InputDataReader();
    Labyrinth labyrinth = new Labyrinth();

    @BeforeAll
    void  init(){
        List<String> lines = inputDataReader.readFromFile("data/input.txt");
        labyrinth.createLabyrinth(lines);
    }

    @Test
    void findMinTime() {
        int expected = 60;
        int actual =labyrinth.findMinTime();
        assertEquals(expected,actual);
    }
}