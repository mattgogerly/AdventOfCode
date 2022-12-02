package _2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.InputUtils.asIntegerStream;

class Day01 extends Day {

    public static void main(String[] args) {
        new Day01().printAnswers();
    }

    Day01() {
        super(1);
    }

    @Override
    public Object partOne() {
        List<Integer> input = asIntegerStream(YEAR, DAY).toList();

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
        List<Integer> input = asIntegerStream(YEAR, DAY)
                .sorted()
                .toList();

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
