package days;

import static utils.InputUtils.asString;

public class Day13 implements Day {

    public static void main(String[] args) {
        new Day13().printAnswers();
    }

    @Override
    public Object partOne() {
        String[] input = asString(13).split("\n");

        int time = Integer.parseInt(input[0]);
        String timetable = input[1];

        int minDiff = Integer.MAX_VALUE;
        int bestBus = 0;

        for (String bus : timetable.split(",")) {
            if (bus.equals("x")) {
                continue;
            }

            int busId = Integer.parseInt(bus);

            int diff = busId - time % busId;
            if (diff < minDiff) {
                minDiff = diff;
                bestBus = busId;
            }
        }

        return bestBus * minDiff;
    }

    @Override
    public Object partTwo() {
        return null;
    }
}
