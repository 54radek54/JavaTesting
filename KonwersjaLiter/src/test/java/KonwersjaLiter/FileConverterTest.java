package KonwersjaLiter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FileConverterTest
{
    public String directory = "filefold";
    private final FileConverter subject = new FileConverter();

    @Test
    void test_1() throws URISyntaxException {
        assertEquals("RADOSLAW BARTCZYK 123", getFileLines("1.txt").get(0));
    }

    @Test
    void test_2() throws URISyntaxException {
        assertEquals("RADOSLAW BARTCZYK 123", getFileLines("2.txt").get(0));
    }

    @Test
    void test_3() throws URISyntaxException {
        List<String> expected = List.of("B", "A", "R", "T", "C", "Z", "Y", "K", "", "1", "2", "3");
        assertIterableEquals(expected, getFileLines("3.txt"));
    }

    @Test
    void test_4() throws URISyntaxException {
        List<String> expected = List.of("B", "A", "R", "T", "C", "Z", "Y", "K", "1", "2", "3");
        assertIterableEquals(expected, getFileLines("4.txt"));
    }

    private List<String> getFileLines(String fileName) throws URISyntaxException {
        String pathToFile = pathGetter(this.directory, fileName);
        File file = new File(pathToFile);
        subject.convert(file.getAbsolutePath());
        return subject.readLines(file);
    }

    private String pathGetter(String directory, String fileName) throws URISyntaxException {
        return Paths.get(getClass().getClassLoader().getResource(directory + "/" + fileName).toURI()).toString();
    }
}

