package SystemPowiadomien.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmailDataProvider
{
    private static List<String> DATA_LINES = new ArrayList<>();
    private final String pathToFile;

    public EmailDataProvider(String pathToFile)
    {
        this.pathToFile = pathToFile;
    }

    public Receiver getUser()
    {
        readLines();
        Receiver user = new Receiver();
        DATA_LINES.stream()
                .filter(s -> s.chars()
                        .filter(ch -> ch == ' ').count() == 2)
                .findAny().ifPresent(s ->
        {
            String[] userData = s.split(" ");
            user.setImie(userData[0]);
            user.setNazwisko(userData[1]);
            user.setEmail(userData[2]);
        });
        return user;
    }

    public List<String> getEmails()
    {
        readLines();
        return DATA_LINES.stream()
                .filter(s -> s.chars()
                        .filter(ch -> ch == ' ').count() == 0)
                .collect(Collectors.toList());
    }

    private void readLines()
    {
        File file = new File(pathToFile);
        try (Stream<String> stream = Files.lines(file.toPath(), Charset.defaultCharset()))
        {
            DATA_LINES = stream.collect(Collectors.toList());
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
