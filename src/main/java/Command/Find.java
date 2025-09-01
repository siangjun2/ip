package Command;

import Exception.DukeException;
import Storage.Storage;
import TaskList.Task;
import Ui.Ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Matches all {@link Task} in task list that have given string
 * in description.
 */
public class Find implements Command {
    /**
     * Substring to be found in description
     */
    private final String text;

    /**
     * Initialises command object with substring
     * @param text Substring to be found
     */
    public Find(String text) {
        this.text = text;
    }

    /**
     * Matches all tasks in storage task list that have a
     * description containing given substring.
     * @param storage   Storage under consideration
     * @throws DukeException    Handles any error during execution
     */
    @Override
    public void execute(Storage storage) throws DukeException {
        List<Task> tasks = storage.getTasks();
        List<Task> found  = new ArrayList<>();

        for (Task t : tasks) {
            if (t.containsString(this.text)) {
                found.add(t);
            }
        }

        String[] output = new String[found.size() + 1];
        IntStream.range(0,found.size()).forEach(i -> output[i] = ((i + 1) + ". " + found.get(i)));
        output[found.size()] = "Here are your tasks! Found " + found.size() + " matching tasks.";
        Ui.display(output);
    }
}
