package _2020;

import java.util.List;

import static utils.InputUtils.asStringStream;

class Day05 extends Day {

    public static void main(String[] args) {
        new Day05().printAnswers();
    }

    Day05() {
        super(5);
    }

    @Override
    public Object partOne() {
        return asStringStream(YEAR, DAY)
                .map(Day05::calculateId)
                .max(Integer::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("No max seat ID"));
    }

    @Override
    public Object partTwo() {
        List<Integer> ids = asStringStream(YEAR, DAY)
                .map(Day05::calculateId)
                .sorted()
                .toList();

        for (int i = 0; i < ids.size(); i++) {
            int next = ids.get(i) + 1;

            if (next != ids.get(i + 1)) {
                return next;
            }
        }

        throw new IllegalArgumentException("No available seat!");
    }

    private static int calculateId(String s) {
        // the instruction set for each seat can be converted into a binary representation
        // of the seat ID by substituting B/R for 1 and F/L for 0, since the seat ID is:
        // row ID multiplied by 8 (i.e. shifted left 3 places) + column ID
        s = s.replaceAll("[BR]", "1").replaceAll("[FL]", "0");
        return Integer.parseInt(s, 2);
    }
}
