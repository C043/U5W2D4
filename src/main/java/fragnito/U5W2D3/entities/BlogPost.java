package fragnito.U5W2D3.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private String titolo;
    private String cover;
    @Column(nullable = false)
    private String contenuto;
    @Column(name = "tempo_lettura", nullable = false)
    private int tempoDiLettura;
    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;
}
