package SystemPowiadomien.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receiver
{
    //imię, nazwisko, adres e-mail użytkownika ladowane z pliku
    private String imie;
    private String nazwisko;
    private String email;
}
