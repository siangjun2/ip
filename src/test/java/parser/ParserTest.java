package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import command.Exit;
import command.Invalid;
import command.ListOut;
import exception.DukeException;
import ui.Ui;


public class ParserTest {
    @Test
    public void stringToDate_correctLayout_noException() {
        try {
            assertEquals(LocalDate.of(2023, 12, 12), Parser.stringToDate("2023-12-12"));
            assertEquals(LocalDate.of(1234, 12, 31), Parser.stringToDate("1234-12-31"));
        } catch (DukeException e) {
            Ui.displayError(e);
            assertEquals(1, 0);
        }
    }

    @Test
    public void stringToDate_wrongLayout_throwsException() {
        assertThrows(DukeException.class, () -> Parser.stringToDate("rubbish"));
        assertThrows(DukeException.class, () -> Parser.stringToDate("2023-14-14"));
        assertThrows(DukeException.class, () -> Parser.stringToDate("1234-12-32"));
    }

    @Test
    public void parse_correctList() {
        assertEquals(new ListOut(), Parser.parse("list"));
        assertEquals(new Invalid(), Parser.parse("list please"));
        assertEquals(new Invalid(), Parser.parse("l1st"));
    }

    @Test
    public void parse_correctExit() {
        assertEquals(new Exit(), Parser.parse("bye"));
        assertEquals(new Invalid(), Parser.parse("bye bye"));
        assertEquals(new Exit(), Parser.parse("exit"));
    }
}
