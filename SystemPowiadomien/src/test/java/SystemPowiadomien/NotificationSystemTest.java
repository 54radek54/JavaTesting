package SystemPowiadomien;

import SystemPowiadomien.exception.MessageException;
import SystemPowiadomien.model.NotificationSystem;
import SystemPowiadomien.service.EmailService;
import SystemPowiadomien.model.EmailDataProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class NotificationSystemTest {
    private final String exMessage="Wysłanie maila zakończyło się niepowodzeniem";
    private final String notifyException="Wysłanie wiadomości o niedostarczeniu powiadomienia zakonczylo sie niepowodzeniem dla maili ";
    private static final String FILE_NAME = "dataSource.txt";
    private final String nadawca = "Radoslaw Bartczyk";
    private final EmailService emailService;
    private final NotificationSystem notification;

    public NotificationSystemTest() throws URISyntaxException {
        String pathToFile = pathGetter(FILE_NAME);
        EmailDataProvider dataProvider = new EmailDataProvider(pathToFile);
        this.emailService = mock(EmailService.class);
        this.notification = new NotificationSystem(emailService, dataProvider);
    }

    @Test
    void sendMailWithSuccess() {
        List<String> emails = List.of("anowak@gmail.com", "kchojnicka@gmail.com", "ffelicki@gmail.com", "dwalaszek@gmail.com");
        notification.sendNotification("temat", "tresc");
        verify(emailService, times(4))
                .send(anyString(), anyString(), anyString(), anyString());
        for (String email : emails) {
            verifyMailHasBeenSent(emailService, nadawca, email);
        }
    }

    @Test
    void sendMailWithOneFailure() {
        List<String> emails = List.of("kchojnicka@gmail.com", "ffelicki@gmail.com", "dwalaszek@gmail.com");
        throwMailServiceException(emailService, nadawca, "anowak@gmail.com");
        notification.sendNotification("temat", "tresc");
        verify(emailService, times(5))
                .send(anyString(), anyString(), anyString(), anyString());
        verify(emailService, times(1))
                .send(eq(nadawca), eq("NetFlix@gmail.com"), eq(exMessage), eq("anowak@gmail.com"));
        for (String email : emails) {
            verifyMailHasBeenSent(emailService, nadawca, email);
        }
    }

    @Test
    void sendMailWithAllFailure() {
        List<String> emails = List.of("anowak@gmail.com", "kchojnicka@gmail.com", "ffelicki@gmail.com", "dwalaszek@gmail.com");
        for (String email : emails) {
            throwMailServiceException(emailService, nadawca, email);
        }
        notification.sendNotification("temat", "tresc");
        verify(emailService, times(5))
                .send(anyString(), anyString(), anyString(), anyString());
        verify(emailService, times(1))
                .send(eq(nadawca), eq("NetFlix@gmail.com"), eq(exMessage),
                        eq("anowak@gmail.com kchojnicka@gmail.com ffelicki@gmail.com dwalaszek@gmail.com"));
    }

    @Test
    void sendMailWithOneFailureAndNoBackNotification() {
        List<String> emails = List.of("kchojnicka@gmail.com", "ffelicki@gmail.com", "dwalaszek@gmail.com");
        throwMailServiceException(emailService, nadawca, "anowak@gmail.com");
        throwMailServiceException(emailService, nadawca, "NetFlix@gmail.com");
        try {
            notification.sendNotification("temat", "tresc");
        } catch (MessageException ex) {
            assertEquals(notifyException +
                    "anowak@gmail.com", ex.getMessage());
        }
        for (String email : emails) {
            verifyMailHasBeenSent(emailService, nadawca, email);
        }
    }

    @Test
    void sendMailWithAllFailureAndNoBackNotification() {
        List<String> emails = List.of("anowak@gmail.com", "kchojnicka@gmail.com", "ffelicki@gmail.com", "dwalaszek@gmail.com", "NetFlix@gmail.com");
        for (String email : emails) {
            throwMailServiceException(emailService, nadawca, email);
        }
        try {
            notification.sendNotification("temat", "tresc");
        } catch (MessageException ex) {
            assertEquals(notifyException +
                    "anowak@gmail.com kchojnicka@gmail.com ffelicki@gmail.com dwalaszek@gmail.com", ex.getMessage());
        }
    }

    private void verifyMailHasBeenSent(EmailService emailService, String nadawca, String adresat) {
        verify(emailService, times(1)).send(eq(nadawca), eq(adresat), ArgumentMatchers.startsWith("Powiadomienie: "),
                ArgumentMatchers.startsWith("Powiadomienie od " + nadawca));
    }

    private void throwMailServiceException(EmailService emailService, String nadawca, String adresat) {
        doThrow(MessageException.class).when(emailService).send(eq(nadawca), eq(adresat), anyString(), anyString());
    }

    private String pathGetter(String file) throws URISyntaxException {
        return Paths.get(getClass().getClassLoader().getResource(file).toURI()).toString();
    }
}
