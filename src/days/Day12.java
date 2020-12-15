package days;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.InputUtils.asStringStream;

class Day12 extends Day {

    public static void main(String[] args) {
        new Day12().printAnswers();
    }

    Day12() {
        super(12);
    }

    @Override
    public Object partOne() {
        List<Instruction> input = asStringStream(DAY)
                .map(Instruction::new)
                .collect(Collectors.toList());

        Ferry ferry = new Ferry();
        for (Instruction i : input) {
            ferry.move(i);
        }

        return ferry.manhattanDistance();
    }

    @Override
    public Object partTwo() {
        return null;
    }


    private static class Ferry {

        private Direction facing;
        private int x;
        private int y;


        Ferry() {
            this.facing = Direction.E;
            this.x = 0;
            this.y = 0;
        }

        void move(Instruction i) {
            switch (i.direction) {
                case L:
                case R:
                    rotate(i);
                    break;
                case N:
                    this.y += i.step;
                    break;
                case S:
                    this.y -= i.step;
                    break;
                case E:
                    this.x += i.step;
                    break;
                case W:
                    this.x -= i.step;
                    break;
                case F:
                    move(new Instruction(facing, i.step));
                    break;
            }
        }

        void rotate(Instruction i) {
            int currentAngle = facing.angle;
            int newAngle;

            if (i.direction == Direction.L) {
                newAngle = currentAngle - i.step;
            } else {
                newAngle = currentAngle + i.step;
            }

            newAngle = newAngle % 360;
            newAngle = newAngle < 0 ? newAngle + 360 : newAngle;

            this.facing = Direction.findByAngle(newAngle);
        }

        double manhattanDistance() {
            return Math.abs(x) + Math.abs(y);
        }
    }


    private static class Instruction {

        private final Direction direction;
        private final int step;

        Instruction(String s) {
            this.direction = Direction.valueOf(s.substring(0, 1));
            this.step = Integer.parseInt(s.substring(1));
        }

        Instruction(Direction direction, int step) {
            this.direction = direction;
            this.step = step;
        }
    }


    private enum Direction {
        N(0),
        E(90),
        S(180),
        W(270),
        F,
        L,
        R;

        private static final Map<Integer, Direction> map;
        static {
            map = new HashMap<>();
            map.put(0, N);
            map.put(90, E);
            map.put(180, S);
            map.put(270, W);
        }

        private int angle;

        Direction() {

        }

        Direction(int angle) {
            this.angle = angle;
        }

        static Direction findByAngle(int angle) {
            return map.get(angle);
        }
    }
}
