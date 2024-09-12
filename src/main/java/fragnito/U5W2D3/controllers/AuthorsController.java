package fragnito.U5W2D3.controllers;

import fragnito.U5W2D3.entities.Author;
import fragnito.U5W2D3.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    private AuthorsService authorsService;

    @GetMapping
    public List<Author> getAuthors() {
        return authorsService.findAllAuthors();
    }

    @GetMapping("/{authorId}")
    public Author getAuthorById(@PathVariable int authorId) {
        return authorsService.findAuthorById(authorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author saveAuthor(@RequestBody Author body) {
        return authorsService.saveAuthor(body);
    }

    @PutMapping("/{authorId}")
    public Author putAuthor(@PathVariable int authorId, @RequestBody Author body) {
        return authorsService.updateAuthor(authorId, body);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable int authorId) {
        authorsService.deleteAuthor(authorId);
    }
}