package mk.ukim.finki.emt.workspaces.repository;

import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceMembershipRepository extends JpaRepository<WorkspaceMembership, Long> {
    List<WorkspaceMembership> findWorkspaceMembershipById(Long id);
    Optional<WorkspaceMembership> findByWorkspaceIdAndUserId(Long workspaceId, Long userId);
    boolean existsByWorkspaceIdAndUserId(Long workspaceId, Long userId);

    Optional<Object> findByWorkspaceIdAndUserUsername(Long workspaceId, String username);
}
