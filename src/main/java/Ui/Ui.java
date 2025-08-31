package Ui;

import java.util.Random;

public class Ui {
    private static final Random r = new Random();
    private static final String[] faces = {"^_^", "O_O", "Y_Y", "U_U", "o_o", "C_C", "P_P"};
    private static final String[] hands = {"/", "_/", "_|", "d", "P", "c", "_P", "o/"};
    private static final String border = "---------------------------------------------------------";

    public static void display(String... messages) {
        Ui.printLine(Ui.border);
        Ui.printLine(Ui.getEmotion());
        for (String s : messages) {
            Ui.printLine(s);
        }
        Ui.printLine(Ui.border);
    }

    public static void displayError(Exception e) {
        Ui.printLine(Ui.border);
        Ui.printLine("(X_X)__/\t!!An error occurred!!");
        Ui.printLine(e.toString());
        Ui.printLine(Ui.border);
    }

    private static String getEmotion() {
        return "DukeGPT || (" + Ui.faces[r.nextInt(Ui.faces.length)] + ")" + Ui.hands[r.nextInt(Ui.hands.length)];
    }

    private static void printLine(String message) {
        System.out.println("\t" + message);
    }
}
