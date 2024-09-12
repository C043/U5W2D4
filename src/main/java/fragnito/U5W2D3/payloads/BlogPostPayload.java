package fragnito.U5W2D3.payloads;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlogPostPayload {
    private String titolo;
    private String contenuto;
    private String categoria;
    private int authorId;
    private int tempoDiLettura;
}
