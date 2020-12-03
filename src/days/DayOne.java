package days;

import utils.InputUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayOne implements Day {

    public static void main(String[] args) {
        new DayOne().printAnswers();
    }

    public DayOne() {

    }

    @Override
    public Object partOne() {
        List<Integer> input = InputUtils.numberInput(1);

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
        List<Integer> input = InputUtils.numberInput(1);

        Collections.sort(input);

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
