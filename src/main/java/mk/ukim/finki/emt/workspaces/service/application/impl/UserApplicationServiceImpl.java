package mk.ukim.finki.emt.workspaces.service.application.impl;

import mk.ukim.finki.emt.workspaces.helpers.JwtHelper;
import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.dto.*;
import mk.ukim.finki.emt.workspaces.service.application.UserApplicationService;
import mk.ukim.finki.emt.workspaces.service.domain.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userService;
    private final JwtHelper jwtHelper;

    public UserApplicationServiceImpl(UserService userService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
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

    @Override
    public Optional<RegisterUserResponseDto> register(RegisterUserRequestDto registerUserRequestDto) {
        User user = userService.register(registerUserRequestDto.toUser());
        RegisterUserResponseDto displayUserDto = RegisterUserResponseDto.from(user);
        return Optional.of(displayUserDto);
    }

    @Override
    public Optional<LoginUserResponseDto> login(LoginUserRequestDto loginUserRequestDto) {
        User user = userService.login(loginUserRequestDto.username(), loginUserRequestDto.password());
        String token = jwtHelper.generateToken(user);
        return Optional.of(new LoginUserResponseDto(token));
    }

    @Override
    public Optional<RegisterUserResponseDto> findByUsername(String username) {
        return userService
                .findByUsername(username)
                .map(RegisterUserResponseDto::from);
    }


}
