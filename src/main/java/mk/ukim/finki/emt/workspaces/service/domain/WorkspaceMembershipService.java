package mk.ukim.finki.emt.workspaces.service.domain;

import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;

import java.util.*;

public interface WorkspaceMembershipService {
    List<WorkspaceMembership> findAll();
    Optional<WorkspaceMembership> addMember(Long workspaceId, Long memberId, Role role);
    void removeMember(Long workspaceId, Long memberId);
    Optional<WorkspaceMembership> updateRole(Long workspaceId, Long memberId, Role newRole);
    List<WorkspaceMembership> findAllByWorkspaceId(Long workspaceId);
    Optional<WorkspaceMembership> findByWorkspaceIdAndUserId(Long workspaceId, Long userId);


}
