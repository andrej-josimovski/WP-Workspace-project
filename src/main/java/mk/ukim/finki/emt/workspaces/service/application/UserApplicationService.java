package mk.ukim.finki.emt.workspaces.service.application;

import mk.ukim.finki.emt.workspaces.model.dto.CreateUserDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayUserDto;

import java.util.List;
import java.util.Optional;

public interface UserApplicationService {
    Optional<DisplayUserDto> update(Long id, CreateUserDto createUserDto);
    Optional<DisplayUserDto> save(CreateUserDto createUserDto);
    void deleteById(Long id);
    Optional<DisplayUserDto> findById(Long id);
    List<DisplayUserDto> findAll();
}
