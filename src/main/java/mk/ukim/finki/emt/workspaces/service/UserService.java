package mk.ukim.finki.emt.workspaces.service;

import mk.ukim.finki.emt.workspaces.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> save(String name, String password, String email);
    Optional<User> update(Long id, String name, String password, String email);
    void deleteById(Long id);

}
