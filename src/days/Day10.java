package days;

import java.util.List;
import java.util.stream.Collectors;

import static utils.InputUtils.asIntegerStream;

class Day10 extends Day {

    public static void main(String[] args) {
        new Day10().printAnswers();
    }

    Day10() {
        super(10);
    }

    @Override
    public Object partOne() {
        List<Integer> input = asIntegerStream(DAY)
                .sorted()
                .collect(Collectors.toList());

        input.add(0, 0); // socket is 0 jolts

        int oneCount = 0;
        int threeCount = 1; // always a 3 jolt difference between last adapter and socket

        for (int i = 0; i < input.size(); i++) {
            if (i + 1 == input.size()) {
                break;
            }

            int diff = input.get(i + 1) - input.get(i);
            if (diff == 1) {
                oneCount++;
            } else if (diff == 3) {
                threeCount++;
            }
        }

        return oneCount * threeCount;
    }

    @Override
    public Object partTwo() {
        List<Integer> input = asIntegerStream(DAY)
                .sorted()
                .collect(Collectors.toList());

        input.add(0, 0); // socket is 0 jolts

        int target = input.get(input.size() - 1) + 3; // device is max + 3 jolts
        input.add(input.size(), target);

        return getCombinations(input, target);
    }

    private long getCombinations(List<Integer> input, int target) {
        long[] dp = new long[target + 1];

        dp[0] = 1;

        for (int i = 0; i < input.size(); i++) {
            int adapter = input.get(i);

            for (int j = i + 1; j < input.size() && j <= i + 3; j++) {
                if (input.get(j) - adapter <= 3) {
                    dp[input.get(j)] += dp[adapter];
                }
            }
        }

        return dp[target];
    }
}
