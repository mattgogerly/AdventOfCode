package _2020;

import static utils.InputUtils.asStringStream;

class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().printAnswers();
    }

    Day02() {
        super(2);
    }

    @Override
    public Object partOne() {
        return asStringStream(YEAR, DAY)
                .map(Password::new)
                .filter(Password::isValidPartOne)
                .count();
    }

    @Override
    public Object partTwo() {
        return asStringStream(YEAR, DAY)
                .map(Password::new)
                .filter(Password::isValidPartTwo)
                .count();
    }

    private static class Password {

        int min;
        int max;
        char match;
        String password;

        Password(String line) {
            String[] parts = line.split(" ");

            String[] limits = parts[0].split("-");
            int min = Integer.parseInt(limits[0]);
            int max = Integer.parseInt(limits[1]);

            char c = parts[1].charAt(0);
            String password = parts[2];

            this.min = min;
            this.max = max;
            this.match = c;
            this.password = password;
        }

        boolean isValidPartOne() {
            long count = password.chars()
                    .filter(c -> c == match)
                    .count();

            return count >= min && count <= max;
        }

        boolean isValidPartTwo() {
            char firstIndex = password.charAt(min - 1);
            char secondIndex = password.charAt(max - 1);

            return firstIndex != secondIndex && (firstIndex == match || secondIndex == match);
        }
    }
}
