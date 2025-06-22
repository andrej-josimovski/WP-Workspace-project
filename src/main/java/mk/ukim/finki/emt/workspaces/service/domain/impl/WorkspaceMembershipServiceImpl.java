package mk.ukim.finki.emt.workspaces.service.domain.impl;

import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;
import mk.ukim.finki.emt.workspaces.model.exceptions.UserNotFoundException;
import mk.ukim.finki.emt.workspaces.model.exceptions.WorkspaceNotFoundException;
import mk.ukim.finki.emt.workspaces.repository.UserRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceMembershipRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceRepository;
import mk.ukim.finki.emt.workspaces.service.domain.WorkspaceMembershipService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceMembershipServiceImpl implements WorkspaceMembershipService {

    private final WorkspaceMembershipRepository workspaceMembershipRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public WorkspaceMembershipServiceImpl(WorkspaceMembershipRepository workspaceMembershipRepository, WorkspaceRepository workspaceRepository, UserRepository userRepository) {
        this.workspaceMembershipRepository = workspaceMembershipRepository;
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<WorkspaceMembership> findAll() {
        return workspaceMembershipRepository.findAll();
    }

    @Override
    public Optional<WorkspaceMembership> addMember(Long workspaceId, Long memberId, Role role, Long requestUserId) throws AccessDeniedException {
        if (workspaceId == null || memberId == null || role == null) {
            throw new IllegalArgumentException("Workspace ID, Member ID and Role must not be null.");
        }

        WorkspaceMembership actorMembership = workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, requestUserId)
                .orElseThrow(AccessDeniedException::new);

        if (actorMembership.getRole() != Role.OWNER){
            throw new AccessDeniedException();
        }

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));

        Optional<WorkspaceMembership> existingMembershipOpt =
                workspaceMembershipRepository.findByWorkspaceIdAndUserId(workspaceId, memberId);

        if (existingMembershipOpt.isPresent()) {
            Role existingRole = existingMembershipOpt.get().getRole();
            throw new IllegalArgumentException(String.format(
                    "User with ID %d already has role %s in workspace %d",
                    memberId, existingRole, workspaceId
            ));
        }

        WorkspaceMembership membership = new WorkspaceMembership();
        membership.setUser(user);
        membership.setWorkspace(workspace);
        membership.setRole(role);

        return Optional.of(workspaceMembershipRepository.save(membership));
    }


    @Override
    public void removeMember(Long workspaceId, Long memberId, Long requestUserId) throws AccessDeniedException {
        WorkspaceMembership actorMembership = workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, requestUserId)
                .orElseThrow(AccessDeniedException::new);

        if (actorMembership.getRole() != Role.OWNER){
            throw new AccessDeniedException();
        }

        WorkspaceMembership workspaceMembership = workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, memberId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        workspaceMembershipRepository.delete(workspaceMembership);
    }

    @Override
    public Optional<WorkspaceMembership> updateRole(Long workspaceId, Long memberId, Role newRole, Long requestUserId) throws AccessDeniedException {
        WorkspaceMembership actorMembership = workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, requestUserId)
                .orElseThrow(AccessDeniedException::new);

        if (actorMembership.getRole() != Role.OWNER) {
            throw new AccessDeniedException();
        }

        // Сега ја менуваш улогата на друг член
        WorkspaceMembership targetMembership = workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, memberId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));

        targetMembership.setRole(newRole);
        return Optional.of(workspaceMembershipRepository.save(targetMembership));
    }

    @Override
    public List<WorkspaceMembership> findWorkspaceMembershipById(Long workspaceId) {
        return workspaceMembershipRepository.findWorkspaceMembershipById(workspaceId);
    }

    @Override
    public Optional<WorkspaceMembership> findByWorkspaceIdAndUserId(Long workspaceId, Long userId) {
        return workspaceMembershipRepository.findByWorkspaceIdAndUserId(workspaceId, userId);
    }

    @Override
    public Workspace createWorkspace(String name, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException(ownerId));

        Workspace workspace = new Workspace();
        workspace.setName(name);
        Workspace savedWorkspace = workspaceRepository.save(workspace);

        WorkspaceMembership membership = new WorkspaceMembership();
        membership.setUser(owner);
        membership.setWorkspace(savedWorkspace);
        membership.setRole(Role.OWNER);
        workspaceMembershipRepository.save(membership);

        return savedWorkspace;
    }
}
