package Main;

import Exception.DukeException;
import Storage.Storage;
import Command.Greet;
import Command.Command;
import Command.Exit;
import Ui.Ui;
import Parser.Parser;

import java.util.Scanner;

public class DukeGPT {
    private final Storage storage;

    public DukeGPT() {
        this.storage = new Storage();
    }

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

    public static void main(String[] args) {
        DukeGPT bot = new DukeGPT();
        bot.loop();
    }
}
