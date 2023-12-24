import java.util.Scanner;
import java.util.Random;

public class MineSweeper {

    // Number of rows and columns in the game grid
    public int rowNumber;
    public int colNumber;

    // Total size of the game grid
    public int size;

    // Arrays to represent the game map and board
    public String[][] map;
    public String[][] board;

    // Scanner to get user input
    public Scanner inp = new Scanner(System.in);

    // Random object for generating random numbers
    public Random rand = new Random();

    // Constructor to initialize the game
    public MineSweeper() {
        // Get the dimensions of the game grid from the user
        getMatrixSizeFromUser();

        // Create arrays for the game map and board
        this.map = new String[this.rowNumber][this.colNumber];
        this.board = new String[this.rowNumber][this.colNumber];

        // Calculate the total size of the game grid
        this.size = this.rowNumber * this.colNumber;

        // Prepare the game and print the initial game board
        prepareGame();
        printArray(board);
        System.out.println(); // We have used println to see both arrays separately
    }

    // Method to get the dimensions of the game grid from the user
    public void getMatrixSizeFromUser() {
        System.out.println("\nWhich dimensions do you want to play Minesweeper?");
        while (!isValidMatrixSize()) {
            System.out.println("\nPlease enter the row number: ");
            this.rowNumber = inp.nextInt();

            System.out.println("\nPlease enter the column number: ");
            this.colNumber = inp.nextInt();
        }
    }

    // Method to check if the entered matrix size is valid
    public boolean isValidMatrixSize() {
        if (this.rowNumber >= 2 && this.colNumber >= 2) {
            System.out.println("\nWelcome to Minesweeping Game!");
            return true;
        } else {
            System.out.println("\nPlease enter at least 2x2 matrix size!");
            return false;
        }
    }

    // Method to prepare the game by initializing the map and placing mines
    public void prepareGame() {
        initializeMap();
        placeMines();
    }

    // Method to initialize the game map and board with default values
    public void initializeMap() {
        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.colNumber; j++) {
                map[i][j] = " -";
                board[i][j] = " -";
            }
        }
    }

    // Method to randomly place mines on the game map
    public void placeMines() {
        int mineCount = this.size / 4;
        int placeMineCount = 0;

        while (placeMineCount < mineCount) {
            int randRow = rand.nextInt(this.rowNumber);
            int randCol = rand.nextInt(this.colNumber);

            if (!map[randRow][randCol].equals(" *")) {
                map[randRow][randCol] = " *";
                placeMineCount++;
            }
        }
    }

    // Method to print the content of a 2D array
    public void printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to run the game
    public void run() {
        System.out.println("\n---> Game Started <---\n");

        int revealedCellCount = 0;

        while (true) {

            printArray(board);

            System.out.print("\nEnter Row: ");
            int row = inp.nextInt();
            System.out.print("Enter Column: ");
            int col = inp.nextInt();


            // Check if the entered row and column values are valid
            if (isValidCoordinate(row, col)) {
                if (board[row][col].equals(" -")) {
                    if (!map[row][col].equals(" *")) {
                        int mineCount = countMines(row, col);
                        board[row][col] = " " + mineCount + "";
                        revealedCellCount++;
                    } else {
                        System.out.println("\nYou Hit a Mine! Game Over!");
                        printArray(map);
                        break;
                    }

                    // Check if the player has revealed all safe cells and won the game
                    if (revealedCellCount == (rowNumber * colNumber - size / 4)) {
                        System.out.println("\nCongratulations! You Revealed All Safe Cells!");
                        printArray(board);
                        break;
                    }
                } else {
                    System.out.println("This cell has already been revealed. Please choose a new one.");
                }
            } else {
                System.out.println("Invalid row or column. Please enter valid values.");
            }
        }
    }

    // Method to count the number of mines around a given cell
    public int countMines(int row, int col) {
        int mineCount = 0;

        // Iterate over the neighboring cells
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                // Check if the neighboring cell is a valid coordinate
                if (isValidCoordinate(newRow, newCol)) {
                    // Check if the neighboring cell contains a mine
                    if (map[newRow][newCol].equals(" *")) {
                        mineCount++;
                    }
                }
            }
        }

        return mineCount;
    }

    // Method to check if a coordinate is valid within the game grid
    public boolean isValidCoordinate(int row, int col) {
        return (row >= 0 && row < rowNumber) && (col >= 0 && col < colNumber);
    }
}
