package days;

import static utils.InputUtils.asStringStream;

public class Day2 implements Day {

    public static void main(String[] args) {
        new Day2().printAnswers();
    }

    public Day2() {

    }

    @Override
    public Object partOne() {
        return asStringStream(2)
                .map(Password::new)
                .filter(Password::isValidPartOne)
                .count();
    }

    @Override
    public Object partTwo() {
        return asStringStream(2)
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
