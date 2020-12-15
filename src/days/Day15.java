package days;

import java.util.*;
import java.util.stream.Collectors;

import static utils.InputUtils.asString;

class Day15 extends Day {

    public static void main(String[] args) {
        new Day15().printAnswers();
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

        // store number to the turn on which it was spoken
        Map<Long, Long> store = new HashMap<>();

        // add all but the last input
        // using i = 1, i-1 to reduce confusion on turn number vs array index
        for (int i = 1; i < input.size(); i++) {
            store.put(input.get(i - 1), (long) i);
        }

        // start with the last number in the input
        long lastNumber = input.get(input.size() - 1);

        // turn starts at the last element of the input (the first number we need to consider)
        // note "turn < limit" since we set the lastNumber for the next iteration at the end of each iteration, so
        // the number spoken on the "limit" turn is the value of lastNumber at the end of the iteration before it
        for (long turn = input.size(); turn < limit; turn++) {
            long nextLastNumber = store.containsKey(lastNumber) ? turn - store.get(lastNumber) : 0;
            store.put(lastNumber, turn);
            lastNumber = nextLastNumber;
        }

        return lastNumber;
    }
}
