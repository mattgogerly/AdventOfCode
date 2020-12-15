package days;

import java.util.*;
import java.util.stream.Collectors;

import static utils.InputUtils.asLongStream;

public class Day09 implements Day {

    public static void main(String[] args) {
        new Day09().printAnswers();
    }

    @Override
    public Object partOne() {
        List<Long> input = asLongStream(9)
                .collect(Collectors.toList());

        Encoder encoder = new Encoder(input);
        return encoder.firstInvalid;
    }

    @Override
    public Object partTwo() {
        List<Long> input = asLongStream(9)
                .collect(Collectors.toList());

        Encoder encoder = new Encoder(input);
        return encoder.findContiguousSumToFirstInvalid();
    }


    static class Encoder {

        private final List<Long> input;
        private final List<Long> trail;

        private final long firstInvalid;
        private final int firstInvalidIndex;
        private int index;

        Encoder(List<Long> input) {
            this.input = input;
            this.index = 0;
            this.trail = new ArrayList<>(25);

            for (; index < 25; index++) {
                trail.add(input.get(index));
            }

            this.firstInvalid = findFirstInvalid();
            this.firstInvalidIndex = index;
        }

        private long findFirstInvalid() {
            while (index < input.size()) {
                long current = input.get(index);

                if (!isValid(current)) {
                    return current;
                }

                // remove oldest int from the queue and add the next
                trail.remove(0);
                trail.add(current);

                index++;
            }

            throw new IllegalArgumentException("No invalid number");
        }

        private boolean isValid(long target) {
            Map<Long, Integer> complements = new HashMap<>();

            for (int i = 0; i < trail.size(); i++) {
                long current = trail.get(i);

                if (complements.containsKey(current)) {
                    return true;
                }

                long complement = target - current;
                complements.put(complement, i);
            }

            return false;
        }

        private long findContiguousSumToFirstInvalid() {
            for (int windowSize = 2; windowSize < input.size(); windowSize++) {
                for (int low = firstInvalidIndex - windowSize; low > 0; low--) {
                    List<Long> window = input.subList(low, low + windowSize);
                    long sum = window.stream()
                            .reduce(0L, Long::sum);

                    if (sum == firstInvalid) {
                        Collections.sort(window);

                        long min = window.get(0);
                        long max = window.get(window.size() - 1);

                        return min + max;
                    }
                }
            }

            throw new IllegalArgumentException("No possible solution");
        }
    }
}
