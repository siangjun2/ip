package Parser;

import Command.Command;
import Exception.DukeException;
import TaskList.Deadline;
import TaskList.Event;
import TaskList.Task;
import TaskList.ToDo;
import Ui.Ui;
import Command.Exit;
import Command.Find;
import Command.Greet;
import Command.Add;
import Command.Invalid;
import Command.ListOut;
import Command.Mark;
import Command.Delete;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Class in charge of converting string inputs into more usable classes
 */
public class Parser {
    /**
     * Helper function that converts date in string format to LocalDate object.
     * Accepts YYYY-MM-DD format. More format to be added in future
     * @param s Date in string format
     * @return Date in LocalDate format
     * @throws DukeException Handles errors during execution
     */
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
        throw new DukeException("Invalid string format used to convert to date! Attempted to convert " + s);
    }

    /**
     * Handles converting user inputs into executable commands
     * @param s Input by users. Fixed to specified formats
     * @return  Corresponding command from input s. Invalid if unsuccessful.
     */
    public static Command parse(String s) {
        s = s.strip();
        if (s.equals("bye") | s.equals("exit")) {
            return new Exit();
        } else if (s.equals("list")) {
            return new ListOut();
        } else if (s.startsWith("find")) {
            return new Find(s.substring(5));
        } else if (s.startsWith("delete")) {
            try {
                int index = Parser.extractIndex(s);
                return new Delete(index);
            } catch (DukeException e) {
                Ui.displayError(e);
            }
        } else if (s.startsWith("mark") | s.startsWith("unmark")) {
            try {
                int index = Parser.extractIndex(s);
                return new Mark(index, s.startsWith("mark"));
            } catch (DukeException e) {
                Ui.displayError(e);
            }
        } else if (s.startsWith("deadline") | s.startsWith("event") | s.startsWith("todo")) {
            try {
                return Parser.createAdd(s);
            } catch (DukeException e) {
                Ui.displayError(e);
            }
        }
        return new Invalid();
    }

    /**
     * Internal helper function that handles different types of events to be added.
     * @param s String input by user
     * @return  Add command if successful. Else Invalid.
     * @throws DukeException    Handles error in execution
     */
    private static Command createAdd(String s) throws DukeException {
        if (s.startsWith("todo")) {
            return new Add(new ToDo(s.substring(5)));
        } else if (s.startsWith("deadline")) {
            String re = "deadline\\s+(.+)\\s+/by\\s+(.+)";

            Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(s);
            if (m.find()) {
                try {
                    LocalDate date = Parser.stringToDate(m.group(2));
                    return new Add(new Deadline(m.group(1).strip(), date));
                } catch (DukeException e) {
                    throw new DukeException("While creating deadline - " + e.toString());
                }
            } else {
                throw new DukeException("Invalid deadline format used! Given " + s);
            }
        } else if (s.startsWith("event")) {
            String re = "event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)";

            Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(s);
            if (m.find()) {
                try {
                    LocalDate from = Parser.stringToDate(m.group(2));
                    LocalDate to = Parser.stringToDate(m.group(3));
                    return new Add(new Event(m.group(1).strip(), from, to));
                } catch (DukeException e) {
                    throw new DukeException("While creating event - " + e.toString());
                }
            } else {
                throw new DukeException("Invalid event format used! Given " + s);
            }
        } else {
            return new Invalid();
        }
    }

    /**
     * Internal helper function to extract index and check format for mark and delete
     * commands. Handles invalid formats.
     * @param s String command by user
     * @return  int index if successful
     * @throws DukeException    Handles error in execution. Specifically wrong formatting.
     */
    private static int extractIndex(String s) throws DukeException{
        String[] parts = s.split(" ");
        if (parts.length != 2) {
            throw new DukeException("Invalid format used for delete/mark command");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Invalid integer used for delete/mark command");
        }
    }
}
