package days;

public abstract class Day {

    protected static int DAY;

    Day(int dayNumber) {
        DAY = dayNumber;
    }

    void printAnswers() {
        System.out.println("Part 1: " + partOne());
        System.out.println("Part 2: " + partTwo());
    }

    abstract Object partOne();

    abstract Object partTwo();
}
