package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.Workspace;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayWorkspaceDto(Long id, String name, List<DisplayContentDto> contents) {
    public static DisplayWorkspaceDto from(Workspace workspace) {
        return new DisplayWorkspaceDto(workspace.getId(), workspace.getName(), workspace.getContents().stream()
                .map(DisplayContentDto::from)
                .collect(Collectors.toList()));
    }

    public static List<DisplayWorkspaceDto> from(List<Workspace> workspaces) {
        return workspaces.stream().map(DisplayWorkspaceDto::from).collect(Collectors.toList());
    }
}
