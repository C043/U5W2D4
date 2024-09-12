package fragnito.U5W2D4.payloads;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BlogPostDTO(
        @NotNull(message = "Il titolo è obbligatorio")
        @Size(min = 5, max = 24, message = "Il titolo deve essere minimo 5 caratteri e massimo 24")
        String titolo,
        @NotNull(message = "Il contenuto è obbligatorio")
        @Min(value = 100, message = "Il contenuto deve essere di minimo 100 caratteri")
        String contenuto,
        @NotNull(message = "La categoria è obbligatoria")
        String categoria,
        @NotNull(message = "L'id dell'autore è obbligatorio")
        int authorId,
        @NotNull(message = "Il tempo di lettura è obbligatorio")
        int tempoDiLettura
) {
}
