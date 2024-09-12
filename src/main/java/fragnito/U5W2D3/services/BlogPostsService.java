package fragnito.U5W2D3.services;

import fragnito.U5W2D3.entities.Author;
import fragnito.U5W2D3.entities.BlogPost;
import fragnito.U5W2D3.exceptions.NotFoundException;
import fragnito.U5W2D3.payloads.BlogPostPayload;
import fragnito.U5W2D3.repositories.BlogPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BlogPostsService {
    @Autowired
    private BlogPostsRepository blogPostsRepository;

    @Autowired
    private AuthorsService authorsService;

    public Page<BlogPost> findBlogPosts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.blogPostsRepository.findAll(pageable);
    }

    public BlogPost getBlogPostById(int postId) {
        return this.blogPostsRepository.findAll().stream().filter(post -> post.getId() == postId).findFirst().orElseThrow(() -> new NotFoundException(postId));
    }

    public BlogPost saveBlogPost(BlogPostPayload body) {
        Author found = this.authorsService.findAuthorById(body.getAuthorId());
        BlogPost newBlogPost = new BlogPost();
        newBlogPost.setCover("https://picsum.photos/200/300");
        newBlogPost.setTitolo(body.getTitolo());
        newBlogPost.setAuthor(found);
        newBlogPost.setCategoria(body.getCategoria());
        newBlogPost.setContenuto(body.getContenuto());
        newBlogPost.setTempoDiLettura(body.getTempoDiLettura());
        this.blogPostsRepository.save(newBlogPost);
        return newBlogPost;
    }

    public BlogPost updateBlogPost(int postId, BlogPost updatedBlogPost) {
        BlogPost found = this.getBlogPostById(postId);
        found.setCover(updatedBlogPost.getCover());
        found.setTitolo(updatedBlogPost.getTitolo());
        found.setContenuto(updatedBlogPost.getContenuto());
        found.setTempoDiLettura(updatedBlogPost.getTempoDiLettura());
        found.setCategoria(updatedBlogPost.getCategoria());
        this.blogPostsRepository.save(found);
        return found;
    }

    public void deleteBlogPost(int postId) {
        BlogPost found = this.getBlogPostById(postId);
        this.blogPostsRepository.delete(found);
    }
}
