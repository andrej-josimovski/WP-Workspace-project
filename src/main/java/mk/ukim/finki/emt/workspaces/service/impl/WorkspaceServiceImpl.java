package mk.ukim.finki.emt.workspaces.service.impl;

import mk.ukim.finki.emt.workspaces.model.Workspace;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceRepository;
import mk.ukim.finki.emt.workspaces.service.WorkspaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public void deleteById(Long id) {
        this.workspaceRepository.deleteById(id);
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
    public Optional<Workspace> update(Long id, Workspace workspace) {
        return workspaceRepository.findById(id)
                .map(existingWorkspace -> {
                    if (workspace.getName() != null) {
                        existingWorkspace.setName(workspace.getName());
                    }
                    return workspaceRepository.save(existingWorkspace);

                });
    }
}
