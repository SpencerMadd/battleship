import java.util.Scanner;
import java.util.Random;

class battleship{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        board player = new board(true);
        board bot = new board(false);
        populateRandomBoard(bot);
        int ships = 4;
        player.printBoard();
        System.out.println("");
        
        while(ships != 0){
        System.out.println("Place your ship (Ex. A1 down): ");
        System.out.print("You have " + ships + " remaining\n");
        String input = scanner.nextLine();
        int column = letterToColumn(input.substring(0, 1));
        int row = Integer.parseInt(input.substring(1, input.indexOf(" ")));
        String direction = input.substring(input.indexOf(" ") + 1);
        if(player.placeShip(column, row - 1, direction, 3))
            ships--;
        player.printBoard();
        System.out.println("\n");
        }
        while(!checkEnd(player, bot)){
            showBoards(player, bot);
            System.out.print("Make a guess:");
            String guess = scanner.nextLine();
            boolean hit = bot.guessSpot(letterToColumn(guess.substring(0,1)), Integer.parseInt(guess.substring(1)));
            if(hit)
                System.out.println("That's a hit!");
            else
            System.out.println("So close!");
        }


    }

    public static boolean checkEnd(board player, board bot){
        return bot.isCleared() &&  player.isCleared();
    }

    public static int letterToColumn(String letter) {
        // Convert the letter to uppercase in case the user enters a lowercase letter
        letter = letter.toUpperCase();
        // Get the numeric value by subtracting 'A' from the letter's char value
        return letter.charAt(0) - 'A';
    }

    public static void populateRandomBoard(board botBoard) {
        int[] shipLengths = {5, 4, 3, 3, 2};  // Standard ship lengths: Carrier, Battleship, Destroyer, Submarine, Patrol Boat
        String[] directions = {"up", "down", "left", "right"};
        Random random = new Random();

        for (int length : shipLengths) {
            boolean placed = false;

            while (!placed) {
                int row = random.nextInt(10); // Random row (0-9)
                int col = random.nextInt(10); // Random column (0-9)
                String direction = directions[random.nextInt(directions.length)]; // Random direction
                // Check if the ship can be placed
                if (botBoard.canPlaceShip(col, row, direction, length)) {
                    botBoard.placeShip(col, row, direction, length);
                    placed = true;
                }
            }
        }
    }
    public static void showBoards(board player, board bot){
        System.out.println("\n\n");
        System.out.println("Your board:");
        player.printBoard();
        System.out.println("\n\n");
        System.out.println("Computer board:");
        bot.printBoard();
        System.out.println("\n\n");
    }
}