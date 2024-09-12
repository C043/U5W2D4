package fragnito.U5W2D4.controllers;

import fragnito.U5W2D4.entities.Author;
import fragnito.U5W2D4.exceptions.BadRequestException;
import fragnito.U5W2D4.payloads.AuthorDTO;
import fragnito.U5W2D4.payloads.RespAuthorDTO;
import fragnito.U5W2D4.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    public RespAuthorDTO saveAuthor(@RequestBody @Validated AuthorDTO body, BindingResult validation) {
        String messages = validation.getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.joining(". "));
        if (validation.hasErrors()) throw new BadRequestException("Ci sono stati errori di validazione: " + messages);
        Author author = authorsService.saveAuthor(body);
        return new RespAuthorDTO(author.getId());
    }

    @PutMapping("/{authorId}")
    public Author putAuthor(@PathVariable int authorId, @RequestBody AuthorDTO body) {
        return authorsService.updateAuthor(authorId, body);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable int authorId) {
        authorsService.deleteAuthor(authorId);
    }

    @PostMapping("/{authorId}/avatar")
    public void uploadAvatar(@PathVariable int authorId, @RequestParam("avatar") MultipartFile img) throws IOException {
        this.authorsService.uploadAvatar(authorId, img);
    }
}