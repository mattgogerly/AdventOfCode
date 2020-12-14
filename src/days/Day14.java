package days;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static utils.InputUtils.asStringStream;

public class Day14 implements Day {

    public static void main(String[] args) {
        new Day14().printAnswers();
    }

    @Override
    public Object partOne() {
        List<String> input = asStringStream(14).collect(Collectors.toList());

        NavigationComputer navigationComputer = new NavigationComputer();
        for (String l : input) {
            navigationComputer.processLine(l, true);
        }

        return navigationComputer.memory.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    public Object partTwo() {
        List<String> input = asStringStream(14).collect(Collectors.toList());

        NavigationComputer navigationComputer = new NavigationComputer();
        for (String l : input) {
            navigationComputer.processLine(l, false);
        }

        return navigationComputer.memory.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private static class NavigationComputer {

        private static final Pattern maskPattern = Pattern.compile("mask = (.+)");
        private static final Pattern memoryPattern = Pattern.compile("mem\\[([0-9]{1,36})] = (.+)");

        private String mask;
        private final Map<Long, Long> memory;

        NavigationComputer() {
            memory = new HashMap<>();
            mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        }

        void processLine(String line, boolean partOne) {
            Matcher lineMatcher = maskPattern.matcher(line);
            if (lineMatcher.matches()) {
                setMask(lineMatcher.group(1));
            } else {
                lineMatcher = memoryPattern.matcher(line);
                if (!lineMatcher.matches()) {
                    throw new IllegalArgumentException("Invalid memory address or value");
                }

                if (partOne) {
                    updateMemoryWithValueMask(lineMatcher.group(1), lineMatcher.group(2));
                } else {
                    updateMemoryWithAddressMask(lineMatcher.group(1), lineMatcher.group(2));
                }
            }
        }

        void setMask(String mask) {
            this.mask = mask;
        }

        void updateMemoryWithValueMask(String a, String v) {
            long address = Long.parseLong(a);
            long value = Long.parseLong(v);

            value = value | Long.parseLong(mask.replaceAll("X", "0"), 2);

            memory.put(address, value);
        }

        void updateMemoryWithAddressMask(String a, String v) {
            long address = Long.parseLong(a);
            long value = Long.parseLong(v);

            String binary = padLeft(convertToBinary(address), 36);

            String result = IntStream.range(0, mask.length())
                    .map(i -> mask.charAt(i) == 'X' ? 'X' : mask.charAt(i) == '0' ? binary.charAt(i) : mask.charAt(i))
                    .mapToObj(c -> (char) c)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            long floatCount = result.chars()
                    .filter(c -> c == 'X')
                    .count();

            int combinations = (int) Math.pow(2, floatCount);
            for (int i = 0; i < combinations; i++) {
                String[] combs = padLeft(convertToBinary(i), (int) floatCount).split("");
                List<String> comb = new ArrayList<>(Arrays.asList(combs));

                StringBuilder resultCopy = new StringBuilder();
                for (int j = 0; j < result.length(); j++) {
                    char c = result.charAt(j);
                    if (c == 'X') {
                        resultCopy.append(comb.remove(0));
                    } else {
                        resultCopy.append(c);
                    }
                }

                Long newAddress = Long.parseLong(resultCopy.toString(), 2);
                memory.put(newAddress, value);
            }
        }

        private String convertToBinary(long value) {
            return Long.toBinaryString(value);
        }

        private String padLeft(String input, int number) {
            if (input.length() >= number) {
                return input;
            }

            StringBuilder sb = new StringBuilder();
            while (sb.length() < number - input.length()) {
                sb.append('0');
            }

            sb.append(input);

            return sb.toString();
        }
    }
}