package _2022;

import static utils.InputUtils.asStringStream;

class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().printAnswers();
    }

    Day02() {
        super(2);
    }

    @Override
    public Object partOne() {
        return asStringStream(YEAR, DAY)
                .map(r -> r.split(" "))
                .map(r -> new Round(r[0], r[1]))
                .map(Round::calculateScore)
                .mapToInt(i -> i)
                .sum();
    }

    @Override
    public Object partTwo() {
        return asStringStream(YEAR, DAY)
                .map(r -> r.split(" "))
                .map(r -> new Round(r[0], r[1]))
                .map(Round::calculateScorePartTwo)
                .mapToInt(i -> i)
                .sum();
    }

    static class Round {

        private final String opponentMove;
        private String myMove;

        public Round(String opponentMove, String myMove) {
            this.opponentMove = opponentMove;
            this.myMove = myMove;
        }

        int calculateScore() {
            return moveScore() + (isWin() ? 6 : isDraw() ? 3 : 0);
        }

        int calculateScorePartTwo() {
            myMove = determineRequiredMove();
            return calculateScore();
        }

        int moveScore() {
            return switch (myMove) {
                case "X" -> 1;
                case "Y" -> 2;
                case "Z" -> 3;
                default -> throw new IllegalArgumentException("Illegal move!");
            };
        }

        boolean isDraw() {
            return getDrawMove().equals(myMove);
        }

        boolean isWin() {
            return getWinningMove().equals(myMove);
        }

        String getDrawMove() {
            return switch (opponentMove) {
                case "A" -> "X";
                case "B" -> "Y";
                case "C" -> "Z";
                default -> throw new IllegalArgumentException("Illegal opponent move!");
            };
        }

        String getWinningMove() {
            return switch (opponentMove) {
                case "A" -> "Y";
                case "B" -> "Z";
                case "C" -> "X";
                default -> throw new IllegalArgumentException("Illegal opponent move!");
            };
        }

        String getLosingMove() {
            return switch (opponentMove) {
                case "A" -> "Z";
                case "B" -> "X";
                case "C" -> "Y";
                default -> throw new IllegalArgumentException("Illegal opponent move!");
            };
        }

        String determineRequiredMove() {
            return switch (myMove) {
                case "X" -> getLosingMove();
                case "Y" -> getDrawMove();
                case "Z" -> getWinningMove();
                default -> throw new IllegalArgumentException("Illegal opponent move!");
            };
        }
    }
}
