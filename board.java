public class board {

    private cell[][] board;
    private boolean isPlayer;



    public board(boolean isPlayer){
        this.isPlayer = isPlayer;
        this.board = new cell[10][10];
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; j < this.board.length; j++){
                board[i][j] = new cell();
            }
        }
    }

    public void printBoard(){
        System.out.print("     A    B    C    D    E    F    G    H    I    J");
        System.out.println();
        for(int i = 0; i < this.board.length; i++){
            if(i!=9)
                System.out.print(i+1 + "  ");
            else
            System.out.print(i+1 + " ");
            for(int j = 0; j < this.board[i].length; j++){
                if(!isPlayer && this.board[i][j].toString().equals("[ S ]")){
                    System.out.print("[   ]");
                } else{
                    System.out.print(this.board[i][j]);
                }
            }
            System.out.println();
        }
    }

    public boolean isPlayer(){
        return isPlayer;
    }

    public boolean placeShip(int col, int row, String direction, int length){
        if(canPlaceShip(col, row, direction, length)){
            board[row][col].placeShip();
            if(direction.equals("up")){
                for(int i = 1; i < length; i++){
                    board[row - i][col].placeShip();    
                }
            } else if(direction.equals("down")){
                for(int i = 1; i < length; i++){
                    board[row + i][col].placeShip();    
                }
            }else if(direction.equals("left")){
                for(int i = 1; i < length; i++){
                    board[row][col - i].placeShip();    
                }
            } else{
                for(int i = 1; i < length; i++){
                    board[row][col + i].placeShip();    
                }
            }
            return true;
        }
        return false;
    }

    public boolean isOccupied(int row, int col) {
        return board[row][col].isShip();  // Assuming Cell has a method to check if it has a ship
    }

    public boolean canPlaceShip(int col, int row, String direction, int length) {
        // Check bounds and make sure no other ship is in the way
        if (direction.equals("up")) {
            if (row - length + 1 < 0) 
                return false;
            for (int i = 0; i < length; i++) {
                if (isOccupied(row - i, col))  
                    return false;
            }
        } else if (direction.equals("down")) {
            if (row + length >= 10) 
                return false;
            for (int i = 0; i < length; i++) {
                if (isOccupied(row + i, col)) 
                return false;
            }
        } else if (direction.equals("left")) {
            if (col - length + 1 < 0) 
                return false;
            for (int i = 0; i < length; i++) {
                if (isOccupied(row, col - i)) 
                return false;
            }
        } else if (direction.equals("right")) {
            if (col + length >= 10) 
                return false;
            for (int i = 0; i < length; i++) {
                if (isOccupied(row, col + i)) 
                return false;
            }
        }
        return true;
    }

}

