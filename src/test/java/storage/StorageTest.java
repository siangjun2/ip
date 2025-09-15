package storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import task.Task;

public class StorageTest {
    @Test
    public void readFromFile_consistentRead() {
        List<Task> one = new Storage().getTasks();
        List<Task> two = new Storage().getTasks();
        for (int i = 0; i < one.size(); i++) {
            assertEquals(one.get(i), two.get(i));
        }
    }
}
