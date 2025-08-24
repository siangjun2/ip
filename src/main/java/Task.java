public class Task {
    private boolean completed = false;
    private final String description;

    public Task(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "[" + (completed ? "X" : " ") + "] " + description;
    }
}
