package wp.example.chess;

import org.junit.jupiter.api.Test;

public class ChessPieceTest {
    @Test
    public void testGetName() {
        ChessPiece piece = new ChessPiece("KING", "King", "Black");
        piece.getName();
    }
}