package Command;

import Storage.Storage;
import Ui.Ui;

public class Add implements Command {
    @Override
    public void execute(Storage storage) {
        Ui.display("Hi! I'm DukeGPT! Nice to meet you!");
    }
}
