package Command;

import Exception.DukeException;
import Storage.Storage;
import Ui.Ui;
import TaskList.Task;

import java.util.List;

public class Add implements Command {
    private final Task task;

    public Add(Task task) {
        this.task = task;
    }

    @Override
    public void execute(Storage storage) throws DukeException {
        List<Task> t = storage.getTasks();
        t.add(this.task);
        storage.readToFile();
        Ui.display("Added ", this.task.toString(), "Now you have " + t.size() + " tasks in the list.");
    }
}
