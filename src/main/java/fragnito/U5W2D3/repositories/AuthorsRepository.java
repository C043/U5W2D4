package fragnito.U5W2D3.repositories;

import fragnito.U5W2D3.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {
    boolean existsByEmail(String email);
}
