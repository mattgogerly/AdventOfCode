package _2022;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.InputUtils.asStringStream;

class Day05 extends Day {

    public static void main(String[] args) {
        new Day05().printAnswers();
    }

    private static final Pattern movePattern = Pattern.compile("move (\\d+?) from (\\d+?) to (\\d+?)");

    Day05() {
        super(5);
    }

    @Override
    public Object partOne() {
        List<Deque<Character>> state = getStartState();
        List<Move> moves = getMoves();

        for (Move move : moves) {
            for (int i = 0; i < move.quantity; i++) {
                char c = state.get(move.from - 1).removeLast();
                state.get(move.to - 1).add(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Deque<Character> stack : state) {
            sb.append(stack.getLast());
        }

        return sb;
    }

    @Override
    public Object partTwo() {
        List<Deque<Character>> state = getStartState();
        List<Move> moves = getMoves();

        for (Move move : moves) {
            Stack<Character> crates = new Stack<>();
            for (int i = 0; i < move.quantity; i++) {
                crates.push(state.get(move.from - 1).removeLast());
            }

            while (!crates.isEmpty()) {
                state.get(move.to - 1).addLast(crates.pop());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Deque<Character> stack : state) {
            sb.append(stack.getLast());
        }

        return sb;
    }

    record Move(int quantity, int from, int to) {}

    private List<Move> getMoves() {
        return asStringStream(YEAR, DAY)
                .map(String::trim)
                .map(movePattern::matcher)
                .filter(Matcher::matches)
                .map(s -> new Move(Integer.parseInt(s.group(1)), Integer.parseInt(s.group(2)), Integer.parseInt(s.group(3))))
                .toList();
    }

    private List<Deque<Character>> getStartState() {
        List<Deque<Character>> state = new ArrayList<>();
        state.add(new ArrayDeque<>(List.of('L', 'N', 'W', 'T', 'D')));
        state.add(new ArrayDeque<>(List.of('C', 'P', 'H')));
        state.add(new ArrayDeque<>(List.of('W', 'P', 'H', 'N', 'D', 'G', 'M', 'J')));
        state.add(new ArrayDeque<>(List.of('C', 'W', 'S', 'N', 'T', 'Q', 'L')));
        state.add(new ArrayDeque<>(List.of('P', 'H', 'C', 'N')));
        state.add(new ArrayDeque<>(List.of('T', 'H', 'N', 'D', 'M', 'W', 'Q', 'B')));
        state.add(new ArrayDeque<>(List.of('M', 'B', 'R', 'J', 'G', 'S', 'L')));
        state.add(new ArrayDeque<>(List.of('Z', 'N', 'W', 'G', 'V', 'B', 'R', 'T')));
        state.add(new ArrayDeque<>(List.of('W', 'G', 'D', 'N', 'P', 'L')));

        return state;
    }
}
