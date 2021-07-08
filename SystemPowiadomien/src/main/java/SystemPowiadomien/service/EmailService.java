package SystemPowiadomien.service;


public interface EmailService
{
    void send(String nadawca, String adresat, String tematPowiadomienia, String trescPowiadomienia);
}
