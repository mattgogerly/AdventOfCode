package days;

import java.util.ArrayList;
import java.util.List;

import static utils.InputUtils.asString;

class Day13 extends Day {

    public static void main(String[] args) {
        new Day13().printAnswers();
    }

    Day13() {
        super(13);
    }

    @Override
    public Object partOne() {
        String[] input = asString(DAY).split("\n");

        int time = Integer.parseInt(input[0]);
        String timetable = input[1];

        int minDiff = Integer.MAX_VALUE;
        int bestBus = 0;

        for (String bus : timetable.split(",")) {
            if (bus.equals("x")) {
                continue;
            }

            int busId = Integer.parseInt(bus);

            // modulo gives time between our start time and the departure before it, so the time we have to wait
            // is the interval of that bus minus how long it's already been going before our start time
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
        String[] input = asString(DAY).split("\n");
        String timetable = input[1];

        String[] buses = timetable.split(",");

        List<Long> moduli = new ArrayList<>();
        List<Long> residues = new ArrayList<>();

        for (int i = 0; i < buses.length; i++) {
            String bus = buses[i];
            if (bus.equals("x")) {
                continue;
            }


            long busId = Integer.parseInt(bus);
            moduli.add(busId);
            residues.add(busId - i);
        }

        return ChineseRemainderTheorem.chineseRemainder(moduli, residues);
    }


    /**
     * Adapted from https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
     */
    private static class ChineseRemainderTheorem {

        public static long chineseRemainder(List<Long> n, List<Long> a) {
            long prod = n.stream().reduce(1L, (i, j) -> i * j);

            long p, sm = 0;
            for (int i = 0; i < n.size(); i++) {
                long currentN = n.get(i);
                long currentA = a.get(i);

                if (currentN == 0L) {
                    continue;
                }

                p = prod / currentN;
                sm += currentA * mulInv(p, currentN) * p;
            }

            return sm % prod;
        }

        private static long mulInv(long a, long b) {
            long b0 = b;
            long x0 = 0;
            long x1 = 1;

            if (b == 1) {
                return 1;
            }

            while (a > 1) {
                long q = a / b;
                long amb = a % b;
                a = b;
                b = amb;
                long xqx = x1 - q * x0;
                x1 = x0;
                x0 = xqx;
            }

            if (x1 < 0) {
                x1 += b0;
            }

            return x1;
        }
    }
}
