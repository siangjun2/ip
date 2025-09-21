package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toString_correctFormat() {
        Deadline d = new Deadline("submit assignment", LocalDate.of(2025, 9, 30));
        assertEquals("[D][ ] submit assignment (by: Sep 30 2025)", d.toString());
    }

    @Test
    public void toFileString_correctFormat() {
        Deadline d = new Deadline("submit assignment", LocalDate.of(2025, 9, 30));
        assertEquals("D | 0 | submit assignment | 2025-09-30", d.toFileString());
    }

    @Test
    public void equals_sameDeadline_true() {
        Deadline d1 = new Deadline("submit assignment", LocalDate.of(2025, 9, 30));
        Deadline d2 = new Deadline("submit assignment", LocalDate.of(2025, 9, 30));
        assertEquals(d1, d2);
    }

    @Test
    public void equals_differentDeadline_false() {
        Deadline d1 = new Deadline("submit assignment", LocalDate.of(2025, 9, 30));
        Deadline d2 = new Deadline("submit assignment", LocalDate.of(2025, 10, 1));
        assertNotEquals(d1, d2);
    }
}
