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
                System.out.print(this.board[i][j]);
            }
            System.out.println();
        }
    }






}

