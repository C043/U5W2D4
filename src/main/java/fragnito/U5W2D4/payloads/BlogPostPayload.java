package fragnito.U5W2D4.payloads;


public record BlogPostPayload(
        String titolo,
        String contenuto,
        String categoria,
        int authorId,
        int tempoDiLettura
) {
}
