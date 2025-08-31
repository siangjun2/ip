package Command;

import Exception.DukeException;
import Storage.Storage;

public interface Command {
    public void execute(Storage storage) throws DukeException;
}
