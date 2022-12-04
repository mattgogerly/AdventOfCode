package _2022;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static utils.InputUtils.asStringStream;

class Day04 extends Day {

    public static void main(String[] args) {
        new Day04().printAnswers();
    }

    Day04() {
        super(4);
    }

    @Override
    public Object partOne() {
        List<String> input = asStringStream(YEAR, DAY).toList();

        int anyOverlap = 0;
        for (String line : input) {
            String[] split = line.split(",");
            Set<Integer> firstSet = getFirstSet(split);
            Set<Integer> secondSet = getSecondSet(split);

            if (firstSet.containsAll(secondSet) || secondSet.containsAll(firstSet)) {
                anyOverlap++;
            }
        }

        return anyOverlap;
    }

    @Override
    public Object partTwo() {
        List<String> input = asStringStream(YEAR, DAY).toList();

        int anyOverlap = 0;
        for (String line : input) {
            String[] split = line.split(",");
            Set<Integer> first = getFirstSet(split);
            Set<Integer> second = getSecondSet(split);

            if (first.stream().anyMatch(second::contains)) {
                anyOverlap++;
            }
        }

        return anyOverlap;
    }

    private Set<Integer> getFirstSet(String[] split) {
        int[] first = Arrays.stream(split[0].split("-")).mapToInt(Integer::valueOf).toArray();

        // +1 since the input ranges are inclusive but the range method is not
        return IntStream.range(first[0], first[1] + 1).boxed().collect(Collectors.toSet());
    }

    private Set<Integer> getSecondSet(String[] split) {
        int[] second = Arrays.stream(split[1].split("-")).mapToInt(Integer::valueOf).toArray();
        return IntStream.range(second[0], second[1] + 1).boxed().collect(Collectors.toSet());
    }
}
