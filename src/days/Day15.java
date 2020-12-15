package days;

import java.util.*;
import java.util.stream.Collectors;

import static utils.InputUtils.asString;

class Day15 extends Day {

    public static void main(String[] args) {
        new days.Day15().printAnswers();
    }

    Day15() {
        super(15);
    }

    @Override
    public Object partOne() {
        return determineLastNumber(2020);
    }

    @Override
    public Object partTwo() {
        return determineLastNumber(30000000);
    }

    private long determineLastNumber(long limit) {
        List<Long> input = Arrays.stream(asString(DAY).split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        Map<Long, Long> store = new HashMap<>();

        // add all but the last input
        for (int i = 0; i < input.size() - 1; i++) {
            store.put(input.get(i), (long) i);
        }

        // start with the last number in the input
        long lastNumber = input.get(input.size() - 1);

        // confusing because arrays are 0 indexed, but first turn is the index of the last number in the input
        // and the last turn is the limit - 1
        for (long turn = input.size() - 1; turn < limit - 1; turn++) {
            long nextLastNumber = store.containsKey(lastNumber) ? turn - store.get(lastNumber) : 0;
            store.put(lastNumber, turn);
            lastNumber = nextLastNumber;
        }

        return lastNumber;
    }
}
