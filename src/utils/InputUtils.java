package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class InputUtils {

    public static String asString(int day) {
        return readInput(day);
    }

    public static Stream<String> asStringStream(int day) {
        return stringStream(day);
    }

    public static Stream<Integer> asIntegerStream(int day) {
        return stringStream(day)
                .map(Integer::parseInt);
    }

    public static char[][] asGrid(int day) {
        return stringStream(day)
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private static Stream<String> stringStream(int day) {
        return Arrays.stream(readInput(day).split("\n"));
    }

    private static String readInput(int day) {
        String file = String.format("resources/day%d.txt", day);

        try {
            return Files.readString(Paths.get(file));
        } catch (IOException e) {
            System.err.println("Failed to read input file" + e);
        }

        throw new IllegalArgumentException("Could not read input");
    }
}
