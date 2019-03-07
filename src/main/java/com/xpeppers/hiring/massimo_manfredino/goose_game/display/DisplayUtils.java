package com.xpeppers.hiring.massimo_manfredino.goose_game.display;

public class DisplayUtils {

    public static String getDisplayableIndex(int index) {
        String result = String.valueOf(index);

        switch (index) {
            case 0:
                result = "Start";
                break;
            case 6:
                result = "The Bridge";
                break;
            case 63:
                result = "End";
                break;
            default:
                break;
        }

        return result;
    }
}
