package days;

import java.util.HashMap;
import java.util.Map;

import static utils.InputUtils.asGrid;

public class Day11 implements Day {

    public static void main(String[] args) {
        new Day11().printAnswers();
    }

    @Override
    public Object partOne() {
        char[][] grid = asGrid(11);

        Map<Coordinate, Character> changes = new HashMap<>();
        do {
            updateGrid(grid, changes);
            changes = findUpdates(grid);
        } while (!changes.isEmpty());

        return countOccupied(grid);
    }

    @Override
    public Object partTwo() {
        return null;
    }

    private Map<Coordinate, Character> findUpdates(char[][] grid) {
        Map<Coordinate, Character> changes = new HashMap<>();

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                char current = grid[row][column];

                char newState = determineNewSeatState(grid, row, column);

                if (newState != current) {
                    changes.put(new Coordinate(row, column), newState);
                }
            }
        }

        return changes;
    }

    private char determineNewSeatState(char[][] grid, int row, int column) {
        char current = grid[row][column];

        if (current == '.') {
            return current;
        }

        int numOccupiedAdjacent = numOccupiedAdjacent(grid, row, column);

        if (current == 'L' && numOccupiedAdjacent == 0) {
            return '#';
        } else if (current == '#' && numOccupiedAdjacent >= 4) {
            return 'L';
        }

        return current;
    }

    private int numOccupiedAdjacent(char[][] grid, int row, int column) {
        int occupiedAdjacent = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length) {
                    continue;
                }

                if (i == row && j == column) {
                    // don't include self
                    continue;
                }

                if (grid[i][j] == '#') {
                    occupiedAdjacent++;
                }
            }
        }

        return occupiedAdjacent;
    }

    private void updateGrid(char[][] grid, Map<Coordinate, Character> changes) {
        for (Map.Entry<Coordinate, Character> entry : changes.entrySet()) {
            Coordinate pos = entry.getKey();
            Character newState = entry.getValue();

            grid[pos.row][pos.column] = newState;
        }
    }

    private int countOccupied(char[][] grid) {
        int count = 0;

        for (char[] chars : grid) {
            for (char c : chars) {
                if (c == '#') {
                    count++;
                }
            }
        }

        return count;
    }


    private static class Coordinate {

        private final int row;
        private final int column;

        public Coordinate(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
