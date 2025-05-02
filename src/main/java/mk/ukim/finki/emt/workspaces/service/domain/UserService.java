package mk.ukim.finki.emt.workspaces.service.domain;

import mk.ukim.finki.emt.workspaces.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> save(User user);
    Optional<User> update(Long id, User user);
    void deleteById(Long id);

}
