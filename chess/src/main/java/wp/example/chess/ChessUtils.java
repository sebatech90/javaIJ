package wp.example.chess;

public class ChessUtils {
    public String strExtend(String str, String signImitation, boolean drawOnlyBottom) {

        int strLenght = str.length();
        int limit = 10;
        if (drawOnlyBottom) {limit = 13;}

        for (int i = 0; i <=  limit - strLenght; i++) {
            str = str + signImitation;
        }
        return str;
    }

    public int[][] reverseFirstArrayDimension(int[][] array) {
        int temp[];
        int newArray[][] = new int [array.length][];
        System.out.println("start reverse");

        for (int left = 0, right = array.length-1; left <= array.length/2; left++, right--) {
            temp = array[left];
            newArray[left] = array[right];
            newArray[right] = temp;
        }
        return newArray;
    }

    public int[][] reverseSecondArrayDimension(int[][] array) {

        int newArr[][] = new int[array.length][];
        int left;
        int right;
        int temp;
        int i = 0;

        for (int row[] : array) {
            for (left = 0, right = row.length-1; left <= row.length/2; left++, right--) {
                temp = row[left];
                newArr[i][left] = row[right];
                newArr[i][right] = temp;
            }
            i++;
        }
        return newArr;
    }

    public String[][] reverseArray(String[][] array) {

        int len_row = array.length;

        String newArr[][] = new String[len_row][];

        int j = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            for (int k = 0; k < array[i].length; k++) {
                newArr[j][k] = array[i][k];
                j++;
            }
        }
        return newArr;
    }

    public static void printBuiltMsg(String msgType, String strArgs[]) {
        if (msgType == "tryMove") {
            System.out.println(strArgs[0] + "`S MOVE \n");
        } else if (msgType == "turnMove") {
            System.out.println("TRY TURN FROM " + strArgs[0] + " TO " + strArgs[1]
            + "\n" + "THERE`S " + strArgs[2] + " MOVE NUMBER");
        }
    }

    public static void printMsg(String stringOut) {
        System.out.println(stringOut);
    }
}

//        String outputStr = "";
//        int cntGapCharOnLeftSide = (12 - str.length()) / 2;
//        int cntGapCharOnRightSide = (12 - str.length() - cntGapCharOnLeftSide);
//
//        for (int i = 0; i < cntGapCharOnLeftSide; i++) {
//            outputStr = outputStr + " ";
//        }
//
//        outputStr = outputStr + str;
//
//        for (int i = 0; i < cntGapCharOnRightSide; i++) {
//            outputStr = outputStr + " ";
//        }
//        return "|" + outputStr + "|"

