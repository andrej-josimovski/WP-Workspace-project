package mk.ukim.finki.emt.workspaces.service;

import mk.ukim.finki.emt.workspaces.model.Workspace;

import java.util.List;
import java.util.Optional;

public interface WorkspaceService {
    List<Workspace> findAll();
    Optional<Workspace> findById(Long id);
    Optional<Workspace> save(Workspace workspace);
    Optional<Workspace> update(Long id, Workspace workspace);
    void deleteById(Long id);
}
