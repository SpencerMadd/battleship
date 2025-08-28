public class Cell {
    private boolean isShip;
    private boolean isHit;
    private int shipId; // -1 for no ship, otherwise unique ship identifier
    
    public Cell() {
        this.isShip = false;
        this.isHit = false;
        this.shipId = -1;
    }
    
    public String toString(boolean showShips) {
        if (isHit && isShip) {
            return "[ X ]"; // Hit ship
        } else if (isHit) {
            return "[ • ]"; // Miss
        } else if (isShip && showShips) {
            return "[ S ]"; // Ship (only shown if allowed)
        } else {
            return "[   ]"; // Unexplored water
        }
    }
    
    public boolean takeFire() {
        if (!isHit) {
            isHit = true;
            return isShip;
        }
        return false; // Already hit
    }
    
    public void placeShip(int shipId) {
        this.isShip = true;
        this.shipId = shipId;
    }
    
    public boolean isShip() {
        return isShip;
    }
    
    public boolean isHit() {
        return isHit;
    }
    
    public int getShipId() {
        return shipId;
    }
    
    public boolean isSunk() {
        return isHit && isShip;
    }
}