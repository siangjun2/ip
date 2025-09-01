package Storage;

import TaskList.Deadline;
import TaskList.Event;
import TaskList.Task;
import TaskList.ToDo;
import Ui.Ui;
import Exception.DukeException;
import Parser.Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Storage {
    private static final String PATH = String.valueOf(Paths.get(System.getProperty("user.dir"),
            "data", "duke.txt"));
    private final List<Task> tasks;

    public Storage() {
        this.tasks = Storage.readFromFile();
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    private static void ensureExists() {
        try {
            Path p = Paths.get(Storage.PATH);
            if (!Files.exists(p)) {
                if (!Files.exists(p.getParent())) {
                    Files.createDirectories(p.getParent());
                }
                Files.createFile(p);
            }
        } catch (IOException e) {
            Ui.displayError(e);
        }
    }

    public static List<Task> readFromFile() {
        //Ensure file path exists
        Storage.ensureExists();

        //Reading file
        List<Task> output = new ArrayList<>();
        Path pf = Paths.get(Storage.PATH);

        try {
            List<String> lines = Files.readAllLines(pf);
            String re = "([DTE])\\s+\\|\\s+([01])\\s+\\|\\s+(.+)";
            List<Integer> corrupted = new ArrayList<>();

            for (int i = 0; i < lines.size(); i++) {
                String s = lines.get(i);

                Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(s);
                if (m.find()) {
                    Task t = null;
                    switch (m.group(1)) {
                    case "T":
                        t = new ToDo(m.group(3));
                        break;
                    case "D":
                        String[] smaller = m.group(3).split("\\|");
                        if (smaller.length != 2) {
                            corrupted.add(i);
                            break;
                        }
                        try {
                            t = new Deadline(smaller[0], Parser.stringToDate(smaller[1]));
                        } catch (DukeException e) {
                            Ui.displayError(e);
                            corrupted.add(i);
                        }
                        break;
                    case "E":
                        smaller = m.group(3).split("\\|");
                        if (smaller.length != 3) {
                            corrupted.add(i);
                            break;
                        }
                        try {
                            t = new Event(smaller[0], Parser.stringToDate(smaller[1]),
                                        Parser.stringToDate(smaller[2]));
                        } catch (DukeException e) {
                            Ui.displayError(e);
                            corrupted.add(i);
                        }
                        break;
                    default:
                        Ui.displayError(new DukeException("Fatal error in reading from file! Attempted to read " + s));
                    }
                    if (t != null) {
                        t.setCompleted(m.group(2).equals("1"));
                        output.add(t);
                    }
                } else {
                    corrupted.add(i);
                }
            }

            //Removing corrupted lines
            if (!corrupted.isEmpty()) {
                for (int i = corrupted.size() - 1; i >= 0; i--) {
                    lines.remove((int) corrupted.get(i));
                }
                Files.write(pf, lines);
            }
        } catch (IOException e) {
            Ui.displayError(e);
        }
        return output;
    }

    public void readToFile() throws DukeException {
        Path p = Paths.get(Storage.PATH);
        List<String> lines = new ArrayList<>();

        for (Task t : this.tasks) {
            lines.add(t.toFileString());
        }
        try {
            Files.write(p, lines);
        } catch (IOException e) {
            throw new DukeException("Error while attempting to read back to file");
        }
    }

    public static void appendToFile(String text){
        Storage.ensureExists();
        try {
            FileWriter fw = new FileWriter(Storage.PATH, true);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            Ui.displayError(e);
        }
    }
}
