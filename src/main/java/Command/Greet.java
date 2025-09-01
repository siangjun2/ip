package Command;

import Storage.Storage;
import Ui.Ui;
import Exception.DukeException;

/**
 * Represents a Greet command
 */
public class Greet implements Command {
    /**
     * Execute the command, by displaying message
     * @param storage
     * @throws DukeException
     */
    @Override
    public void execute(Storage storage) throws DukeException {
        Ui.display("Hi! I'm Main.DukeGPT! Nice to meet you!");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Greet;
    }
}
