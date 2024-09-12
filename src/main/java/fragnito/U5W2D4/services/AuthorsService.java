package fragnito.U5W2D4.services;

import fragnito.U5W2D4.entities.Author;
import fragnito.U5W2D4.exceptions.BadRequestException;
import fragnito.U5W2D4.exceptions.NotFoundException;
import fragnito.U5W2D4.payloads.AuthorDTO;
import fragnito.U5W2D4.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepository;

    public List<Author> findAllAuthors() {
        return this.authorsRepository.findAll();
    }

    public Author findAuthorById(int authorId) {
        return this.authorsRepository.findAll().stream().filter(author -> author.getId() == authorId).findFirst().orElseThrow(() -> new NotFoundException(authorId));
    }

    public Author saveAuthor(AuthorDTO body) {
        if (this.authorsRepository.existsByEmail(body.email())) throw new BadRequestException("Email già in uso.");
        Author author = new Author(body.nome(), body.cognome(), body.email(), body.dataDiNascita(),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        this.authorsRepository.save(author);
        return author;
    }

    public Author updateAuthor(int authorId, Author updatedAuthor) {
        Author found = this.findAuthorById(authorId);
        found.setAvatar("https://ui-avatars.com/api/?name=" + updatedAuthor.getNome() + "+" + updatedAuthor.getCognome());
        found.setNome(updatedAuthor.getNome());
        found.setCognome(updatedAuthor.getCognome());
        found.setEmail(updatedAuthor.getEmail());
        found.setDataDiNascita(updatedAuthor.getDataDiNascita());
        return found;
    }

    public void deleteAuthor(int authorId) {
        this.authorsRepository.delete(this.findAuthorById(authorId));
    }
}
