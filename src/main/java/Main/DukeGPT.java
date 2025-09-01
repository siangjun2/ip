package Main;

import Exception.DukeException;
import Storage.Storage;
import Command.Greet;
import Command.Command;
import Command.Exit;
import Ui.Ui;
import Parser.Parser;

import java.util.Scanner;

/**
 * Entry of the program.
 */
public class DukeGPT {
    /**
     * Instance of storage being used
     */
    private final Storage storage;

    /**
     * Initialises instance of chatbot with storage
     */
    public DukeGPT() {
        this.storage = new Storage();
    }

    /**
     * The main repeating loop, where users type command and bot
     * responds accordingly.
     */
    public void loop() {
        Command c = new Greet();
        Scanner sc = new Scanner(System.in);

        while (!(c instanceof Exit)) {
            try {
                c.execute(this.storage);
                c = Parser.parse(sc.nextLine());
            } catch (DukeException e) {
                Ui.displayError(e);
            }
        }
    }

    /**
     * Entry of program
     * @param args  Optional args, not used
     */
    public static void main(String[] args) {
        DukeGPT bot = new DukeGPT();
        bot.loop();
    }
}
