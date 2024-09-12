package fragnito.U5W2D4.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthorDTO(
        @NotNull(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 10, message = "Il nome deve essere di almeno 3 caratteri e massimo 10")
        String nome,
        @NotNull(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 10, message = "Il cognome deve essere di almeno 3 caratteri e massimo 10")
        String cognome,
        @Email(message = "L'email inserita non è valida")
        String email,
        String dataDiNascita
) {
}
