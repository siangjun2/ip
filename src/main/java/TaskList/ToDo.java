package TaskList;

public class ToDo extends Task{
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T" + super.toFileString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ToDo t) {
            return super.equals(o);
        }
        return false;
    }
}
