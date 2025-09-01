package Command;

import Exception.DukeException;
import Storage.Storage;
import Ui.Ui;

public class Exit implements Command {
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
