import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DukeGPT {
    private List<String> messages = new ArrayList<String>();

    public void greet() {
        this.printLine(() -> System.out.println("\tHello! I'm DukeGPT\n\tWhat can I do for you?"));
        this.respond();
    }

    public void respond() {
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();

        while (!message.equals("bye")) {
            if (message.equals("list")) {
                this.printLine(() -> IntStream.range(0,messages.size()).forEach(i -> System.out.println("\t" + (i+1) + ". " + messages.get(i))));
            } else {
                String finalMessage = message;
                messages.add(finalMessage);
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
