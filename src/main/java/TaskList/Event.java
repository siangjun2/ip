package TaskList;

import java.time.LocalDate;

public class Event extends Task{
    private final LocalDate from;
    private final LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + Task.dateToString(this.from) +
                    " to: " + Task.dateToString(this.to) + ")";
    }

    @Override
    public String toFileString() {
        return "E" + super.toFileString() + " | " + this.from + " | " + this.to;
    }
}
