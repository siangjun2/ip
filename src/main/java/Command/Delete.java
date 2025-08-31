package Command;

import Exception.DukeException;
import Storage.Storage;
import TaskList.Task;
import Ui.Ui;

import java.util.List;

public class Delete implements Command{
    private final int index;

    public Delete(int index) {
        this.index = index;
    }

    @Override
    public void execute(Storage storage) throws DukeException {
        if (storage.isEmpty()) {
            throw new DukeException("Index does not exist! Attempted to mark " + this.index);
        } else {
            List<Task> t = storage.getTasks();
            Task gone = t.remove(this.index);
            storage.readToFile();
            Ui.display( new String[]{"Noted. I've removed this task:", gone.toString(),
                        "Now you have " + t.size() + " tasks in the list."});
        }
    }
}
