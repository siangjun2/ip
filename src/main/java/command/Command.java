package command;

import exception.DukeException;
import storage.Storage;

/**
 * Generic command interface to abstract and promise ability to execute
 */
public interface Command {
    public abstract String[] execute(Storage storage) throws DukeException;
}
