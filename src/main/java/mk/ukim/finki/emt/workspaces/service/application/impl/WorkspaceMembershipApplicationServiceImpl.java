package mk.ukim.finki.emt.workspaces.service.application.impl;

import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayWorkspaceMembershipDto;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;
import mk.ukim.finki.emt.workspaces.service.application.WorkspaceMembershipApplicationService;
import mk.ukim.finki.emt.workspaces.service.domain.WorkspaceMembershipService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceMembershipApplicationServiceImpl implements WorkspaceMembershipApplicationService {

    private final WorkspaceMembershipService workspaceMembershipService;

    public WorkspaceMembershipApplicationServiceImpl(WorkspaceMembershipService workspaceMembershipService) {
        this.workspaceMembershipService = workspaceMembershipService;
    }

    @Override
    public List<DisplayWorkspaceMembershipDto> findAll() {
        return DisplayWorkspaceMembershipDto.from(workspaceMembershipService.findAll());
    }

    @Override
    public Optional<DisplayWorkspaceMembershipDto> addMember(Long workspaceId, Long memberId, Role role, Long requestUserId) throws AccessDeniedException {
        return workspaceMembershipService.addMember(workspaceId, memberId, role, requestUserId)
                .map(DisplayWorkspaceMembershipDto::from);
    }

    @Override
    public void removeMember(Long workspaceId, Long memberId, Long requestUserId) throws AccessDeniedException {
        workspaceMembershipService.removeMember(workspaceId, memberId, requestUserId);
    }


    @Override
    public Optional<DisplayWorkspaceMembershipDto> updateRole(Long workspaceId, Long memberId, Role newRole, Long requestUserId) throws AccessDeniedException {
        return workspaceMembershipService.updateRole(workspaceId, memberId, newRole, requestUserId)
                .map(DisplayWorkspaceMembershipDto::from);
    }

    @Override
    public List<DisplayWorkspaceMembershipDto> findWorkspaceMembershipById(Long workspaceId) {
        return DisplayWorkspaceMembershipDto.from(workspaceMembershipService.findWorkspaceMembershipById(workspaceId));
    }

    @Override
    public Optional<DisplayWorkspaceMembershipDto> findByWorkspaceIdAndUserId(Long workspaceId, Long userId) {
        return workspaceMembershipService.findByWorkspaceIdAndUserId(workspaceId, userId)
                .map(DisplayWorkspaceMembershipDto::from);
    }

    @Override
    public Workspace createWorkspace(String workspaceName, Long userId) {
        return workspaceMembershipService.createWorkspace(workspaceName, userId);
    }


}
