package TaskList;

import java.time.LocalDate;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + Task.dateToString(this.by) + ")";
    }

    @Override
    public String toFileString() {
        return "D" + super.toFileString() + " | " + this.by;
    }
}
