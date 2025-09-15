package command;

import exception.DukeException;
import storage.Storage;

public interface Command {
    public abstract String[] execute(Storage storage) throws DukeException;
}
