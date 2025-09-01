package Command;

import Exception.DukeException;
import Storage.Storage;
import Ui.Ui;

/**
 * Represents an Invalid command, for when invalid format is used
 */
public class Invalid implements Command {
    /**
     * Executes the command, by reprompting for new command
     * @param storage
     * @throws DukeException
     */
    @Override
    public void execute(Storage storage) throws DukeException {
        Ui.display("Invalid command. Please retype your command.");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Invalid;
    }
}
