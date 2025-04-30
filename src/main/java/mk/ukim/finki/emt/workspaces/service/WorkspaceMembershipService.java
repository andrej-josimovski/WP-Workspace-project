package mk.ukim.finki.emt.workspaces.service;

import mk.ukim.finki.emt.workspaces.model.Role;
import mk.ukim.finki.emt.workspaces.model.WorkspaceMembership;

import java.util.*;

public interface WorkspaceMembershipService {
    List<WorkspaceMembership> findAll();
    Optional<WorkspaceMembership> findById(Long id);
    Optional<WorkspaceMembership> addMember(Long workspaceId, Long memberId);
    void removeMember(Long workspaceId, Long memberId);
    Optional<WorkspaceMembership> updateRole(Long workspaceId, Long memberId, Role newRole);

}
