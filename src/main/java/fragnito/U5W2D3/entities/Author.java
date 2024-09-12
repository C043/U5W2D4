package fragnito.U5W2D3.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private String email;
    @Column(name = "data_di_nascita", nullable = false)
    private String dataDiNascita;
    @Column(nullable = false)
    private String avatar;
}
