public class DukeGPT {

    public void greet() {
        this.printLine(() -> System.out.println("Hello! I'm DukeGPT\nWhat can I do for you?"));
    }

    public void echo() {

    }

    public void exit() {
        this.printLine(() -> System.out.println("Bye. Hope to see you again soon!"));
    }

    private void printLine(Runnable runnable) {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("DukeGPT (^_^)/ : ");
        runnable.run();
        System.out.println("----------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        DukeGPT duke = new DukeGPT();
        duke.greet();
        duke.exit();
    }
}
