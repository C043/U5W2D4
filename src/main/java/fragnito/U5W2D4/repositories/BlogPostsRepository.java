package fragnito.U5W2D4.repositories;

import fragnito.U5W2D4.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostsRepository extends JpaRepository<BlogPost, Integer> {
}
