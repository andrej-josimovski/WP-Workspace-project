package mk.ukim.finki.emt.workspaces.service.domain.impl;

import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.repository.UserRepository;
import mk.ukim.finki.emt.workspaces.service.domain.UserService;
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
    public Optional<User> save(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty() || user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException();
        }
        User user1 = new User(user.getName(), user.getPassword(), user.getEmail());
        return Optional.of(this.userRepository.save(user1));
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (user.getName() != null && !user.getName().isEmpty()) {
                        existingUser.setName(user.getName());
                    }
                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        existingUser.setPassword(user.getPassword());
                    }
                    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                        existingUser.setEmail(user.getEmail());
                    }
                    return userRepository.save(existingUser);
                });
    }
}
