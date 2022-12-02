package _2022;

import java.util.Arrays;
import java.util.Comparator;

import static utils.InputUtils.asString;

class Day01 extends Day {

    public static void main(String[] args) {
        new Day01().printAnswers();
    }

    Day01() {
        super(1);
    }

    @Override
    public Object partOne() {
        String[] input = asString(YEAR, DAY).split("\n\n");
        return Arrays.stream(input)
                .map(elfPocket -> {
                    String[] calories = elfPocket.split("\n");
                    return Arrays.stream(calories)
                            .mapToInt(Integer::parseInt)
                            .reduce(0, Integer::sum);
                })
                .mapToInt(i -> i)
                .max()
                .orElseThrow(() -> new IllegalStateException("No answer!"));
    }

    @Override
    public Object partTwo() {
        String[] input = asString(YEAR, DAY).split("\n\n");
        return Arrays.stream(input)
                .map(elfPocket -> {
                    String[] calories = elfPocket.split("\n");
                    return Arrays.stream(calories)
                            .mapToInt(Integer::parseInt)
                            .reduce(0, Integer::sum);
                })
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToInt(i -> i)
                .sum();
    }
}
