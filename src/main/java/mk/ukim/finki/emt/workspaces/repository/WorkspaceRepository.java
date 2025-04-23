package mk.ukim.finki.emt.workspaces.repository;

import mk.ukim.finki.emt.workspaces.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}
