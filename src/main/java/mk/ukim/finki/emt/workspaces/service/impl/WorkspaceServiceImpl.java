package mk.ukim.finki.emt.workspaces.service.impl;

import mk.ukim.finki.emt.workspaces.model.Workspace;
import mk.ukim.finki.emt.workspaces.model.exceptions.WorkspaceNotFoundException;
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
    public Optional<Workspace> save(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Workspace workspace = new Workspace(name);
        return Optional.of(this.workspaceRepository.save(workspace));
    }

    @Override
    public Optional<Workspace> update(Long id, String name) {
        if (id == null || name==null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Workspace workspace = this.workspaceRepository.findById(id).orElseThrow(() -> new WorkspaceNotFoundException(id));
        return Optional.of(this.workspaceRepository.save(workspace));
    }
}
