package Command;

import Storage.Storage;
import Ui.Ui;
import Exception.DukeException;

public class Greet implements Command {
    @Override
    public void execute(Storage storage) throws DukeException {
        Ui.display("Hi! I'm Main.DukeGPT! Nice to meet you!");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Greet;
    }
}
