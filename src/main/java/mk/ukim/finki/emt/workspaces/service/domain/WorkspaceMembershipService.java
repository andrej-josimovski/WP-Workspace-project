package mk.ukim.finki.emt.workspaces.service.domain;

import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayWorkspaceMembershipDto;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;

import java.util.*;

public interface WorkspaceMembershipService {
    List<WorkspaceMembership> findAll();
    Optional<WorkspaceMembership> addMember(Long workspaceId, Long memberId, Role role, Long requestUserId) throws AccessDeniedException;
    void removeMember(Long workspaceId, Long memberId, Long requestUserId) throws AccessDeniedException;
    Optional<WorkspaceMembership> updateRole(Long workspaceId, Long memberId, Role newRole, Long requestUserId) throws AccessDeniedException;
    List<WorkspaceMembership> findWorkspaceMembershipById(Long workspaceId);
    Optional<WorkspaceMembership> findByWorkspaceIdAndUserId(Long workspaceId, Long userId);
    Workspace createWorkspace(String name, Long ownerId);
}
