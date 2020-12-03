package days;

public interface Day {

    default void printAnswers() {
        System.out.println("Part 1: " + partOne());
        System.out.println("Part 2: " + partTwo());
    }

    Object partOne();

    Object partTwo();
}
