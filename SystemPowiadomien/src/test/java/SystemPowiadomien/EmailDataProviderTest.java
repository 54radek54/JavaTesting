package SystemPowiadomien;

import SystemPowiadomien.model.Receiver;
import SystemPowiadomien.model.EmailDataProvider;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailDataProviderTest
{
    public final String directory = "dataSource.txt";
    public final String directory2 = "dataSource2.txt";

    @Test
    void test_readUser() throws URISyntaxException {
        readUser(directory,"Radoslaw", "Bartczyk", "NetFlix@gmail.com");
        readUser(directory2,"Dominik", "Bartnik", "NetFlixSupport@gmail.com");
    }
    @Test
    void test_readEmails() throws URISyntaxException {
        readEmails(directory,"@gmail.com",4);
        readEmails(directory2,"@onet.com",3);
    }

    void readEmails(String directoryOfFile, String emailFormat, Integer numberOfOccurance) throws URISyntaxException {
        List<String> actual = dataReader(directoryOfFile).getEmails();
        actual.forEach(s -> assertTrue(s.contains(emailFormat)));
        assertEquals(numberOfOccurance, actual.size());
    }
    void readUser(String directoryOfFile, String name, String surname,String email) throws URISyntaxException {
        Receiver expected = new Receiver(name, surname, email);
        Receiver actual = dataReader(directoryOfFile).getUser();
        assertEquals(expected, actual);
    }

    private EmailDataProvider dataReader(String directories) throws URISyntaxException {
        String pathToFile = pathGetter(directories);
        return new EmailDataProvider(pathToFile);
    }

    private String pathGetter(String file) throws URISyntaxException {
        return Paths.get(getClass().getClassLoader().getResource(file).toURI()).toString();
    }
}
