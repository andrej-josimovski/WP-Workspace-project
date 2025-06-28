package mk.ukim.finki.emt.workspaces.service.domain.impl;

import mk.ukim.finki.emt.workspaces.model.domain.Content;
import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;
import mk.ukim.finki.emt.workspaces.model.exceptions.WorkspaceNotFoundException;
import mk.ukim.finki.emt.workspaces.repository.UserRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceMembershipRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceRepository;
import mk.ukim.finki.emt.workspaces.service.domain.WorkspaceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMembershipRepository workspaceMembershipRepository;
    private final UserRepository userRepository;

    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository, WorkspaceMembershipRepository workspaceMembershipRepository, UserRepository userRepository) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMembershipRepository = workspaceMembershipRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void deleteById(Long workspaceId, Long requestingUserId) throws AccessDeniedException {
        WorkspaceMembership workspaceMembership = workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, requestingUserId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));

        if (workspaceMembership.getRole() != Role.OWNER) {
            throw new AccessDeniedException();
        }
        workspaceRepository.deleteById(workspaceId);
    }

    @Override
    public List<Workspace> findAll() {
        return this.workspaceRepository.findAll();
    }

    @Override
    public Optional<Workspace> findById(Long id) {
        return this.workspaceRepository.findById(id);
    }

    @Override
    public Optional<Workspace> save(Workspace workspace) {
        if (workspace.getName() == null || workspace.getName().isEmpty()) {
            throw new IllegalArgumentException();
        }
        Workspace workspace1 = new Workspace(workspace.getName());
        return Optional.of(this.workspaceRepository.save(workspace1));
    }

    @Override
    public Optional<Workspace> update(Long id, Workspace workspace, Long requestingUserId) throws AccessDeniedException {
        WorkspaceMembership workspaceMembership = workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(id, requestingUserId)
                .orElseThrow(() -> new WorkspaceNotFoundException(id));

        if (workspaceMembership.getRole() != Role.OWNER) {
            throw new AccessDeniedException();
        }
        return workspaceRepository.findById(id)
                .map(existingWorkspace -> {
                    if (workspace.getName() != null) {
                        existingWorkspace.setName(workspace.getName());
                    }
                    return workspaceRepository.save(existingWorkspace);

                });
    }

    public Optional<Workspace> addContentToWorkspace(Long workspaceId, Content content) throws AccessDeniedException {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        boolean isMember=workspaceMembershipRepository
                .findByWorkspaceIdAndUserId(workspaceId, user.getId())
                .isPresent();

        if (!isMember){
            throw new AccessDeniedException();
        }

        workspace.getContents().add(content);
        return Optional.of(workspace);
    }
}


