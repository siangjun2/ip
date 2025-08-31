package Command;

import Exception.DukeException;
import Storage.Storage;
import Ui.Ui;

public class Invalid implements Command {
    @Override
    public void execute(Storage storage) throws DukeException {
        Ui.display("Invalid command. Please retype your command.");
    }
}
