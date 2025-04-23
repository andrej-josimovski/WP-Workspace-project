package mk.ukim.finki.emt.workspaces.service;

import mk.ukim.finki.emt.workspaces.model.Workspace;

import java.util.List;
import java.util.Optional;

public interface WorkspaceService {
    List<Workspace> findAll();
    Optional<Workspace> findById(Long id);
    Optional<Workspace> save(String name);
    Optional<Workspace> update(Long id, String name);
    void deleteById(Long id);
}
