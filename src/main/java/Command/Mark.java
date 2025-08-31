package Command;

import Storage.Storage;
import TaskList.Task;
import Ui.Ui;
import Exception.DukeException;

import java.util.List;

public class Mark implements Command {
    private final int index;
    private final boolean mark;

    public Mark(int index, boolean mark) {
        this.index = index;
        this.mark = mark;
    }

    @Override
    public void execute(Storage storage) throws DukeException {
        if (storage.isEmpty()) {
            throw new DukeException("Index does not exist! Attempted to mark " + this.index);
        } else {
            List<Task> t = storage.getTasks();
            t.get(this.index).setCompleted(this.mark);
            storage.readToFile();
        }
    }
}
