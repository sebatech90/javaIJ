package wp.example.chess;
import java.util.HashMap;

public class ChessDashboard {
    private static String PLAYERS[] = {"WHITE", "BLACK"};

    static final HashMap<String, Integer> PIECE_MOVEMENT_CONVERSION = new HashMap<String, Integer>();

    static {
        PIECE_MOVEMENT_CONVERSION.put("A", 0);
        PIECE_MOVEMENT_CONVERSION.put("B", 1);
        PIECE_MOVEMENT_CONVERSION.put("C", 2);
        PIECE_MOVEMENT_CONVERSION.put("D", 3);
        PIECE_MOVEMENT_CONVERSION.put("E", 4);
        PIECE_MOVEMENT_CONVERSION.put("F", 5);
        PIECE_MOVEMENT_CONVERSION.put("G", 6);
        PIECE_MOVEMENT_CONVERSION.put("H", 7);
    }

    private int dashboardFieldsContent[][] = new int[8][8];
    private ChessPiece pieces[] = new ChessPiece[32];

    public ChessDashboard() {

        int offsetObjectCreation = 1;

        for (int row = 0; row < dashboardFieldsContent.length; row++) {
            String[] pieces_in_row = ChessConfigProvider.getPiecesInRow(row);

            if (pieces_in_row[0] == null) {continue;}

            String piece_color = ChessConfigProvider.getRowColor(row);

            for (int field_number = 0; field_number < dashboardFieldsContent[row].length; field_number++) {

                String pieceName = pieces_in_row[field_number] + "_" + piece_color.substring(0,1);

                ChessPiece createdNewPiece = new ChessPiece(pieces_in_row[field_number], pieceName, piece_color);
                pieces[createdNewPiece.getId() - offsetObjectCreation] = createdNewPiece;
                dashboardFieldsContent[row][field_number] = createdNewPiece.getId();
            }
        }
    }

    public static String[] getPlayerType () {
        return PLAYERS;
    }

    public int getDashboardFieldId(int row, int col) {
        if (row <= dashboardFieldsContent.length && col <= dashboardFieldsContent[row].length) {
            return dashboardFieldsContent[row][col];
        } else {
            return 32;
        }
    }

    public void setDashboardFieldId(int row, int col, int pieceId) {
        this.dashboardFieldsContent[row][col] = pieceId;
    }

    public void clearDashboardField(int row, int col) {
        this.dashboardFieldsContent[row][col] = 0;
    }

    public void movePiece(String[] coordinates, String currPlayer) throws ArrayIndexOutOfBoundsException,
            ArithmeticException, IllegalArgumentException  {

        String[] humanCoordinates = splitHumanCoordinates(coordinates);

        if (validateEnteredData(humanCoordinates)) {
            int[] finalCoordinates = produceFinalCoordinates(humanCoordinates);
            moveDashboardField(finalCoordinates, currPlayer);
        } else {
            System.out.println("Not validate");
            throw new IllegalArgumentException("Entered data out of range");
        }
    }

    private String[] splitHumanCoordinates(String coordinates[]) {

        String fromField = coordinates[ChessMovementRules.getIndexCoordinate("from")];
        String toField = coordinates[ChessMovementRules.getIndexCoordinate("to")];

        String[] humanCoordinates = new String[4];

        humanCoordinates[ChessMovementRules.getIndexCoordinate("fromRow")] = fromField.substring(1,2);
        humanCoordinates[ChessMovementRules.getIndexCoordinate("fromCol")] = fromField.substring(0,1);
        humanCoordinates[ChessMovementRules.getIndexCoordinate("toRow")] = toField.substring(1,2);
        humanCoordinates[ChessMovementRules.getIndexCoordinate("toCol")] = toField.substring(0,1);

        return humanCoordinates;
    }

    private boolean validateEnteredData(String coordinates[]) {
        for (String coord : coordinates) {
            if (coord == null || coord.isEmpty()) {
                return false;
            }
        }

        if (
            coordinates[ChessMovementRules.getIndexCoordinate("fromCol")].matches("[a-hA-h]{1}")
            && coordinates[ChessMovementRules.getIndexCoordinate("toCol")].matches("[a-hA-h]{1}")
            && coordinates[ChessMovementRules.getIndexCoordinate("fromRow")].matches("[1-8]")
            && coordinates[ChessMovementRules.getIndexCoordinate("toRow")].matches("[1-8]")
        ) { return true; }
        return false;
    }

    private int[] produceFinalCoordinates(String[] coordinates) {

        int[] finalCoordinates = new int[4];
        finalCoordinates[ChessMovementRules.getIndexCoordinate("fromRow")] = Integer.parseInt(coordinates[ChessMovementRules.getIndexCoordinate("fromRow")]) - 1;
        finalCoordinates[ChessMovementRules.getIndexCoordinate("fromCol")] = PIECE_MOVEMENT_CONVERSION.get(coordinates[ChessMovementRules.getIndexCoordinate("fromCol")]);
        finalCoordinates[ChessMovementRules.getIndexCoordinate("toRow")] = Integer.parseInt(coordinates[ChessMovementRules.getIndexCoordinate("toRow")]) - 1;
        finalCoordinates[ChessMovementRules.getIndexCoordinate("toCol")] = PIECE_MOVEMENT_CONVERSION.get(coordinates[ChessMovementRules.getIndexCoordinate("toCol")]);

        return finalCoordinates;
    }

