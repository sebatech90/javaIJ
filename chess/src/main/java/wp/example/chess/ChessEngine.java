package wp.example.chess;
import java.util.Scanner;

public class ChessEngine {
    private ChessDashboard gameBoard;
    private static int MOVES_LIMIT = 1000;
    private int movesCounter;
    private int turnToken;
    private boolean IN_GAME = true;
    private String[] Players;

    private ChessEngine() {
        initDashboard();
        initGame();
    }

    private void initDashboard() {
        this.gameBoard = new ChessDashboard();
    }

    private void initGame() {
        this.movesCounter = 0;
        this.turnToken = 0;
        this.Players = ChessDashboard.getPlayerType();
    }

    public void startGame (ChessDashboard gameBoard) {
        ChessUtils.printMsg("\n\n" + "CHESS GAME STARTED! \n" + "ENTER MOVE - FOR EXAMPLE A1 A3 \n");
        gameBoard.showDashboardFields();
        Scanner moveCmd = new Scanner(System.in);
        String msgArr[] = new String[10];

        while ( IN_GAME && movesCounter < MOVES_LIMIT ) {
            try {
                msgArr[0] = Players[turnToken];
                ChessUtils.printBuiltMsg("tryMove", msgArr);

                String enteredCoordinates[] = convertCoordinates(moveCmd.nextLine());
                movesCounter++;
                msgArr[0] = enteredCoordinates[0];
                msgArr[1] = enteredCoordinates[1];
                msgArr[2] = String.valueOf(movesCounter);
                ChessUtils.printBuiltMsg("turnMove", msgArr);

                gameBoard.movePiece(enteredCoordinates, Players[turnToken]);
                gameBoard.showDashboardFields();
                checkWin();
                changeToken();
            }
            catch (ArithmeticException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("INVALID DATA. REPEAT THE MOVING");
                //e.printStackTrace();
            }
            catch (Exception e) {
                System.out.println("SOMETHING WENT WRONG WHILE MOVING. REPEAT THE MOVING.");
                //e.printStackTrace();
            }
        }
    }

    String [] convertCoordinates(String enteredMove) {
        return enteredMove.split(" ");
    }

    public void checkWin() {
        if (gameBoard.findKings() != 2) {
            IN_GAME = false;
        }
    }

    public void changeToken() {
        this.turnToken = 1 - this.turnToken;
    }

    public static void main(String[] args) {
        ChessEngine gameSession = new ChessEngine();
        gameSession.startGame(gameSession.gameBoard);
    }
}
// Add test comment