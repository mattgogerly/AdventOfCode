package days;

import java.util.*;
import java.util.stream.Stream;

import static utils.InputUtils.asString;

public class Day6 implements Day {

    public static void main(String[] args) {
        new Day6().printAnswers();
    }

    public Day6() {

    }

    @Override
    public Object partOne() {
        return formStream()
                .map(s -> s.replaceAll("\n", ""))
                .map(s -> s.split(""))
                .map(Day6::partOneSize)
                .reduce(0, Integer::sum);

    }

    @Override
    public Object partTwo() {
        return formStream()
                .map(s -> s.replaceAll("\n", " "))
                .map(s -> s.split(" "))
                .map(Day6::partTwoSize)
                .reduce(0L, Long::sum);
    }

    private Stream<String> formStream() {
        String[] input = asString(6).split("\n\n");
        return Arrays.stream(input);
    }

    private static int partOneSize(String[] form) {
        Set<String> entries = new HashSet<>(Arrays.asList(form));
        return entries.size();
    }

    private static long partTwoSize(String[] forms) {
        Map<Character, Integer> occurrences = new HashMap<>();
        for (String entry : forms) {
            for (Character c : entry.toCharArray()) {
                occurrences.merge(c, 1, Integer::sum);
            }
        }

        int size = forms.length;
        return occurrences.values().stream()
                .filter(s -> s == size)
                .count();
    }
}
