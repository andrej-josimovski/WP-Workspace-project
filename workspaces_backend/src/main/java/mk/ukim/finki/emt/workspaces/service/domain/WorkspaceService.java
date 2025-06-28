package mk.ukim.finki.emt.workspaces.service.domain;

import mk.ukim.finki.emt.workspaces.model.domain.Content;
import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.dto.CreateContentDto;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;

import java.util.List;
import java.util.Optional;

public interface WorkspaceService {
    List<Workspace> findAll();
    Optional<Workspace> findById(Long id);
    Optional<Workspace> save(Workspace workspace);
    Optional<Workspace> update(Long id, Workspace workspace, Long requestingUserId) throws AccessDeniedException;
    void deleteById(Long id, Long requestingUserId) throws AccessDeniedException;
    Optional<Workspace> addContentToWorkspace(Long workspaceId, Content content) throws AccessDeniedException;
}
