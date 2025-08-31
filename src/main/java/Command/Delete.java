package Command;

import Exception.DukeException;
import Storage.Storage;
import TaskList.Task;

import java.util.List;

public class Delete implements Command{
    private final int index;

    public Delete(int index, boolean mark) {
        this.index = index;
    }

    @Override
    public void execute(Storage storage) throws DukeException {
        if (storage.isEmpty()) {
            throw new DukeException("Index does not exist! Attempted to mark " + this.index);
        } else {
            List<Task> t = storage.getTasks();
            t.remove(this.index);
            storage.readToFile();
        }
    }
}
