package com.klasevich.task.prince.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputDataReader {
    private static final Logger logger = LogManager.getLogger();
    private static final String DEFAULT_FILE = "data/input.txt";


    public List<String> readFromFile(String fileName)  {
        List<String>data = new ArrayList<>();
        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            path = Paths.get(DEFAULT_FILE);
        }

        try (Stream<String> stream = Files.lines(path)) {
          data = stream.collect(Collectors.toList());
          logger.debug("data in stream - {}", data);
        }
         catch (IOException e) {
            logger.error(e);

        }
        return data;
    }
}
