package mk.ukim.finki.emt.workspaces.service.impl;

import mk.ukim.finki.emt.workspaces.model.User;
import mk.ukim.finki.emt.workspaces.model.exceptions.UserNotFoundException;
import mk.ukim.finki.emt.workspaces.repository.UserRepository;
import mk.ukim.finki.emt.workspaces.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<User> save(String name, String password, String email) {
        if (name == null || name.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            throw new IllegalArgumentException();
        }
        User user = new User(name, password, email);
        return Optional.of(this.userRepository.save(user));
    }

    @Override
    public Optional<User> update(Long id, String name, String password, String email) {
        if (id == null || name == null || name.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            throw new IllegalArgumentException();
        }
        User user= this.userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        return Optional.of(this.userRepository.save(user));
    }
}
