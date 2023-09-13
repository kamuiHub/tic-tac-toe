package example;

public class RenderGrid {
    private final static int BLOCKS = 9;
    private final static String EMPTY_BLOCK = " - ";
    private static StringBuilder line;
    private final static String ANSI_RESET = "\u001B[0m";
    private final static String ANSI_RED = "\u001B[31m";
    private final static String ANSI_GREEN = "\u001B[32m";

    public static void render(Grid grid) {
        line = new StringBuilder();
        for (int numBlock = 1; numBlock <= BLOCKS; numBlock++) {
            if (coordinateIsExist(numBlock, grid)) {
                line.append(getSprite(numBlock, grid));
            } else {
                line.append(EMPTY_BLOCK);
            }
            handleLineBreak(numBlock);
        }
        renderOutput();
    }

    private static void handleLineBreak(int i) {
        if (i == 6 || i == 3) {
            line.append("\n");
        }
    }

    private static void renderOutput() {
        System.out.println(line);
    }

    private static boolean coordinateIsExist(int key, Grid grid) {
        return grid.getCoordinates().containsKey(key);
    }

    private static String getSprite(int key, Grid grid) {
        String sprite = grid.getCoordinates().get(key);
        if (sprite.equals("◯")) {
            return ANSI_RED + " " + sprite + " " + ANSI_RESET;
        } else {
            return ANSI_GREEN + " " + sprite + " " + ANSI_RESET;
        }
    }
}
