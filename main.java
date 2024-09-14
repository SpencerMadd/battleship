class battleship{
    public static void main(String[] args){

        //Declares and initializes gameboard
        String[][] gameboard = new String[10][10];

        //Populates gameboard
        for(int i = 0; i < gameboard.length; i++){
            for(int j = 0; j < gameboard[i].length; j++){
                gameboard[i][j] = "[ * ]";
            }
        }


        printBoard(gameboard);




    }


    public static void printBoard(String[][] gameboard){
        System.out.print("     A    B    C    D    E    F    G    H    I    J");
        System.out.println();
        for(int i = 0; i < gameboard.length; i++){
            if(i!=9)
                System.out.print(i+1 + "  ");
            else
            System.out.print(i+1 + " ");
            for(int j = 0; j < gameboard[i].length; j++){
                System.out.print(gameboard[i][j]);
            }
            System.out.println();
        }
    }
}