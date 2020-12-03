package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InputUtils {

    public static List<String> stringInput(int day) {
        return readInputAsString(day);
    }

    public static List<Integer> numberInput(int day) {
        return readInputAsString(day).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static char[][] gridInput(int day) {
        return readInputAsString(day).stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private static List<String> readInputAsString(int day) {
        String file = String.format("resources/day%d.txt", day);

        try {
            List<String> input = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                input.add(line);
                line = reader.readLine();
            }

            reader.close();
            return input;
        } catch (IOException e) {
            System.err.println("Failed to read input file" + e);
        }

        throw new IllegalArgumentException("Could not read input");
    }
}
