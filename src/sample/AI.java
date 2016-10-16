package sample;

import java.util.ArrayList;

/**
 * Created by user on 10.09.2016.
 */
public class AI {
    private static final String LINE1_WIN_PATTERN = "^1{3}.+?$";
    private static final String LINE2_WIN_PATTERN = "^.{3}1{3}.+$";
    private static final String LINE3_WIN_PATTERN = "^.+1{3}$";

    private static final String DIAG1_WIN_PATTERN = "1.{3}1.{3}1";
    private static final String DIAG2_WIN_PATTERN = ".{2}1.{1}1.{1}1.{2}";

    public static boolean isGameOver(String gameSeq) {
        if (gameSeq.contains("111")
                || gameSeq.matches(LINE1_WIN_PATTERN)
                || gameSeq.matches(LINE2_WIN_PATTERN)
                || gameSeq.matches(LINE3_WIN_PATTERN)
                || gameSeq.matches(DIAG1_WIN_PATTERN)
                || gameSeq.matches(DIAG2_WIN_PATTERN)) {
            return true;
        }
        return false;
    }

    public static ArrayList<String> findAvailableMoves(String gameSeq) {
        ArrayList<String> result = new ArrayList<String>();

        if (gameSeq.contains("0")) {
            for(int i = gameSeq.indexOf("0"); i != -1; i = gameSeq.indexOf("0", ++i)) {
//                System.out.println("i = " + i);
                result.add(String.valueOf(i));
            }
            System.out.println("result " + result);
        }

        return result;
    }

    public static boolean isAvailableMove(int move, String gameSeq, String selfSeq) {
        System.out.println("move " + move);
        return (gameSeq.charAt(move) == '0' && selfSeq.charAt(move) == '0');
    }

    //todo: actually it is second move
    public static boolean isFirstMove(String gameSeq){
        return (gameSeq.contains("1") && (gameSeq.indexOf('1') == gameSeq.lastIndexOf('1')));
    }

    public static int findBestMove(String gameSeq) {
        if (isFirstMove(gameSeq)) {
            if (gameSeq.indexOf('1') == 4) {
                return 0; //todo add randomization - not only 1 must be returned but either 3 or 7 or 9
            } else {
                return 3; //there is 2 ways of solving this situation - 41 | 43 | 45 | 48 or side cell
            }
        }

        if (gameSeq.contains("11")) {
            System.out.println("char at 11 = " + gameSeq.indexOf("11"));
            return gameSeq.indexOf("11") + 1;
        }

        return 0;
    }

    public static int getCornerMove(String gameSeq, String selfSeq) {
        //todo add randomization

        if (isAvailableMove(0, gameSeq, selfSeq)) {
            return 0;
        } else if (isAvailableMove(2, gameSeq, selfSeq)) {
            return 2;
        } else if (isAvailableMove(6, gameSeq, selfSeq)) {
            return 6;
        } else if (isAvailableMove(8, gameSeq, selfSeq)) {
            return 8;
        } else {
            return -1;
        }
    }

    public static int getSideMove(String gameSeq, String selfSeq) {
        //todo add randomization

        if (isAvailableMove(1, gameSeq, selfSeq)) {
            return 1;
        } else if (isAvailableMove(3, gameSeq, selfSeq)) {
            return 3;
        } else if (isAvailableMove(5, gameSeq, selfSeq)) {
            return 5;
        } else if (isAvailableMove(7, gameSeq, selfSeq)) {
            return 7;
        } else {
            return -1;
        }
    }

    public static int getCenterMove(String gameSeq, String selfSeq) {
        if (isAvailableMove(4, gameSeq, selfSeq)) {
            return 4;
        } else {
            return -1;
        }
    }

    public static int getNearMove(int previousMove, String gameSeq, String selfSeq) {
        return -1;
    }
}