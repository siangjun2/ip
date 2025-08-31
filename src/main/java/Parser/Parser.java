package Parser;

import Command.Command;
import Exception.DukeException;
import Ui.Ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Parser {
    public static LocalDate stringToDate(String s) throws DukeException {
        String re = "(\\d{4})-(\\d{2})-(\\d{2})";
        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(s.strip());

        try {
            if (m.find()) {
                int year = Integer.parseInt(m.group(1));
                int month = Integer.parseInt(m.group(2));
                int day = Integer.parseInt(m.group(3));
                return LocalDate.of(year, month, day);
            }
        } catch (DateTimeException e) {
            throw new DukeException("Invalid string used to convert to date! Attempted to convert " + s);
        }
        throw new DukeException("CRITICAL regex error. Should not be able to reach here");
    }

    public static Command parse(String s) {
        """
        delete 3
        mark 3
        list todo borrow book
        deadline return book /by Sunday
        event project meeting/from /to        
        """
    }

//    String[] parts = message.split(" ");
//        if (parts.length == 2) {
//        try {
//            int index = Integer.valueOf(parts[1]) - 1;
//            if (!(index >= 0 && index < this.messages.size())) {
//                NumberFormatException e = new NumberFormatException("Out of index");
//                throw e;
//            }
//            this.messages.get(index).setCompleted(message.startsWith("mark "));
//            if (message.startsWith("mark ")) {
//                this.printLine(() -> System.out.println("\tNice! I've marked this task as done:\n\t\t" + this.messages.get(index)));
//            } else {
//                this.printLine(() -> System.out.println("\tOk! I've marked this task as not done yet:\n\t\t" + this.messages.get(index)));
//            }
//        } catch (NumberFormatException e) {
//            this.printLine(() -> System.out.println("\tInvalid integer used for mark/unmark!\t" + e));
//        }
//    } else {
//        this.printLine(() -> System.out.println("\tInvalid usage of mark/unmark!"));
//    }
}
