package wp.example.chess;
import java.util.HashMap;

public class ChessMovementRules {

    static final HashMap<String, String> PIECE_MOVEMENT_VECTOR = new HashMap<String, String>();
    /* substrings define allowed finished moves:
    0 - through diagonal,
    1 - change row,
    2 - change col,
    3 - reverse move

    available shapes of move trace:
    5 - diagonal,
    6 - line,
    7 - trace like "L" shape

    9 - max stride of move size
    */

    static {
        PIECE_MOVEMENT_VECTOR.put("KING", "1111-110-1");
        PIECE_MOVEMENT_VECTOR.put("ROOK", "0111-010-8");
        PIECE_MOVEMENT_VECTOR.put("BISHOP","1001-100-8");
        PIECE_MOVEMENT_VECTOR.put("QUEEN","1111-110-8");
        PIECE_MOVEMENT_VECTOR.put("KNIGHT", "0111-001-3");
        PIECE_MOVEMENT_VECTOR.put("PAWN", "0101-010-2");
    }

    static int getIndexCoordinate(String coordinateName) {
        HashMap<String, Integer> MOVE_COMMAND_TRANSLATIONS = new HashMap<String, Integer>();

        MOVE_COMMAND_TRANSLATIONS.put("from", 0);
        MOVE_COMMAND_TRANSLATIONS.put("to", 1);
        MOVE_COMMAND_TRANSLATIONS.put("fromRow", 0);
        MOVE_COMMAND_TRANSLATIONS.put("fromCol", 1);
        MOVE_COMMAND_TRANSLATIONS.put("toRow", 2);
        MOVE_COMMAND_TRANSLATIONS.put("toCol", 3);

        return MOVE_COMMAND_TRANSLATIONS.get(coordinateName);
    }

    static boolean checkMovmentRules(int[] coordinates, String piece_type) {

        int row = coordinates[ChessMovementRules.getIndexCoordinate("fromRow")];
        int col = coordinates[ChessMovementRules.getIndexCoordinate("fromCol")];
        int row_new = coordinates[ChessMovementRules.getIndexCoordinate("toRow")];
        int col_new = coordinates[ChessMovementRules.getIndexCoordinate("toCol")];

        String currMove = (ChessMovementRules.getMovementChangeVector(row, col, row_new, col_new));
        String currTrace = (ChessMovementRules.getMovementTrace(row, col, row_new, col_new));

        if (
            ChessMovementRules.isCorrectMovementVector(currMove, piece_type) &&
            ChessMovementRules.isCorrectMovementTrace(currTrace, piece_type) &&
            ChessMovementRules.isCorrectMovementStride(row, col, row_new, col_new, piece_type)
        ) {
            return true;
        } else {
            System.out.println("RULE 5: IT IS NOT POSSIBLE TO MOVE PIECE IN THIS PLACE!");

            //System.out.println("isMovementCollision " + getFieldIdWithColision(row, col, row_new, col_new, currMove));
            System.out.println("isCorrectMovementVector " + ChessMovementRules.isCorrectMovementVector(currMove, piece_type));
            System.out.println("isCorrectMovementTrace " + ChessMovementRules.isCorrectMovementTrace(currTrace, piece_type));
            System.out.println("isCorrectMovementStride " + ChessMovementRules.isCorrectMovementStride(row, col, row_new, col_new, piece_type));
            return false;
        }
    }

    static boolean isCorrectMovementVector(String movementVector, String pieceType) {
        String availableDirections = PIECE_MOVEMENT_VECTOR.get(pieceType).substring(0,3);

        for (int i = 0; i < availableDirections.length(); i++) {
            if (availableDirections.substring(i, i + 1).equals(movementVector.substring(i, i + 1))
                    && availableDirections.substring(i, i + 1).equals("1")) {
                return true;
            }
        }
        return false;
    }

    static boolean isCorrectMovementTrace (String movementVector, String pieceType) {
        String availableDirections = PIECE_MOVEMENT_VECTOR.get(pieceType).substring(5,8);

        for (int i = 0; i < availableDirections.length(); i++) {
            if (availableDirections.substring(i, i + 1).equals(movementVector.substring(i, i + 1))
                    && availableDirections.substring(i, i + 1).equals("1")) {
                return true;
            }
        }
        return false;
    }

    static boolean isCorrectMovementStride (int row, int col, int row_new, int col_new, String pieceType) {
        String maxStride = PIECE_MOVEMENT_VECTOR.get(pieceType).substring(9);
        int maxStrideInt = Integer.parseInt(maxStride);
        int triedStride;

        if (Math.abs(row_new - row) > Math.abs(col_new - col)) {
            triedStride = Math.abs(row_new - row);
        } else {
            triedStride = Math.abs(col_new - col);
        }

        if (pieceType.equals("KNIGHT")) {
            return true;
        } else {
            if (triedStride <= maxStrideInt) {
                return true;
            } else {
                return false;
            }
        }
    }

    static String getMovementChangeVector (int row, int col, int row_new, int col_new) {
        StringBuilder mv = new StringBuilder();

        int diffRow = row_new - row;
        int diffCol = col_new - col;

        if (row != row_new && col != col_new && Math.abs(diffRow) == Math.abs(diffCol)) {
            mv.append("100");
        } else if (row == row_new) {
            mv.append("001");
        } else if (col == col_new) {
            mv.append("010");
        } else {
            //Knight`s vector
            mv.append("011");
        }

        if (diffRow < 0 || diffCol < 0) {mv.append("1");}

        String moveVector = mv.toString();
        return moveVector;
    }

    static String getMovementTrace (int row, int col, int row_new, int col_new) {
        StringBuilder mt = new StringBuilder();

        if (row != row_new && col != col_new && Math.abs(row_new - row) == Math.abs(col_new - col)) {
            mt.append("100");
        } else if ( (Math.abs(row_new - row) == 2 && (Math.abs(col_new - col) == 1)) ||  (Math.abs(row_new - row) == 1 && (Math.abs(col_new - col) == 2)) ) {
            mt.append("001");
        } else if (row == row_new || col == col_new) {
            mt.append("010");
        } else {
            mt.append("000");
        }
        String moveTrace = mt.toString();
        return moveTrace;
    }
}
