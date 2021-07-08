package KonwersjaLiter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class DirConverterTest
{
    private final String directory = "filefold";
    private final FileConverter fileConverter = new FileConverter();
    private final DirConverter dirConverter = new DirConverter(fileConverter);

    @Test
    void test_behaviorVer1() throws URISyntaxException, IOException {
        String extension = ".txt";
        convertCase(extension);
        List<List<String>> filesContents = getFilesContents(extension);
        assertEquals(4, filesContents.size());
        for (List<String> lines : getFilesContents(extension)) {
            for (String line : lines) {
                assertEquals(line, line.toUpperCase());
            }
        }
    }

    @Test
    void test_behaviorVer2() throws URISyntaxException, IOException {
        String extension = ".json";
        convertCase(extension);
        List<List<String>> filesContents = getFilesContents(extension);
        assertEquals(2, filesContents.size());
        for (List<String> lines : getFilesContents(extension)) {
            for (String line : lines) {
                assertEquals(line, line.toUpperCase());
            }
        }
    }

    @Test
    void test_behaviorVer3() throws URISyntaxException, IOException {
        String extension = ".css";
        convertCase(extension);
        List<List<String>> filesContents = getFilesContents(extension);
        assertEquals(1, filesContents.size());
        for (List<String> lines : getFilesContents(extension)) {
            for (String line : lines) {
                assertEquals(line, line.toUpperCase());
            }
        }
    }

    @Test
    void test_behaviorVer4() throws URISyntaxException, IOException {
        String extension = ".js";
        convertCase(extension);
        List<List<String>> filesContents = getFilesContents(extension);
        assertEquals(1, filesContents.size());
        for (List<String> lines : getFilesContents(extension)) {
            for (String line : lines) {
                assertEquals(line, line.toUpperCase());
            }
        }
    }

    private void convertCase(String extension) throws URISyntaxException {
        String pathToDir = pathGet(directory);
        dirConverter.convertFiles(pathToDir, extension);
    }

    private List<List<String>> getFilesContents(String extension) throws IOException, URISyntaxException {
        List<List<String>> filesContents = new ArrayList<>();
        File directory = new File(pathGet(this.directory));
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null && files.length != 0) {
                for (File file : files) {
                    if (file.getName().endsWith(extension)) {
                        filesContents.add(Files.lines(file.toPath(), Charset.defaultCharset()).collect(Collectors.toList()));
                    }
                }
            }
        }
        return filesContents;
    }

    private String pathGet(String directory) throws URISyntaxException {
        return Paths.get(getClass().getClassLoader().getResource(directory).toURI()).toString();
    }
}

