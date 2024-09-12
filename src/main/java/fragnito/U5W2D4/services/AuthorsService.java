package fragnito.U5W2D4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fragnito.U5W2D4.entities.Author;
import fragnito.U5W2D4.exceptions.BadRequestException;
import fragnito.U5W2D4.exceptions.NotFoundException;
import fragnito.U5W2D4.payloads.AuthorDTO;
import fragnito.U5W2D4.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

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

    public Author updateAuthor(int authorId, AuthorDTO updatedAuthor) {
        Author found = this.findAuthorById(authorId);
        found.setNome(updatedAuthor.nome());
        found.setCognome(updatedAuthor.cognome());
        found.setEmail(updatedAuthor.email());
        found.setDataDiNascita(updatedAuthor.dataDiNascita());
        return found;
    }

    public void deleteAuthor(int authorId) {
        this.authorsRepository.delete(this.findAuthorById(authorId));
    }

    public void uploadAvatar(int authorId, MultipartFile file) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        Author author = this.findAuthorById(authorId);
        author.setAvatar(url);
        this.authorsRepository.save(author);
    }
}
