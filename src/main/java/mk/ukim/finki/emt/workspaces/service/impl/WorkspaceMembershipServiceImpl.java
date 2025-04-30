package mk.ukim.finki.emt.workspaces.service.impl;

import mk.ukim.finki.emt.workspaces.model.Role;
import mk.ukim.finki.emt.workspaces.model.Workspace;
import mk.ukim.finki.emt.workspaces.model.User;
import mk.ukim.finki.emt.workspaces.model.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.model.exceptions.UserNotFoundException;
import mk.ukim.finki.emt.workspaces.model.exceptions.WorkspaceNotFoundException;
import mk.ukim.finki.emt.workspaces.repository.UserRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceMembershipRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceRepository;
import mk.ukim.finki.emt.workspaces.service.WorkspaceMembershipService;
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
    public Optional<WorkspaceMembership> findById(Long id) {
        return workspaceMembershipRepository.findById(id);
    }

    @Override
    public Optional<WorkspaceMembership> addMember(Long workspaceId, Long memberId) {
        if (workspaceId==null || memberId==null){
            throw new IllegalArgumentException();
        }
        Workspace workspace = this.workspaceRepository.findById(workspaceId).orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        User user= this.userRepository.findById(memberId).orElseThrow(()-> new UserNotFoundException(memberId));

        WorkspaceMembership workspaceMembership = new WorkspaceMembership(user, workspace);
        return Optional.of(this.workspaceMembershipRepository.save(workspaceMembership));
    }

    @Override
    public void removeMember(Long workspaceId, Long memberId) {
        Workspace workspace = this.workspaceRepository.findById(workspaceId).orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        User user= this.userRepository.findById(memberId).orElseThrow(()-> new UserNotFoundException(memberId));

        WorkspaceMembership workspaceMembership = new WorkspaceMembership(user, workspace);
        this.workspaceMembershipRepository.delete(workspaceMembership);
    }

    @Override
    public Optional<WorkspaceMembership> updateRole(Long workspaceId, Long memberId, Role newRole) {
        Workspace workspace = this.workspaceRepository.findById(workspaceId).orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        User user= this.userRepository.findById(memberId).orElseThrow(()-> new UserNotFoundException(memberId));
        WorkspaceMembership workspaceMembership = new WorkspaceMembership(user, workspace);

        workspaceMembership.setWorkspace(workspace);
        workspaceMembership.setUser(user);
        workspaceMembership.setRole(newRole);

        return Optional.of(this.workspaceMembershipRepository.save(workspaceMembership));
    }
}
