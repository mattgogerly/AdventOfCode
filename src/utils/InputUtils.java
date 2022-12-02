package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class InputUtils {

    public static String asString(int year, int day) {
        return readInput(year, day);
    }

    public static Stream<String> asStringStream(int year, int day) {
        return stringStream(year, day);
    }

    public static Stream<Integer> asIntegerStream(int year, int day) {
        return stringStream(year, day)
                .map(Integer::parseInt);
    }

    public static Stream<Long> asLongStream(int year, int day) {
        return stringStream(year, day)
                .map(Long::parseLong);
    }

    public static char[][] asGrid(int year, int day) {
        return stringStream(year, day)
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private static Stream<String> stringStream(int year, int day) {
        return Arrays.stream(readInput(year, day).split("\n"));
    }

    private static String readInput(int year, int day) {
        String file = String.format("resources/%d/day%d.txt", year, day);

        try {
            return Files.readString(Paths.get(file));
        } catch (IOException e) {
            System.err.println("Failed to read input file" + e);
        }

        throw new IllegalArgumentException("Could not read input");
    }
}
