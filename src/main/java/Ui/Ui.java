package Ui;

import java.util.Random;

public class Ui {
    private static final Random r = new Random();
    private static final String[] faces = {"^_^", "O_O", "Y_Y", "U_U", "o_o", "C_C", "P_P"};
    private static final String[] hands = {"/", "_/", "_|", "d", "P", "c", "C"};

    public static void display(String... messages) {
        Ui.printLine("---------------------------------------------------------");
        Ui.printLine(Ui.getEmotion());
        for (String s : messages) {
            Ui.printLine(s);
        }
        Ui.printLine("---------------------------------------------------------");
    }

    private static String getEmotion() {
        return "(" + Ui.faces[r.nextInt(Ui.faces.length)] + ")" + Ui.hands[r.nextInt(Ui.hands.length)];
    }

    private static void printLine(String message) {
        System.out.println("\t" + message);
    }

    public static void main(String[] args) {
        display("something");
        display(new String[]{"diawoip","HDIAWDWIO"});
    }
}
