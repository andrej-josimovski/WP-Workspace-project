package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayWorkspaceMembershipDto(
        Long id,
        Long userId,
        String userEmail,
        Long workspaceId,
        String workspaceName,
        String role
) {
    public static DisplayWorkspaceMembershipDto from(WorkspaceMembership membership) {
        return new DisplayWorkspaceMembershipDto(
                membership.getId(),
                membership.getUser().getId(),
                membership.getUser().getEmail(),
                membership.getWorkspace().getId(),
                membership.getWorkspace().getName(),
                membership.getRole().name()
        );
    }
    public static List<DisplayWorkspaceMembershipDto> from(List<WorkspaceMembership> workspaceMemberships) {
        return workspaceMemberships.stream().map(DisplayWorkspaceMembershipDto::from).collect(Collectors.toList());
    }
}

