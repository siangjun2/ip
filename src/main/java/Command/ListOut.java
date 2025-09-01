package Command;

import Exception.DukeException;
import Storage.Storage;
import TaskList.Task;
import Ui.Ui;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Represents a List command, that lists all tasks
 */
public class ListOut implements Command {
    /**
     * Executes the command, by listing all tasks in storage
     * @param storage   Storage currently being used
     * @throws DukeException    Handles error during execution
     */
    @Override
    public void execute(Storage storage) throws DukeException {
        List<Task> t = storage.getTasks();
        String[] output = new String[t.size()];
        IntStream.range(0,t.size()).forEach(i -> output[i] = ((i + 1) + ". " + t.get(i)));
        Ui.display(output);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ListOut;
    }
}
