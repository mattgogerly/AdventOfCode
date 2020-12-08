package days;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static utils.InputUtils.asStringStream;

public class Day8 implements Day {

    public static void main(String[] args) {
        new Day8().printAnswers();
    }

    @Override
    public Object partOne() {
        List<Command> commands = getCommands();

        Computer computer = new Computer(commands);
        computer.run();
        return computer.accumulator;
    }

    @Override
    public Object partTwo() {
        List<Command> commands = getCommands();

        for (int i = 0; i < commands.size(); i++) {
            List<Command> changedCommands = changeCommandAtIndex(commands, i);

            Computer computer = new Computer(changedCommands);
            computer.run();

            if (computer.position >= changedCommands.size()) {
                return computer.accumulator;
            }
        }

        throw new IllegalArgumentException("No solution found");
    }

    private static List<Command> getCommands() {
        return asStringStream(8)
                .map(s -> s.split(" "))
                .map(a -> new Command(a[0], a[1]))
                .collect(Collectors.toList());
    }

    private List<Command> changeCommandAtIndex(List<Command> commands, int index) {
        Command current = commands.get(index);

        if (current.op.equals("jmp") || current.op.equals("nop")) {
            String oppositeOp = current.op.equals("jmp") ? "nop" : "jmp";
            Command newCommand = new Command(oppositeOp, current.param);

            List<Command> commandsCopy = getCommands();
            commandsCopy.remove(index);
            commandsCopy.add(index, newCommand);

            return commandsCopy;
        }

        return commands;
    }


    private static class Computer {

        private final List<Command> commands;

        private int position;
        private long accumulator;

        Computer(List<Command> commands) {
            this.commands = commands;
            this.position = 0;
            this.accumulator = 0;
        }

        private void run() {
            Set<Command> seen = new HashSet<>();

            while (position < commands.size()) {
                Command current = commands.get(position);
                if (seen.contains(current)) {
                    break;
                }

                switch (current.op) {
                    case "acc":
                        accumulator += Integer.parseInt(current.param);
                        break;
                    case "jmp":
                        position += Integer.parseInt(current.param) - 1;
                        break;
                    case "nop":
                        break;
                }

                seen.add(current);
                position++;
            }
        }
    }

    private static class Command {

        private final String op;
        private final String param;

        Command(String op, String param) {
            this.op = op;
            this.param = param;
        }
    }
}