    private void moveDashboardField(int[] coordinates, String currPlayer) throws IllegalArgumentException {

        int row = coordinates[ChessMovementRules.getIndexCoordinate("fromRow")];
        int col = coordinates[ChessMovementRules.getIndexCoordinate("fromCol")];
        int row_new = coordinates[ChessMovementRules.getIndexCoordinate("toRow")];
        int col_new = coordinates[ChessMovementRules.getIndexCoordinate("toCol")];

        int fromFieldId = getDashboardFieldId(row, col);
        String currMove = (ChessMovementRules.getMovementChangeVector(row, col, row_new, col_new));

        //Check dashboard rules and check pieces rules
        if (checkDashboardRules(coordinates, currPlayer) && ChessMovementRules.checkMovmentRules(coordinates, pieces[fromFieldId].getType())
            && getFieldIdWithColision(row, col, row_new, col_new, currMove) == 0
        ) {
            setDashboardFieldId(row_new, col_new, fromFieldId);
            clearDashboardField(row, col);
        } else {
            throw new IllegalArgumentException("INVALID MOVE");
        }
    }

    private boolean checkDashboardRules (int[] coordinates, String currPlayer) {

        int row = coordinates[ChessMovementRules.getIndexCoordinate("fromRow")];
        int col = coordinates[ChessMovementRules.getIndexCoordinate("fromCol")];
        int row_new = coordinates[ChessMovementRules.getIndexCoordinate("toRow")];
        int col_new = coordinates[ChessMovementRules.getIndexCoordinate("toCol")];

        int fromFieldId = getDashboardFieldId(row, col);
        int toFieldId = getDashboardFieldId(row_new, col_new);

        boolean status = true;

        //System.out.println("Check " + pieces[fromFieldId].getColor() + " " + pieces[toFieldId].getColor());

        if (row == row_new && col == col_new) {
            System.out.println("RULE 0: IT IS NOT POSSIBLE TO MOVE PIECE INTO THE SAME FIELD");
            status = false;
        } else if (fromFieldId == 0) {
            System.out.println("RULE 1: THERE IS NO PIECE ON THE INDICATED FIELD!");
            status = false;
        } else if (fromFieldId == 32 || toFieldId == 32) {
            System.out.println("RULE 2: ONE OF THE INDICATED FIELD NOT EXISTS ON DASHBOARD!");
            status = false;
        } else if (toFieldId != 0 && pieces[fromFieldId].getColor() == pieces[toFieldId].getColor()) {
            System.out.println("RULE 3: IT IS NOT POSSIBLE TO MOVE PIECE INTO FIELD WITH THE SAME COLOUR!");
            status = false;
        } else if (pieces[fromFieldId].getColor() != currPlayer) {
            System.out.println("RULE 4: WAIT FOR " + currPlayer + " MOVE!");
            status = false;
        }
        return status;
    }

    int getFieldIdWithColision (int row, int col, int row_new, int col_new, String moveType) {

        String moveTypeDirection = moveType.substring(0,3);
        int incrementStepValue = 1;
        int targetField;

        if (moveTypeDirection.equals("100") || moveTypeDirection.equals("010")) {
            targetField = row_new;
        } else {
            targetField = col_new;
        }

        for (int i  = row + 1, j = col + 1; i < targetField; i = i + incrementStepValue, j = j + incrementStepValue) {

            switch (moveTypeDirection) {
                case "100":
                    if(getDashboardFieldId(i,j) != 0) {return getDashboardFieldId(i,j);}
                case "001":
                    if(getDashboardFieldId(row,j) != 0) {return getDashboardFieldId(row,j);}
                case "010":
                    if(getDashboardFieldId(i,col) != 0) {
                        return getDashboardFieldId(i,col);
                    }
                case "011":
            }
        }

        return 0;
    }

    int findKings() {
        int cntKings = 0;

        for (ChessPiece objPiece : pieces) {
            //System.out.println("objPiece " + objPiece);
            if ( objPiece.getType().equals("KING") ) {
                cntKings++;
            }
        }
        return cntKings;
    }

    public void showDashboardFieldsId() {
        for (int row[] : dashboardFieldsContent) {
            for (int col : row) {
                System.out.print(col + "\t");
            }
            System.out.println();
        }
    }

    public void showDashboardFields() {
        ChessUtils customizeElement = new ChessUtils();

        int userDashboardView[][] = customizeElement.reverseFirstArrayDimension(dashboardFieldsContent);
        int startSign = 0;
        int rowNum = 0;
        String signsFillingFields[] = {":", " "};

        for (int row[] : userDashboardView) {
            StringBuilder descPieceField = new StringBuilder();
            StringBuilder restField = new StringBuilder();

            for (int pieceIdOnField : row) {
                if (pieceIdOnField != 0) {
                    ChessPiece objectOnField = pieces[pieceIdOnField - 1];
                    descPieceField.append(customizeElement.strExtend(objectOnField.getName(), signsFillingFields[startSign],false));
                } else {
                    descPieceField.append(customizeElement.strExtend("", signsFillingFields[startSign],false));
                }
                restField.append(customizeElement.strExtend("", signsFillingFields[startSign],false));
                startSign = 1 - startSign;
            }

            int dashboardRowLabel = userDashboardView.length - rowNum;
            String tabSign = "  ";

            System.out.println(tabSign + descPieceField);
            System.out.println(tabSign + restField);
            System.out.println(dashboardRowLabel + " " + restField.toString());
            System.out.println(tabSign + restField + "\n" + tabSign + restField);
            startSign = 1 - startSign;
            rowNum++;
        }

        StringBuilder lastRow = new StringBuilder();
        for (String key : PIECE_MOVEMENT_CONVERSION.keySet()) {
            lastRow.append("       " + key + "   ");
        }
        System.out.println(lastRow);
    }
}
