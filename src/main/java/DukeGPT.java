import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DukeGPT {
    private List<Task> messages = new ArrayList<Task>();

    public void greet() {
        this.printLine(() -> System.out.println("\tHello! I'm DukeGPT\n\tWhat can I do for you?"));
        this.respond();
    }

    public void respond() {
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();

        while (!message.equals("bye")) {
            if (!this.messages.isEmpty() && (message.startsWith("mark ") || message.startsWith("unmark "))) { //Marking as done or not done
                    String[] parts = message.split(" ");
                    if (parts.length == 2) {
                        try { //Maybe refactor and abstract this part & reduce lines in try block
                            int index = Integer.valueOf(parts[1]) - 1;
                            if (!(index >= 0 && index < this.messages.size())) {
                                NumberFormatException e = new NumberFormatException("Out of index");
                                throw e;
                            }
                            this.messages.get(index).setCompleted(message.startsWith("mark "));
                            if (message.startsWith("mark ")) {
                                this.printLine(() -> System.out.println("\tNice! I've marked this task as done:\n\t\t" + this.messages.get(index)));
                            } else {
                                this.printLine(() -> System.out.println("\tOk! I've marked this task as not done yet:\n\t\t" + this.messages.get(index)));
                            }
                        } catch (NumberFormatException e) {
                            this.printLine(() -> System.out.println("\tInvalid integer used for mark/unmark!\t" + e));
                        }
                    } else {
                        this.printLine(() -> System.out.println("\tInvalid usage of mark/unmark!"));
                    }
            } else if (message.equals("list")) { //Listing items
                this.printLine(() -> IntStream.range(0,messages.size()).forEach(i -> System.out.println("\t" + (i+1) + ". " + messages.get(i))));
            } else { //Normal addition to list
                String finalMessage = message;
                messages.add(new Task(message));
                this.printLine(() -> System.out.println("\tadded: " + finalMessage));
            }
                message = sc.nextLine();
        }
        this.exit();
    }

    private void exit() {
        this.printLine(() -> System.out.println("\tBye. Hope to see you again soon!"));
    }

    private void printLine(Runnable runnable) {
        System.out.println("\t----------------------------------------------------------------------");
        System.out.println("\tDukeGPT (^_^)/ : ");
        runnable.run();
        System.out.println("\t----------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        DukeGPT duke = new DukeGPT();
        duke.greet();
    }
}
