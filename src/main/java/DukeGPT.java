//import TaskList.Deadline;
//import TaskList.Event;
//import TaskList.Task;
//import TaskList.ToDo;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.IntStream;
//import java.util.regex.*;
//import java.nio.file.Paths;
//import java.nio.file.Path;
//import java.nio.file.Files;
//import java.io.FileWriter;
//
//public class DukeGPT {
//    public DukeGPT() {
//    }
//
//
//    private void addHelper(String message) {
//        Task t = null;
//
//        String dp = "deadline\\s+(.+)\\s+/by\\s+(.+)";
//        String ep = "event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)";
//
//        switch (message.split(" ")[0]) {
//        case "todo": //to/do borrow book
//            Pattern p = Pattern.compile("todo\\s+(.+)", Pattern.CASE_INSENSITIVE);
//            Matcher m = p.matcher(message);
//            if (m.find()) {
//                t = new ToDo(m.group(1));
//                DukeGPT.writeToFile("T | 0 | " + m.group(1));
//            } else {
//                this.printLine(() -> System.out.println("\tInvalid todo format!"));
//            }
//            break;
//        case "deadline": //deadline return book /by Sunday
//            p = Pattern.compile(dp, Pattern.CASE_INSENSITIVE);
//            m = p.matcher(message);
//            if (m.find()) {
//                t = new Deadline(m.group(1), m.group(2));
//                DukeGPT.writeToFile("D | 0 | " + m.group(1).strip() + " | " +
//                        m.group(2).strip());
//            } else {
//                this.printLine(() -> System.out.println("\tInvalid deadline format!"));
//            }
//            break;
//        case "event": //event project meeting /from Mon 2pm /to 4pm
//            p = Pattern.compile(ep, Pattern.CASE_INSENSITIVE);
//            m = p.matcher(message);
//            if (m.find()) {
//                t = new Event(m.group(1), m.group(2), m.group(3));
//                DukeGPT.writeToFile("E | 0 | " + m.group(1).strip() + " | " +
//                            m.group(2).strip() + " | " +
//                            m.group(3).strip());
//            } else {
//                this.printLine(() -> System.out.println("\tInvalid event format!"));
//            }
//            break;
//        default:
//            this.printLine(() -> System.out.println("\tInvalid task format!"));
//        }
//
//        if (t != null) {
//            this.messages.add(t);
//            String tm = t.toString();
//            this.printLine(() -> System.out.println("\tadded: " + tm + "\n\tNow you have " + this.messages.size() + " tasks in the list."));
//        }
//    }
//
//    private void deleteHelper(String message) {
//        String[] parts = message.split(" ");
//        if (parts.length == 2) {
//            try {
//                int index = Integer.valueOf(parts[1]) - 1;
//                if (!(index >= 0 && index < this.messages.size())) {
//                    NumberFormatException e = new NumberFormatException("Out of index");
//                    throw e;
//                }
//                Task t = this.messages.remove(index);
//                this.printLine(() -> System.out.println("\tNoted. I've removed this task:\n\t\t" + t + "\n\tNow you have " + this.messages.size() + " tasks in the list."));
//            } catch (NumberFormatException e) {
//                this.printLine(() -> System.out.println("\tInvalid integer used for delete!\t" + e));
//            } catch (IndexOutOfBoundsException e) {
//                this.printLine(() -> System.out.println("\tIndex out of bounds!\t" + e));
//            }
//        } else {
//            this.printLine(() -> System.out.println("\tInvalid usage of delete"));
//        }
//    }
//
//    private void exit() {
//        this.printLine(() -> System.out.println("\tBye. Hope to see you again soon!"));
//    }
//
//
//    public static void main(String[] args) {
//        DukeGPT duke = new DukeGPT(DukeGPT.PATH);
//        duke.greet();
//    }
//}
