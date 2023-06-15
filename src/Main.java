import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int ROW_COUNT = 3;
    private static int COL_COUNT = 3;
    private static String CELL_STATE_EMPTY = "";
    private static String CELL_STATE_X = "X";
    private static String CELL_STATE_0 = "0";
    private static String GAME_STATE_X_WIN = "Х победили";
    private static String GAME_STATE_0_WIN = "0 победили";
    private static String GAME_STATE_X_DRAW = "Ничья";
    private static String GAME_STATE_NOT_FISHED = "Игра не окончена";
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        while (true) {
            startGameRound();
        }


    }

    public static void startGameRound() {
        System.out.println("Начало нового раунда: ");
        String[][] board = createBoard();
        startGameLoop(board);

    }

    public static String[][] createBoard() {
        String[][] board = new String[ROW_COUNT][COL_COUNT];
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                board[row][col] = CELL_STATE_EMPTY;

            }

        }
        return board;

    }

    public static void startGameLoop(String[][] board) {
        boolean playerTurn = true;
       do  {
            if (playerTurn) {
                makePlayerTurn(board);
                printBoard(board);
            }
            else {
                makeBotTurn(board);
                printBoard(board);
            }
            playerTurn = !playerTurn;

            System.out.println();

            String gameState = checkGameState(board);
        if (!Objects.equals(gameState, GAME_STATE_NOT_FISHED)) {
            System.out.println(gameState);
            return;
            }
        }while(true);
    }
    public static void makePlayerTurn(String[][] board) {
        int[] coordinates = InputCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_X;
    }

    public static int[] InputCellCoordinates(String[][] board){
        System.out.println("Введите 2 числа (ряд и колонку) от 0 до 2 через пробел: ");
        do {
            String[] input = scanner.nextLine().split(" ");
            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);

            if ((row < 0) || (row >= ROW_COUNT) || (col < 0) || (col >= COL_COUNT)) {
            System.out.println("Некоректное значение! Введите2 числа от (ряд и колонку) 0 до 2 через пробел: ");
            } else if(!Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
            System.out.println("данная ячейка занята: ");
            } else{
                return new int[]{row,col};
            }


        } while (true);

    }
    public static void makeBotTurn(String[][] board){
        System.out.println("Ход бота");
        int[] coordinates = getRandomEmptyCellCrordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_0;

    }
    public static int[] getRandomEmptyCellCrordinates(String[][] board){
        do {
            int row = random.nextInt(ROW_COUNT);
            int col = random.nextInt(COL_COUNT);

            if (Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
                return new int[]{row,col};
            }
        }while (true);

    }
    public static String checkGameState(String[][] board){
        ArrayList<Integer> sums = new ArrayList<>();
        for (int row = 0; row < ROW_COUNT; row++) {
            int rowSum = 0;
            for (int col = 0; col < COL_COUNT; col++) {
                rowSum += calculateNumValue(board[row][col]);

            }
            sums.add(rowSum);
        }
        for (int col = 0; col < COL_COUNT; col++) {
            int colSum = 0;
            for (int row = 0; row < ROW_COUNT; row++) {
                colSum += calculateNumValue(board[row][col]);
            }
            sums.add(colSum);
        }
        // Диагнональ с лево
        int leftDiagonalSum = 0;
        for (int i = 0; i < ROW_COUNT; i++) {
            leftDiagonalSum += calculateNumValue(board[i][i]);
        }
        sums.add(leftDiagonalSum);
        // Диагональ с права
        int rightDiagonalSum = 0;
        for (int i = 0; i < ROW_COUNT; i++) {
            rightDiagonalSum += calculateNumValue(board[i][(ROW_COUNT - 1)-i]);
        }
        sums.add(rightDiagonalSum);
        if (sums.contains(3)) {
            return GAME_STATE_X_WIN;
        } else if (sums.contains(-3)){
            return GAME_STATE_0_WIN;
        }
        else if (areAllCellsTaken(board)){
            return GAME_STATE_X_DRAW;
        }
        else{
            return GAME_STATE_NOT_FISHED;
        }
    }

    private static int calculateNumValue(String cellState) {
        if(Objects.equals(cellState, CELL_STATE_X)){
            return  1;
        }
        else if (Objects.equals(cellState, CELL_STATE_0)){
            return -1;
        }
        else {
            return 0;
        }
    }

    public static boolean areAllCellsTaken(String[][] board){
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                 if (Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void printBoard(String[][] board) {
        System.out.println("-------");
        for (int row = 0; row < ROW_COUNT; row++) {
            String line = "| ";
            for (int col = 0; col < COL_COUNT; col++) {
                line += board[row][col]+ " ";
            }
            line += "| ";
            System.out.println(line);
        }
        System.out.println("-------");
    }
}