package fragnito.U5W2D4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fragnito.U5W2D4.entities.Author;
import fragnito.U5W2D4.entities.BlogPost;
import fragnito.U5W2D4.exceptions.NotFoundException;
import fragnito.U5W2D4.payloads.BlogPostDTO;
import fragnito.U5W2D4.repositories.BlogPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class BlogPostsService {
    @Autowired
    private BlogPostsRepository blogPostsRepository;

    @Autowired
    private AuthorsService authorsService;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<BlogPost> findBlogPosts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.blogPostsRepository.findAll(pageable);
    }

    public BlogPost getBlogPostById(int postId) {
        return this.blogPostsRepository.findAll().stream().filter(post -> post.getId() == postId).findFirst().orElseThrow(() -> new NotFoundException(postId));
    }

    public BlogPost saveBlogPost(BlogPostDTO body) {
        Author found = this.authorsService.findAuthorById(body.authorId());
        BlogPost newBlogPost = new BlogPost();
        newBlogPost.setCover("https://picsum.photos/200/300");
        newBlogPost.setTitolo(body.titolo());
        newBlogPost.setAuthor(found);
        newBlogPost.setCategoria(body.categoria());
        newBlogPost.setContenuto(body.contenuto());
        newBlogPost.setTempoDiLettura(body.tempoDiLettura());
        this.blogPostsRepository.save(newBlogPost);
        return newBlogPost;
    }

    public BlogPost updateBlogPost(int postId, BlogPostDTO updatedBlogPost) {
        BlogPost found = this.getBlogPostById(postId);
        found.setTitolo(updatedBlogPost.titolo());
        found.setContenuto(updatedBlogPost.contenuto());
        found.setTempoDiLettura(updatedBlogPost.tempoDiLettura());
        found.setCategoria(updatedBlogPost.categoria());
        this.blogPostsRepository.save(found);
        return found;
    }

    public void deleteBlogPost(int postId) {
        BlogPost found = this.getBlogPostById(postId);
        this.blogPostsRepository.delete(found);
    }

    public void uploadCover(int postId, MultipartFile file) throws IOException {
        String url = (String) this.cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        BlogPost blogPost = this.getBlogPostById(postId);
        blogPost.setCover(url);
        this.blogPostsRepository.save(blogPost);
    }
}
