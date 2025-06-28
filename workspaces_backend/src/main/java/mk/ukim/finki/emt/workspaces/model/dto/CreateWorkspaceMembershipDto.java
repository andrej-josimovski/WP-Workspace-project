package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;

public record CreateWorkspaceMembershipDto(User user, Workspace workspace, Role role) {
    public WorkspaceMembership toWorkspaceMembership(){
        return new WorkspaceMembership(user, workspace, role);
    }
}
