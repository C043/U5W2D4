package fragnito.U5W2D3.services;

import fragnito.U5W2D3.entities.Author;
import fragnito.U5W2D3.exceptions.BadRequestException;
import fragnito.U5W2D3.exceptions.NotFoundException;
import fragnito.U5W2D3.repositories.AuthorsRepository;
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

    public Author saveAuthor(Author body) {
        if (this.authorsRepository.existsByEmail(body.getEmail())) throw new BadRequestException("Email già in uso.");
        body.setAvatar("https://ui-avatars.com/api/?name=" + body.getNome() + "+" + body.getCognome());
        this.authorsRepository.save(body);
        return body;
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
