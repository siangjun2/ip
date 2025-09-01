package Command;

import Exception.DukeException;
import Storage.Storage;
import TaskList.Task;
import Ui.Ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Find implements Command {
    private final String text;

    public Find(String text) {
        this.text = text;
    }

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
