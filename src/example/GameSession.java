package example;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class GameSession {
    private static final int MAX_MOVES = 9;
    private final String symbolX = "✕";
    private final String symbolO = "◯";
    private State state;
    private final Grid grid;
    private int countMove;
    private static final String[] WIN_COMBINATIONS = {"123", "147", "159", "258", "369", "456", "789", "357"};

    public GameSession() {
        this.state = State.WAIT;
        this.grid = Grid.getInstance();
        this.countMove = 1;
    }

    public void init() {
        while (state == State.WAIT) {
            System.out.println("WELCOME IN TIC-TAC-TOE\nFor start to play write 1");
            Scanner scanner = new Scanner(System.in);
            try {
                if (scanner.nextInt() == 1) {
                    state = State.PLAY;
                }
                startGame(state);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid params. Try again.");
            }
        }
    }

    private void startGame(State state) {
        while (state == State.PLAY) {
            renderGrid();
            System.out.println("Move : " + getCurrentTurn() + " player");
            Scanner scanner = new Scanner(System.in);
            try {
                int getMove = scanner.nextInt();
                state = processPlayerMove(getMove);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    private State processPlayerMove(int move) {
        if (move > 9 || move < 0) {
            System.out.println("Invalid number. Please try again");
            return state;
        }

        if (addNewCoordinate(move)) {
            State gameState = checkIfWinGame();
            if (gameState!=State.WIN) {
                gameState = checkIfDraw();
            }
            countMove++;
            return gameState;
        }
        return state;
    }

    private State checkIfDraw() {
        if (endGameIfDraw()) {
            renderGrid();
            System.out.println("--- DRAW ---");
            return state = State.DRAW;
        }
        return state;
    }

    private State checkIfWinGame() {
        if (checkWinLineSprites()) {
            renderGrid();
            System.out.println("--- WIN PLAYER " + getCurrentTurn() + " ---");
            return state = State.WIN;
        }
        return state;
    }

    private void renderGrid() {
        RenderGrid.render(grid);
    }

    private boolean endGameIfDraw() {
        return countMove >= MAX_MOVES;
    }

    private boolean addNewCoordinate(int move) {
        return grid.addCoordinate(new Coordinate(move, getCurrentPlayerSymbol()));
    }

    private boolean checkWinLineSprites() { //Change method
        for (String winCombination : WIN_COMBINATIONS) {
            String[] positions = winCombination.split("");
            int f = Integer.parseInt(positions[0]);
            int s = Integer.parseInt(positions[1]);
            int t = Integer.parseInt(positions[2]);

            if (grid.getCoordinates().containsKey(f) && grid.getCoordinates().containsKey(s) && grid.getCoordinates().containsKey(t)) {
                if (Objects.equals(grid.getCoordinates().get(f), symbolX) && Objects.equals(grid.getCoordinates().get(s), symbolX) && Objects.equals(grid.getCoordinates().get(t), symbolX)) {
                    return true;
                } else if (Objects.equals(grid.getCoordinates().get(f), symbolO) && Objects.equals(grid.getCoordinates().get(s), symbolO) && Objects.equals(grid.getCoordinates().get(t), symbolO)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getCurrentPlayerSymbol() {
        if (countMove % 2 == 0)
            return symbolO;
        return symbolX;
    }

    private String getCurrentTurn() {
        return getCurrentPlayerSymbol();
    }
}
