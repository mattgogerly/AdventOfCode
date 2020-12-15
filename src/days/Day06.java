package days;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.InputUtils.asString;

class Day06 extends Day {

    public static void main(String[] args) {
        new Day06().printAnswers();
    }

    Day06() {
        super(6);
    }

    @Override
    public Object partOne() {
        return formStream()
                .map(s -> s.replaceAll("\n", ""))
                .mapToLong(s -> s.chars().distinct().count())
                .sum();
    }

    @Override
    public Object partTwo() {
        return formStream()
                .map(s -> s.replaceAll("\n", " "))
                .map(s -> s.split(" "))
                .mapToInt(Day06::partTwoSize)
                .sum();
    }

    private Stream<String> formStream() {
        String[] input = asString(DAY).split("\n\n");
        return Arrays.stream(input);
    }

    private static int partTwoSize(String[] forms) {
        String firstEntry = forms[0];
        Set<Integer> chars = firstEntry.chars()
                .boxed()
                .collect(Collectors.toSet());

        for (String form : forms) {
            chars.retainAll(form.chars().boxed().collect(Collectors.toSet()));
        }

        return chars.size();
    }
}
