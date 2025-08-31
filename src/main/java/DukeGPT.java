import TaskList.Deadline;
import TaskList.Event;
import TaskList.Task;
import TaskList.ToDo;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.regex.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.FileWriter;

public class DukeGPT {
    private final List<Task> messages = new ArrayList<Task>();
    private static final Path PATH = Paths.get(System.getProperty("user.dir"),"data", "duke.txt");

    public DukeGPT(Path filepath) {
        //Attempt to open file at path. If failed, create directory and txt.
        try {
            if (!Files.exists(filepath)) {
                if (!Files.exists(filepath.getParent())) {
                    Files.createDirectories(filepath.getParent());
                }
                Files.createFile(filepath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Read and initialise object
        try {
            List<String> lines = Files.readAllLines(filepath);
            String re = "(D|T|E)\\s+\\|\\s+(0|1)\\s+\\|\\s+(.+)";
            List<Integer> corrupted = new ArrayList<>();

            for (int i = 0; i < lines.size(); i++) {
                String s = lines.get(i);

                Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(s);
                if (m.find()) {
                    Task t = null;
                    switch (m.group(1)) {
                        case "T":
                            t = new ToDo(m.group(3).strip());
                            break;
                        case "D":
                            String[] smaller = m.group(3).split("\\|");
                            if (smaller.length != 2) {
                                corrupted.add(i);
                                break;
                            }
                            t = new Deadline(smaller[0].strip(), smaller[1].strip());
                            break;
                        case "E":
                            smaller = m.group(3).split("\\|");
                            if (smaller.length != 3) {
                                corrupted.add(i);
                                break;
                            }
                            t = new Event(smaller[0].strip(), smaller[1].strip(), smaller[2].strip());
                            break;
                        default:
                            System.out.println("Corrupted file used to initialise. Attempted to initialise" + s);
                    }
                    if (t != null) {
                        t.setCompleted(m.group(2).equals("1"));
                        this.messages.add(t);
                    }
                } else {
                    corrupted.add(i);
                }
            }
            if (!corrupted.isEmpty()) {
                for (int i = corrupted.size() - 1; i >= 0; i--) {
                    lines.remove((int) corrupted.get(i));
                }
                Files.write(filepath, lines);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
            } else if (message.startsWith("delete ")) {
                this.deleteHelper(message);
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
                DukeGPT.writeToFile("T | 0 | " + m.group(1));
            } else {
                this.printLine(() -> System.out.println("\tInvalid todo format!"));
            }
            break;
        case "deadline": //deadline return book /by Sunday
            p = Pattern.compile(dp, Pattern.CASE_INSENSITIVE);
            m = p.matcher(message);
            if (m.find()) {
                t = new Deadline(m.group(1), m.group(2));
                DukeGPT.writeToFile("D | 0 | " + m.group(1).strip() + " | " +
                        m.group(2).strip());
            } else {
                this.printLine(() -> System.out.println("\tInvalid deadline format!"));
            }
            break;
        case "event": //event project meeting /from Mon 2pm /to 4pm
            p = Pattern.compile(ep, Pattern.CASE_INSENSITIVE);
            m = p.matcher(message);
            if (m.find()) {
                t = new Event(m.group(1), m.group(2), m.group(3));
                DukeGPT.writeToFile("E | 0 | " + m.group(1).strip() + " | " +
                            m.group(2).strip() + " | " +
                            m.group(3).strip());
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

    private static void writeToFile(String textToAdd){
        try {
            String filePath = String.valueOf(DukeGPT.PATH);
            FileWriter fw = new FileWriter(filePath,true);
            fw.write(textToAdd);
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void deleteHelper(String message) {
        String[] parts = message.split(" ");
        if (parts.length == 2) {
            try {
                int index = Integer.valueOf(parts[1]) - 1;
                if (!(index >= 0 && index < this.messages.size())) {
                    NumberFormatException e = new NumberFormatException("Out of index");
                    throw e;
                }
                Task t = this.messages.remove(index);
                this.printLine(() -> System.out.println("\tNoted. I've removed this task:\n\t\t" + t + "\n\tNow you have " + this.messages.size() + " tasks in the list."));
            } catch (NumberFormatException e) {
                this.printLine(() -> System.out.println("\tInvalid integer used for delete!\t" + e));
            } catch (IndexOutOfBoundsException e) {
                this.printLine(() -> System.out.println("\tIndex out of bounds!\t" + e));
            }
        } else {
            this.printLine(() -> System.out.println("\tInvalid usage of delete"));
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
        DukeGPT duke = new DukeGPT(DukeGPT.PATH);
        duke.greet();
    }
}
