package _2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static utils.InputUtils.asString;

public class Day16 extends Day {

    public static void main(String[] args) {
        new Day16().printAnswers();
    }

    private final List<Rule> rules;
    private final Ticket myTicket;
    private final List<Ticket> nearbyTickets;

    Day16() {
        super(16);

        String[] input = asString(YEAR, DAY).split("\n\n");

        this.rules = Arrays.stream(input[0].split("\n"))
                .map(Rule::new)
                .collect(Collectors.toList());

        this.myTicket = new Ticket(input[1].replace("your ticket:\n", ""));

        String[] nearby = input[2].replace("nearby tickets:\n", "").split("\n");
        this.nearbyTickets = Arrays.stream(nearby)
                .map(Ticket::new)
                .collect(Collectors.toList());
    }

    @Override
    Object partOne() {
        return nearbyTickets.stream()
                .map(t -> t.values)
                .flatMap(v -> v.stream()
                        .filter(n -> rules.stream().noneMatch(r -> r.isValid(n)))
                )
                .mapToInt(i -> i)
                .sum();
    }

    @Override
    Object partTwo() {
        List<Ticket> validTickets = nearbyTickets.stream()
                .filter(t -> t.values.stream()
                        .allMatch(v -> rules.stream()
                                .anyMatch(r -> r.isValid(v))
                        )
                )
                .collect(Collectors.toList());

        Map<Integer, List<Rule>> mappings = new HashMap<>();
        for (Rule rule : rules) {
            for (int i = 0; i < myTicket.values.size(); i++) {
                int finalI = i;

                boolean validForEveryTicket = validTickets.stream()
                        .allMatch(t -> rule.isValid(t.values.get(finalI)));

                // if the rule is valid at index i for every ticket then add it as a possible mapping
                if (validForEveryTicket) {
                    mappings.computeIfAbsent(i, j -> new ArrayList<>()).add(rule);
                }
            }
        }

        // for there to be a solution there must be at least one index in our possible mappings map
        // that is only valid for one rule at the start, so we can work our way through the map in this way:
        //
        // take the entry that says an index is only valid for one rule (aka that rule is definitely for that index)
        // then for every other entry in the map, remove the rule from the list of possibles
        // at this point another entry will have a definitive mapping, so we can repeat the process
        //
        // when finished we end up with a map of indexes, each to a single rule
        Set<Integer> processedIndexes = new HashSet<>();
        Optional<Map.Entry<Integer, List<Rule>>> opt;
        while ((opt = findDefiniteRule(mappings, processedIndexes)).isPresent()) {
            // this entry is index to a list with one rule, so we are sure that the rule is for that index
            Map.Entry<Integer, List<Rule>> definite = opt.get();

            int index = definite.getKey();
            Rule rule = definite.getValue().get(0);

            // for every entry in the map
            for (Map.Entry<Integer, List<Rule>> entry : mappings.entrySet()) {
                // if the entry is not the one we're certain about then remove the rule from the list of possibles
                // for that entry
                if (!entry.equals(definite)) {
                    entry.getValue().remove(rule);
                }
            }

            // add the definite index to set of processed indexes so we don't handle it again
            processedIndexes.add(index);
        }

        return mappings.entrySet().stream()
                .filter(e -> e.getValue().get(0).isDeparture())
                .mapToLong(v -> myTicket.values.get(v.getKey()))
                .reduce(1L, (a, b) -> a * b);
    }

    private Optional<Map.Entry<Integer, List<Rule>>> findDefiniteRule(Map<Integer, List<Rule>> mapping, Set<Integer> processedIndexes) {
        return mapping.entrySet().stream()
                .filter(o -> o.getValue().size() == 1 && !processedIndexes.contains(o.getKey()))
                .findAny();
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

        boolean isDeparture() {
            return name.contains("departure");
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
