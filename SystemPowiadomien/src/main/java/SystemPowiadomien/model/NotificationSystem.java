package SystemPowiadomien.model;

import SystemPowiadomien.exception.MessageException;
import SystemPowiadomien.service.EmailService;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class NotificationSystem {

    private final EmailService emailService;
    private final EmailDataProvider emailServiceDataProvider;

    public NotificationSystem(EmailService emailService, EmailDataProvider emailServiceDataProvider) {
        this.emailService = emailService;
        this.emailServiceDataProvider = emailServiceDataProvider;
    }

    public void sendNotification(String temat, String tresc) {
        boolean error = false;
        Receiver user = emailServiceDataProvider.getUser();
        List<String> adresaci = emailServiceDataProvider.getEmails();
        List<String> emailsCausedError = new ArrayList<>();
        String nadawca = user.getImie() + " " + user.getNazwisko();
        for (String adresat : adresaci) {
            String tematPowiadomienia = "Powiadomienie: " + temat;
            String trescPowiadomienia = "Powiadomienie od " + nadawca + "\n" + tresc;
            try {
                emailService.send(nadawca, adresat, tematPowiadomienia, trescPowiadomienia);
            } catch (Exception ex) {
                error = true;
                emailsCausedError.add(adresat);
            }
        }
        if (error) {
            String maile = mailList(emailsCausedError);
            try {
                emailService.send(nadawca, user.getEmail(), "Wysłanie maila zakończyło się niepowodzeniem", maile);
            } catch (Exception ex) {
                throw new MessageException("Wysłanie wiadomości o niedostarczeniu powiadomienia zakonczylo sie niepowodzeniem " +
                        "dla maili " + maile);
            }
        }
    }

    private String mailList(List<String> emailsCausedError) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        emailsCausedError.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}
