import java.util.HashMap;
import java.util.Map;

public class Board {
    private Cell[][] grid;
    private boolean isPlayer;
    private Map<Integer, Integer> shipHealth; // shipId -> remaining health
    private int nextShipId;
    private int sunkShips;
    
    public Board(boolean isPlayer) {
        this.isPlayer = isPlayer;
        this.grid = new Cell[10][10];
        this.shipHealth = new HashMap<>();
        this.nextShipId = 0;
        this.sunkShips = 0;
        
        initializeGrid();
    }
    
    private void initializeGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    
    public void printBoard() {
        // Print column headers
        System.out.print("    ");
        for (char c = 'A'; c <= 'J'; c++) {
            System.out.print("  " + c + "  ");
        }
        System.out.println();
        
        // Print grid with row numbers
        for (int i = 0; i < grid.length; i++) {
            System.out.printf("%2d  ", i + 1);
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j].toString(isPlayer));
            }
            System.out.println();
        }
    }
    
    public boolean guessSpot(int col, int row) {
        if (isValidPosition(row, col)) {
            return grid[row][col].takeFire();
        }
        return false;
    }
    
    public boolean isAlreadyGuessed(int col, int row) {
        return isValidPosition(row, col) && grid[row][col].isHit();
    }
    
    public boolean placeShip(int col, int row, String direction, int length) {
        if (!canPlaceShip(col, row, direction, length)) {
            return false;
        }
        
        int shipId = nextShipId++;
        shipHealth.put(shipId, length);
        
        // Place the ship
        placeShipSegment(col, row, shipId);
        
        if (direction.equalsIgnoreCase("up")) {
            for (int i = 1; i < length; i++) {
                placeShipSegment(col, row - i, shipId);
            }
        } else if (direction.equalsIgnoreCase("down")) {
            for (int i = 1; i < length; i++) {
                placeShipSegment(col, row + i, shipId);
            }
        } else if (direction.equalsIgnoreCase("left")) {
            for (int i = 1; i < length; i++) {
                placeShipSegment(col - i, row, shipId);
            }
        } else if (direction.equalsIgnoreCase("right")) {
            for (int i = 1; i < length; i++) {
                placeShipSegment(col + i, row, shipId);
            }
        }
        
        return true;
    }
    
    private void placeShipSegment(int col, int row, int shipId) {
        if (isValidPosition(row, col)) {
            grid[row][col].placeShip(shipId);
        }
    }
    
    public boolean isCleared() {
        return sunkShips == shipHealth.size();
    }
    
    public int getSunkShips() {
        return sunkShips;
    }
    
    public boolean isShipSunk(int col, int row) {
        if (!isValidPosition(row, col) || !grid[row][col].isShip()) {
            return false;
        }
        
        int shipId = grid[row][col].getShipId();
        if (shipId == -1) return false;
        
        // Check if all segments of this ship are hit
        int hitSegments = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getShipId() == shipId && grid[i][j].isHit()) {
                    hitSegments++;
                }
            }
        }
        
        if (hitSegments == shipHealth.get(shipId)) {
            sunkShips++;
            return true;
        }
        
        return false;
    }
    
    public boolean canPlaceShip(int col, int row, String direction, int length) {
        if (!isValidPosition(row, col)) {
            return false;
        }
        
        // Check if the starting position is occupied
        if (grid[row][col].isShip()) {
            return false;
        }
        
        // Check all segments in the desired direction
        if (direction.equalsIgnoreCase("up")) {
            if (row - length + 1 < 0) return false;
            for (int i = 0; i < length; i++) {
                if (!isValidPosition(row - i, col) || grid[row - i][col].isShip()) {
                    return false;
                }
            }
        } else if (direction.equalsIgnoreCase("down")) {
            if (row + length - 1 >= 10) return false;
            for (int i = 0; i < length; i++) {
                if (!isValidPosition(row + i, col) || grid[row + i][col].isShip()) {
                    return false;
                }
            }
        } else if (direction.equalsIgnoreCase("left")) {
            if (col - length + 1 < 0) return false;
            for (int i = 0; i < length; i++) {
                if (!isValidPosition(row, col - i) || grid[row][col - i].isShip()) {
                    return false;
                }
            }
        } else if (direction.equalsIgnoreCase("right")) {
            if (col + length - 1 >= 10) return false;
            for (int i = 0; i < length; i++) {
                if (!isValidPosition(row, col + i) || grid[row][col + i].isShip()) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 10 && col >= 0 && col < 10;
    }
}