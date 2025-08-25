import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.regex.*;

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
            if (message.startsWith("todo") || message.startsWith("deadline") || message.startsWith("event")) {
                this.addHelper(message);
            } else if (message.startsWith("mark ") || message.startsWith("unmark ")) { //Marking as done or not done
                this.markHelper(message);
            } else if (message.equals("list")) { //Listing items
                this.listHelper();
            } else {
                this.printLine(() -> System.out.println("\tInvalid input!"));
            }
                message = sc.nextLine();
        }
        this.exit();
    }

    private void markHelper(String message) {
        if (this.messages.isEmpty()) {
            this.printLine(() -> System.out.println("\tCannot mark when no task added!"));
            return;
        }
        String[] parts = message.split(" ");
        if (parts.length == 2) {
            try {
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
    }

    private void listHelper() {
        this.printLine(() -> IntStream.range(0,messages.size()).forEach(i -> System.out.println("\t" + (i+1) + ". " + messages.get(i))));
    }

    private void addHelper(String message) {
        Task t = null;

        String dp = "deadline\\s+(.+)\\s+/by\\s+(.+)";
        String ep = "event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)";

        switch (message.split(" ")[0]) {
            case "todo": //to/do borrow book
                Pattern p = Pattern.compile("todo\\s+(.+)", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(message);
                if (m.find()) {
                    t = new ToDo(m.group(1));
                } else {
                    this.printLine(() -> System.out.println("\tInvalid todo format!"));
                }
                break;
            case "deadline": //deadline return book /by Sunday
                p = Pattern.compile(dp, Pattern.CASE_INSENSITIVE);
                m = p.matcher(message);
                if (m.find()) {
                    t = new Deadline(m.group(1), m.group(2));
                } else {
                    this.printLine(() -> System.out.println("\tInvalid deadline format!"));
                }
                break;
            case "event": //event project meeting /from Mon 2pm /to 4pm
                p = Pattern.compile(ep, Pattern.CASE_INSENSITIVE);
                m = p.matcher(message);
                if (m.find()) {
                    t = new Event(m.group(1), m.group(2), m.group(3));
                } else {
                    this.printLine(() -> System.out.println("\tInvalid event format!"));
                }
                break;
            default:
                this.printLine(() -> System.out.println("\tInvalid task format!"));
        }

        if (t != null) {
            this.messages.add(t);
            String tm = t.toString();
            this.printLine(() -> System.out.println("\tadded: " + tm + "\n\tNow you have " + this.messages.size() + " tasks in the list."));
        }
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
