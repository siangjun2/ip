package Command;

import Exception.DukeException;
import Storage.Storage;
import TaskList.Task;
import Ui.Ui;

import java.util.List;
import java.util.stream.IntStream;

public class ListOut implements Command {
    @Override
    public void execute(Storage storage) throws DukeException {
        List<Task> t = storage.getTasks();
        String[] output = new String[t.size()];
        IntStream.range(0,t.size()).forEach(i -> output[i] = ((i + 1) + ". " + t.get(i)));
        Ui.display(output);
    }

    public static void main(String[] args) {
        Storage s = new Storage();
        Command c = new ListOut();
        try {
            c.execute(s);
        } catch (DukeException e) {
            Ui.displayError(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ListOut;
    }
}
