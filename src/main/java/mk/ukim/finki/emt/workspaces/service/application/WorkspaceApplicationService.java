package mk.ukim.finki.emt.workspaces.service.application;

import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.dto.CreateWorkspaceDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayWorkspaceDto;

import java.util.List;
import java.util.Optional;

public interface WorkspaceApplicationService {
    List<DisplayWorkspaceDto> findAll();
    Optional<DisplayWorkspaceDto> findById(Long id);
    Optional<DisplayWorkspaceDto> save(CreateWorkspaceDto createWorkspaceDto);
    Optional<DisplayWorkspaceDto> update(Long id, CreateWorkspaceDto createWorkspaceDto);
    void deleteById(Long id);
}
