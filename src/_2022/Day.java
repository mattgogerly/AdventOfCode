package _2022;

public abstract class Day {

    protected static int YEAR;
    protected static int DAY;

    Day(int dayNumber) {
        YEAR = 2022;
        DAY = dayNumber;
    }

    void printAnswers() {
        System.out.println("Part 1: " + partOne());
        System.out.println("Part 2: " + partTwo());
    }

    abstract Object partOne();

    abstract Object partTwo();
}
