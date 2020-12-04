package days;

import static utils.InputUtils.asGrid;

public class DayThree implements Day {

    public static void main(String[] args) {
        new DayThree().printAnswers();
    }

    public DayThree() {

    }

    @Override
    public Object partOne() {
        char[][] input = asGrid(3);

        return numTrees(input, 3, 1);
    }

    @Override
    public Object partTwo() {
        char[][] input = asGrid(3);

        return numTrees(input, 1, 1) * numTrees(input, 3, 1) * numTrees(input, 5, 1)
                * numTrees(input, 7, 1) * numTrees(input, 1, 2);
    }

    private long numTrees(char[][] grid, int x, int y) {
        int numTrees = 0;

        for (int i = 0; i < grid.length; i += y) {
            // x position is current y (aka i) * x
            // e.g. first iteration 3,1 (i = currentY = 1, so currentX = i * x = 3)
            int currentX = i * x;

            // input repeats horizontally as necessary but grid is one "tile" of input
            // so adjust x back using modulo row length
            int adjustedX = currentX % grid[i].length;

            if (grid[i][adjustedX] == '#') {
                numTrees++;
            }
        }

        return numTrees;
    }
}
