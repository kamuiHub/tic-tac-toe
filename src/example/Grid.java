package example;

import java.util.HashMap;
import java.util.Map;

public class Grid {
    private final Map<Integer, String> coordinates;
    private static Grid grid;

    private Grid() {
        this.coordinates = new HashMap<>();
    }

    public boolean addCoordinate(Coordinate coordinate) {
        if (isCoordinateExist(coordinate.block())) {
            System.out.println("Square not empty");
            return false;
        }
        coordinates.put(coordinate.block(), coordinate.sprite());
        return true;
    }

    private boolean isCoordinateExist(int square) {
        return coordinates.containsKey(square);
    }

    public Map<Integer, String> getCoordinates() {
        return coordinates;
    }

    public static Grid getInstance() {
        if (grid == null)
            grid = new Grid();
        return grid;
    }
}
