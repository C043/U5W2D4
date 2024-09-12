package fragnito.U5W2D3.controllers;

import fragnito.U5W2D3.entities.BlogPost;
import fragnito.U5W2D3.payloads.BlogPostPayload;
import fragnito.U5W2D3.services.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public BlogPost postBlogPost(@RequestBody BlogPostPayload body) {
        return blogPostsService.saveBlogPost(body);
    }

    @PutMapping("/{postId}")
    public BlogPost putBlogPost(@PathVariable int postId, @RequestBody BlogPost body) {
        return blogPostsService.updateBlogPost(postId, body);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogPost(@PathVariable int postId) {
        blogPostsService.deleteBlogPost(postId);
    }
}
