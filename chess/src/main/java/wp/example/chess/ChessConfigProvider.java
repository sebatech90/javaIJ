package wp.example.chess;

public class ChessConfigProvider {
    private static String INIT_ROWS[][] = {
            {"WHITE", "ROOK", "KNIGHT", "BISHOP", "QUEEN", "KING", "BISHOP", "KNIGHT", "ROOK"},
            {"WHITE", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN"},
            {},
            {},
            {},
            {},
            {"BLACK", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN"},
            {"BLACK", "ROOK", "KNIGHT", "BISHOP", "QUEEN", "KING", "BISHOP", "KNIGHT", "ROOK"},
    };

    public static String[] getPiecesInRow(int row_number) {
        String chosen_row[] = new String[8];

        for (int i = 1; i < INIT_ROWS[row_number].length; i++) {
            chosen_row[i-1] = INIT_ROWS[row_number][i];
        }
        return chosen_row;
    }

    public static String getRowColor(int row_number) {
        return INIT_ROWS[row_number][0];
    }
}



//        if (INIT_ROWS[row_number].length == 0) {
//            System.out.printf("Empty array" + INIT_ROWS[row_number].length);
//            System.out.println(INIT_ROWS[row_number]);
//            return "EMPTY";
//        }
//        return INIT_ROWS[row_number][0];