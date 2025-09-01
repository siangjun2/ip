package Command;

import Exception.DukeException;
import Storage.Storage;

public interface Command {
    public abstract void execute(Storage storage) throws DukeException;
}
