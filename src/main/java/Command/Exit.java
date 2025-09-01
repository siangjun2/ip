package Command;

import Exception.DukeException;
import Storage.Storage;
import Ui.Ui;

/**
 * Represents an Exit command, that closes the program
 */
public class Exit implements Command {
    /**
     * Execute the command, by displaying message and saving to file
     * @param storage   Storage current being used
     * @throws DukeException    Handles error during execution
     */
    @Override
    public void execute(Storage storage) throws DukeException{
        Ui.display("That was fun! Hope to see you again!");
        storage.readToFile();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Exit;
    }
}
