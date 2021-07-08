package KonwersjaLiter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileConverter
{
    public void convert(String pathToFile) {
        File file = new File(pathToFile);
        if (file.exists()) {
            List<String> lines = readLines(file);
            lines = lines.stream().map(String::toUpperCase).collect(Collectors.toList());
            save(file, lines);
        }
    }

    private void save(File file, List<String> lines) {
        try (FileWriter writer = new FileWriter(file)) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<String> readLines(File file) {
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(file.toPath(), Charset.defaultCharset())) {
            lines = stream.collect(Collectors.toList());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return lines;
    }
}