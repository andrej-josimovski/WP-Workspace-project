package mk.ukim.finki.emt.workspaces.service.application.impl;

import mk.ukim.finki.emt.workspaces.model.dto.CreateUserDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayUserDto;
import mk.ukim.finki.emt.workspaces.service.application.UserApplicationService;
import mk.ukim.finki.emt.workspaces.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userService;

    public UserApplicationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void deleteById(Long id) {
        userService.deleteById(id);
    }

    @Override
    public Optional<DisplayUserDto> update(Long id, CreateUserDto createUserDto) {
        return userService.update(id, createUserDto.toUser())
                .map(DisplayUserDto::from);
    }

    @Override
    public Optional<DisplayUserDto> save(CreateUserDto createUserDto) {
        return userService.save(createUserDto.toUser())
                .map(DisplayUserDto::from);
    }

    @Override
    public Optional<DisplayUserDto> findById(Long id) {
        return userService.findById(id).map(DisplayUserDto::from);
    }

    @Override
    public List<DisplayUserDto> findAll() {
        return DisplayUserDto.from(userService.findAll());
    }
}
