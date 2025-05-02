package mk.ukim.finki.emt.workspaces.service.domain.impl;

import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
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
    public Optional<WorkspaceMembership> addMember(Long workspaceId, Long memberId, Role role) {
        if (workspaceId==null || memberId==null || role==null) {
            throw new IllegalArgumentException();
        }
        Workspace workspace = this.workspaceRepository.findById(workspaceId).orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        User user= this.userRepository.findById(memberId).orElseThrow(()-> new UserNotFoundException(memberId));
        boolean alreadyExists= workspaceMembershipRepository.existsByWorkspaceIdAndUserId(workspace.getId(), user.getId());
        if (alreadyExists) {
            throw new IllegalStateException("This user is already member of this workspace");
        }

        WorkspaceMembership workspaceMembership = new WorkspaceMembership(user, workspace, role);
        return Optional.of(this.workspaceMembershipRepository.save(workspaceMembership));
    }

    @Override
    public void removeMember(Long workspaceId, Long memberId) {
        WorkspaceMembership workspaceMembership = workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, memberId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        workspaceMembershipRepository.delete(workspaceMembership);
    }

    @Override
    public Optional<WorkspaceMembership> updateRole(Long workspaceId, Long memberId, Role newRole) {
        WorkspaceMembership workspaceMembership=workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, memberId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        workspaceMembership.setRole(newRole);
        return Optional.of(this.workspaceMembershipRepository.save(workspaceMembership));
    }

    @Override
    public List<WorkspaceMembership> findAllByWorkspaceId(Long workspaceId) {
        return workspaceMembershipRepository.findAllByWorkspaceId(workspaceId);
    }

    @Override
    public Optional<WorkspaceMembership> findByWorkspaceIdAndUserId(Long workspaceId, Long userId) {
        return workspaceMembershipRepository.findByWorkspaceIdAndUserId(workspaceId, userId);
    }
}
