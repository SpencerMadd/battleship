import java.util.Scanner;
import java.util.Random;

class BattleshipGame {
    private static final int[] SHIP_LENGTHS = {5, 4, 3, 3, 2};
    private static final String[] DIRECTION_OPTIONS = {"up", "down", "left", "right"};
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== BATTLESHIP ===");
        System.out.println("Welcome to Naval Combat!\n");
        
        Board playerBoard = new Board(true);
        Board computerBoard = new Board(false);
        
        // Setup phase
        setupPlayerBoard(playerBoard);
        populateRandomBoard(computerBoard);
        
        // Game phase
        playGame(playerBoard, computerBoard);
        
        scanner.close();
    }
    
    private static void setupPlayerBoard(Board playerBoard) {
        System.out.println("TIME TO DEPLOY YOUR FLEET!");
        playerBoard.printBoard();
        
        int shipsPlaced = 0;
        while (shipsPlaced < SHIP_LENGTHS.length) {
            int remaining = SHIP_LENGTHS.length - shipsPlaced;
            System.out.printf("\nPlace your %d-length ship (%d ships remaining)%n", 
                            SHIP_LENGTHS[shipsPlaced], remaining);
            
            try {
                System.out.print("Enter coordinates and direction (e.g., A1 down): ");
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("Please enter valid input.");
                    continue;
                }
                
                // Parse input
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    System.out.println("Please include direction (up, down, left, right)");
                    continue;
                }
                
                int col = letterToColumn(parts[0].substring(0, 1));
                int row = Integer.parseInt(parts[0].substring(1)) - 1;
                String direction = parts[1].toLowerCase();
                
                // Validate direction
                if (!isValidDirection(direction)) {
                    System.out.println("Invalid direction. Use: up, down, left, right");
                    continue;
                }
                
                // Attempt to place ship
                if (playerBoard.placeShip(col, row, direction, SHIP_LENGTHS[shipsPlaced])) {
                    shipsPlaced++;
                    playerBoard.printBoard();
                    System.out.println("Ship deployed successfully!");
                } else {
                    System.out.println("Cannot place ship there. Try different coordinates.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinate format. Use format like 'A1 down'");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void playGame(Board playerBoard, Board computerBoard) {
        System.out.println("\n=== BATTLE PHASE ===");
        boolean playerTurn = true;
        Random random = new Random();
        
        while (!isGameOver(playerBoard, computerBoard)) {
            if (playerTurn) {
                playerTurn(computerBoard);
            } else {
                computerTurn(playerBoard, random);
            }
            playerTurn = !playerTurn;
            
            // Show current status
            showGameStatus(playerBoard, computerBoard);
        }
        
        // Announce winner
        if (computerBoard.isCleared()) {
            System.out.println("CONGRATULATIONS! You sunk all enemy ships!");
        } else {
            System.out.println("GAME OVER! The computer sunk all your ships.");
        }
    }
    
    private static void playerTurn(Board computerBoard) {
        boolean validGuess = false;
        
        while (!validGuess) {
            try {
                System.out.print("\nYour turn! Enter target coordinates (e.g., A5): ");
                String guess = scanner.nextLine().trim().toUpperCase();
                
                if (guess.length() < 2) {
                    System.out.println("Please enter valid coordinates (e.g., A5)");
                    continue;
                }
                
                int col = letterToColumn(guess.substring(0, 1));
                int row = Integer.parseInt(guess.substring(1)) - 1;
                
                if (row < 0 || row >= 10 || col < 0 || col >= 10) {
                    System.out.println("Coordinates out of bounds. Use A-J and 1-10.");
                    continue;
                }
                
                if (computerBoard.isAlreadyGuessed(col, row)) {
                    System.out.println("You already targeted this location.");
                    continue;
                }
                
                boolean hit = computerBoard.guessSpot(col, row);
                if (hit) {
                    System.out.println("DIRECT HIT! Enemy ship damaged!");
                    if (computerBoard.isShipSunk(col, row)) {
                        System.out.println("You sunk an enemy ship!");
                    }
                } else {
                    System.out.println("Miss! Water splashes...");
                }
                
                validGuess = true;
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinate format. Use format like 'A5'");
            }
        }
    }
    
    private static void computerTurn(Board playerBoard, Random random) {
        System.out.println("\nComputer's turn...");
        
        // Simple AI - random guessing
        int col, row;
        do {
            col = random.nextInt(10);
            row = random.nextInt(10);
        } while (playerBoard.isAlreadyGuessed(col, row));
        
        // Convert to human-readable format for output
        char colChar = (char) ('A' + col);
        System.out.printf("Computer targets: %c%d%n", colChar, row + 1);
        
        boolean hit = playerBoard.guessSpot(col, row);
        if (hit) {
            System.out.println("The computer hit your ship!");
            if (playerBoard.isShipSunk(col, row)) {
                System.out.println("Oh no! The computer sunk one of your ships!");
            }
        } else {
            System.out.println("The computer missed!");
        }
    }
    
    private static boolean isGameOver(Board playerBoard, Board computerBoard) {
        return computerBoard.isCleared() || playerBoard.isCleared();
    }
    
    private static void showGameStatus(Board playerBoard, Board computerBoard) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("YOUR FLEET:");
        playerBoard.printBoard();
        
        System.out.println("\nENEMY WATERS:");
        computerBoard.printBoard();
        
        System.out.printf("\nStatus: Your ships sunk: %d | Enemy ships sunk: %d%n",
                         playerBoard.getSunkShips(), computerBoard.getSunkShips());
    }
    
    public static int letterToColumn(String letter) {
        letter = letter.toUpperCase();
        return letter.charAt(0) - 'A';
    }
    
    public static boolean isValidDirection(String direction) {
        for (String validDir : DIRECTION_OPTIONS) {
            if (validDir.equals(direction)) {
                return true;
            }
        }
        return false;
    }
    
    public static void populateRandomBoard(Board board) {
        Random random = new Random();
        
        for (int length : SHIP_LENGTHS) {
            boolean placed = false;
            int attempts = 0;
            
            while (!placed && attempts < 100) { // Prevent infinite loop
                int row = random.nextInt(10);
                int col = random.nextInt(10);
                String direction = DIRECTION_OPTIONS[random.nextInt(DIRECTION_OPTIONS.length)];
                
                if (board.canPlaceShip(col, row, direction, length)) {
                    board.placeShip(col, row, direction, length);
                    placed = true;
                }
                attempts++;
            }
            
            if (!placed) {
                System.err.println("Warning: Could not place ship of length " + length);
            }
        }
    }
}