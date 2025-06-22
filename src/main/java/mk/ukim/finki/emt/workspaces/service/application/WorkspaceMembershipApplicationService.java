package mk.ukim.finki.emt.workspaces.service.application;

import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.model.dto.CreateUserDto;
import mk.ukim.finki.emt.workspaces.model.dto.CreateWorkspaceMembershipDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayWorkspaceMembershipDto;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMembershipApplicationService {
    List<DisplayWorkspaceMembershipDto> findAll();
    Optional<DisplayWorkspaceMembershipDto> addMember(Long workspaceId, Long memberId, Role role, Long requestUserId) throws AccessDeniedException;
    void removeMember(Long workspaceId, Long memberId, Long requestUserId) throws AccessDeniedException;
    Optional<DisplayWorkspaceMembershipDto> updateRole(Long workspaceId, Long memberId, Role newRole, Long requestUserId) throws AccessDeniedException;
    List<DisplayWorkspaceMembershipDto> findWorkspaceMembershipById(Long workspaceId);
    Optional<DisplayWorkspaceMembershipDto> findByWorkspaceIdAndUserId(Long workspaceId, Long userId);
    Workspace createWorkspace(String workspaceName, Long userId);
}
