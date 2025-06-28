package mk.ukim.finki.emt.workspaces.repository;

import mk.ukim.finki.emt.workspaces.model.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
