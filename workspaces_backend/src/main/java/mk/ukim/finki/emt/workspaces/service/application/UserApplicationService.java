package mk.ukim.finki.emt.workspaces.service.application;

import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.dto.*;

import java.util.List;
import java.util.Optional;

public interface UserApplicationService {
    Optional<DisplayUserDto> update(Long id, CreateUserDto createUserDto);
    Optional<DisplayUserDto> save(CreateUserDto createUserDto);
    void deleteById(Long id);
    Optional<DisplayUserDto> findById(Long id);
    List<DisplayUserDto> findAll();
    Optional<RegisterUserResponseDto> register(RegisterUserRequestDto registerUserRequestDto);

    Optional<LoginUserResponseDto> login(LoginUserRequestDto loginUserRequestDto);

    Optional<RegisterUserResponseDto> findByUsername(String username);

}
