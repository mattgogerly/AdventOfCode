package _2020;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.InputUtils.asString;

class Day04 extends Day {

    public static void main(String[] args) {
        new Day04().printAnswers();
    }

    private static final Map<String, String> requiredFields = Map.of(
            "byr", "^(19[2-9][0-9]|200[0-2])$",
            "iyr", "^(201[0-9]|2020)$",
            "eyr", "^(202[0-9]|2030)$",
            "hgt", "^((1[5-8][0-9]|19[0-3])cm)|((59|6[0-9]|7[0-6])in)$",
            "hcl", "^#[0-9a-f]{6}$",
            "ecl", "^(amb|blu|brn|gry|grn|hzl|oth)$",
            "pid", "^[0-9]{9}$"
    );

    Day04() {
        super(4);
    }

    @Override
    public Object partOne() {
        return passportStream()
                .filter(Passport::validPartOne)
                .count();
    }

    @Override
    public Object partTwo() {
        return passportStream()
                .filter(Passport::validPartTwo)
                .count();
    }

    private Stream<Passport> passportStream() {
        // each entry is separated by a double newline
        String[] input = asString(YEAR, DAY).split("\n\n");

        // map each String (line) to a Passport
        return Arrays.stream(input).map(Passport::new);
    }


    private static class Passport {

        String[] values;

        Passport(String p) {
            // some passports are over multiple lines so join them with a space
            // then split the passport into each of its fields (which are space separated)
            values = p.replace('\n', ' ').split(" ");
        }

        public boolean validPartOne() {
            // each field key is 3 letters so substring it and check we have all the required fields
            return Arrays.stream(values)
                    .map(v -> v.substring(0, 3))
                    .collect(Collectors.toSet())
                    .containsAll(requiredFields.keySet());
        }

        public boolean validPartTwo() {
            boolean partTwoValid = Arrays.stream(values)
                    .map(s -> s.split(":"))
                    .allMatch(s -> validValue(s[0], s[1]));

            return validPartOne() && partTwoValid;
        }

        private boolean validValue(String key, String value) {
            if (!requiredFields.containsKey(key)) {
                // not a required field so ignore it
                return true;
            }

            return value.matches(requiredFields.get(key));
        }
    }
}
