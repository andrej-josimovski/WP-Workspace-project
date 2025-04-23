package mk.ukim.finki.emt.workspaces.repository;

import mk.ukim.finki.emt.workspaces.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
