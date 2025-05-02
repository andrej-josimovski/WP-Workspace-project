package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.Workspace;

public record CreateWorkspaceDto (String name){
    public Workspace toWorkspace(){
        return new Workspace(name);
    }

}
