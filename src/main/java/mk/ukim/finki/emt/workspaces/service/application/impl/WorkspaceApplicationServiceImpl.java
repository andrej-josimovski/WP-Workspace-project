package mk.ukim.finki.emt.workspaces.service.application.impl;

import mk.ukim.finki.emt.workspaces.model.dto.CreateWorkspaceDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayWorkspaceDto;
import mk.ukim.finki.emt.workspaces.service.application.WorkspaceApplicationService;
import mk.ukim.finki.emt.workspaces.service.domain.WorkspaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceApplicationServiceImpl implements WorkspaceApplicationService {

    private final WorkspaceService workspaceService;

    public WorkspaceApplicationServiceImpl(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @Override
    public void deleteById(Long id) {
        workspaceService.deleteById(id);
    }

    @Override
    public List<DisplayWorkspaceDto> findAll() {
        return DisplayWorkspaceDto.from(workspaceService.findAll());
    }

    @Override
    public Optional<DisplayWorkspaceDto> findById(Long id) {
        return workspaceService.findById(id).map(DisplayWorkspaceDto::from);
    }

    @Override
    public Optional<DisplayWorkspaceDto> save(CreateWorkspaceDto createWorkspaceDto) {
        return workspaceService.save(createWorkspaceDto.toWorkspace())
                .map(DisplayWorkspaceDto::from);
    }

    @Override
    public Optional<DisplayWorkspaceDto> update(Long id, CreateWorkspaceDto createWorkspaceDto) {
        return workspaceService.update(id, createWorkspaceDto.toWorkspace())
                .map(DisplayWorkspaceDto::from);
    }
}
