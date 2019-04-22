package wp.example.chess;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChessPieceTest {
    @Test
    public void testGetName() {
        ChessPiece piece = new ChessPiece("KING", "King", "Black");
        piece.getName();
    }
}