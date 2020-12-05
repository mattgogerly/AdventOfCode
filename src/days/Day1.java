package days;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.InputUtils.asIntegerStream;

public class Day1 implements Day {

    public static void main(String[] args) {
        new Day1().printAnswers();
    }

    public Day1() {

    }

    @Override
    public Object partOne() {
        List<Integer> input = asIntegerStream(1).collect(Collectors.toList());

        Map<Integer, Integer> complements = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            int current = input.get(i);
            int complement = 2020 - current;

            if (complements.containsKey(current)) {
                return current * complement;
            }

            complements.put(complement, i);
        }

        throw new IllegalArgumentException("No pair of inputs sum to 2020");
    }

    @Override
    public Object partTwo() {
        List<Integer> input = asIntegerStream(1)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < input.size() - 2; i++) {
            int first = input.get(i);
            int j = i + 1;
            int k = input.size() - 1;

            while (j < k) {
                int second = input.get(j);
                int third = input.get(k);
                int sum = first + second + third;

                if (sum == 2020) {
                    return first * second * third;
                } else if (sum > 2020) {
                    k--;
                } else {
                    j++;
                }
            }
        }

        throw new IllegalArgumentException("No trio of inputs sum to 2020");
    }
}
