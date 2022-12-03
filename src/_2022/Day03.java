package _2022;

import java.util.List;

import static utils.InputUtils.asStringStream;

class Day03 extends Day {

    public static void main(String[] args) {
        new Day03().printAnswers();
    }

    Day03() {
        super(3);
    }

    @Override
    public Object partOne() {
        return asStringStream(YEAR, DAY)
                .map(r -> {
                    String firstHalf = r.substring(0, r.length() / 2);
                    String secondHalf = r.substring(r.length() / 2);

                    return firstHalf
                            .chars()
                            .distinct()
                            .filter(c1 -> secondHalf.chars().anyMatch(c2 -> c2 == c1))
                            .map(this::toPriority)
                            .findFirst();
                })
                .mapToInt(i -> i.orElseThrow(() -> new IllegalArgumentException("No values intersect!")))
                .sum();
    }

    @Override
    public Object partTwo() {
        List<String> input = asStringStream(YEAR, DAY).toList();
        int sum = 0;
        for (int i = 0; i < input.size(); i += 3) {
            int finalI = i;
            sum += input.get(finalI)
                    .chars()
                    .distinct()
                    .filter(c1 -> input.get(finalI + 1).chars().anyMatch(c2 -> c2 == c1))
                    .filter(c2 -> input.get(finalI + 2).chars().anyMatch(c3 -> c3 == c2))
                    .map(this::toPriority)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No values intersect!"));
        }

        return sum;
    }

    int toPriority(int c) {
        if (Character.isUpperCase(c)) {
            return c - 'A' + 27;
        } else {
            return c - 'a' + 1;
        }
    }
}
