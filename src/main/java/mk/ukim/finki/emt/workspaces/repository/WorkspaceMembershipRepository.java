package mk.ukim.finki.emt.workspaces.repository;

import mk.ukim.finki.emt.workspaces.model.WorkspaceMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceMembershipRepository extends JpaRepository<WorkspaceMembership, Long> {
}
