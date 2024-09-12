package fragnito.U5W2D4.controllers;

import fragnito.U5W2D4.entities.BlogPost;
import fragnito.U5W2D4.exceptions.BadRequestException;
import fragnito.U5W2D4.payloads.BlogPostDTO;
import fragnito.U5W2D4.payloads.RespBlogPost;
import fragnito.U5W2D4.services.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blogPosts")
public class BlogPostsController {
    @Autowired
    private BlogPostsService blogPostsService;

    @GetMapping
    public Page<BlogPost> getBlogPosts(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size,
                                       @RequestParam(defaultValue = "titolo") String sortBy) {
        return blogPostsService.findBlogPosts(page, size, sortBy);
    }

    @GetMapping("/{postId}")
    public BlogPost getBlogPostById(@PathVariable int postId) {
        return blogPostsService.getBlogPostById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespBlogPost postBlogPost(@RequestBody @Validated BlogPostDTO body, BindingResult validation) {
        String messages = validation.getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.joining(". "));
        if (validation.hasErrors()) throw new BadRequestException("Ci sono stati errori di validazione: " + messages);
        BlogPost blogPost = blogPostsService.saveBlogPost(body);
        return new RespBlogPost(blogPost.getId());
    }

    @PutMapping("/{postId}")
    public BlogPost putBlogPost(@PathVariable int postId, @RequestBody BlogPostDTO body) {
        return blogPostsService.updateBlogPost(postId, body);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogPost(@PathVariable int postId) {
        blogPostsService.deleteBlogPost(postId);
    }

    @PostMapping("/{postId}/cover")
    public void uploadCover(@PathVariable int postId, @RequestParam("cover") MultipartFile file) throws IOException {
        this.blogPostsService.uploadCover(postId, file);
    }
}
