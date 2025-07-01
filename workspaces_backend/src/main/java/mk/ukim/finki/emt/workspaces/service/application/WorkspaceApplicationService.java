package mk.ukim.finki.emt.workspaces.service.application;

import mk.ukim.finki.emt.workspaces.model.dto.CreateContentDto;
import mk.ukim.finki.emt.workspaces.model.dto.CreateWorkspaceDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayWorkspaceDto;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;

import java.util.List;
import java.util.Optional;

public interface WorkspaceApplicationService {
    List<DisplayWorkspaceDto> findAll();
    Optional<DisplayWorkspaceDto> findById(Long id);
    Optional<DisplayWorkspaceDto> save(CreateWorkspaceDto createWorkspaceDto);
    Optional<DisplayWorkspaceDto> update(Long id, CreateWorkspaceDto createWorkspaceDto, Long requestingUserId) throws AccessDeniedException;
    void deleteById(Long id, Long requestingUserId) throws AccessDeniedException;
    Optional<DisplayWorkspaceDto> addContent(Long workspaceId, CreateContentDto createContentDto) throws AccessDeniedException;
    Optional<DisplayWorkspaceDto> deleteContent(Long workspaceId, Long contentId) throws AccessDeniedException;
}
