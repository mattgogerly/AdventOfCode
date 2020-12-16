package days;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static utils.InputUtils.asString;

public class Day16 extends Day {

    public static void main(String[] args) {
        new Day16().printAnswers();
    }

    private List<Rule> rules;
    private Ticket myTicket;
    private List<Ticket> nearbyTickets;

    Day16() {
        super(16);

        String[] input = asString(DAY).split("\n\n");

        this.rules = Arrays.stream(input[0].split("\n"))
                .map(Rule::new)
                .collect(Collectors.toList());

        this.myTicket = new Ticket(input[1].split("\n")[1]);

        String[] nearby = input[2].replace("nearby tickets:\n", "").split("\n");
        this.nearbyTickets = Arrays.stream(nearby)
                .map(Ticket::new)
                .collect(Collectors.toList());
    }

    @Override
    Object partOne() {
        return nearbyTickets.stream()
                .map(t -> t.values)
                .flatMap(v -> v.stream().filter(n -> rules.stream().noneMatch(r -> r.isValid(n))))
                .mapToInt(i -> i)
                .sum();
    }

    @Override
    Object partTwo() {
        List<Ticket> validTickets = nearbyTickets.stream()
                .filter(t -> t.values.stream().allMatch(v -> rules.stream().anyMatch(r -> r.isValid(v))))
                .collect(Collectors.toList());

        return null;
    }


    private static class Rule {

        private static final Pattern rulePattern = Pattern.compile("(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)");

        private final String name;
        private final int firstLower;
        private final int firstUpper;
        private final int secondLower;
        private final int secondUpper;

        Rule(String s) {
            Matcher m = rulePattern.matcher(s);

            if (!m.matches()) {
                throw new IllegalArgumentException("Invalid rule " + s);
            }

            this.name = m.group(1);
            this.firstLower = Integer.parseInt(m.group(2));
            this.firstUpper = Integer.parseInt(m.group(3));
            this.secondLower = Integer.parseInt(m.group(4));
            this.secondUpper = Integer.parseInt(m.group(5));
        }

        boolean isValid(int value) {
            return (value >= firstLower && value <= firstUpper) || (value >= secondLower && value <= secondUpper);
        }
    }


    private static class Ticket {

        List<Integer> values;

        Ticket(String s) {
            this.values = Arrays.stream(s.split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());
        }
    }
}
