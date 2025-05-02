package mk.ukim.finki.emt.workspaces.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.service.domain.WorkspaceMembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspace/membership")
@Tag(name = "Workspace Membership API", description = "Endpoints for managing workspace memberships.")
public class WorkspaceMembershipController {

    private final WorkspaceMembershipService membershipService;

    public WorkspaceMembershipController(WorkspaceMembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @Operation(summary = "Get all memberships", description = "Retrieves a list of all existing memberships.")
    @GetMapping
    public List<WorkspaceMembership> getMemberships() {
        return membershipService.findAll();
    }

    @Operation(summary = "Get all memberships by workspace ID", description = "Retrieves a list of all existing memberships by the workspace ID.")
    @GetMapping("/{workspaceId}")
    public List<WorkspaceMembership> findByWorkspaceId(@PathVariable Long workspaceId) {
        return membershipService.findAllByWorkspaceId(workspaceId);
    }

    @Operation(summary = "Get user by ID and workspace ID", description = "Finds an user by its ID and the workspace ID.")
    @GetMapping("/{workspaceId}/{userId}")
    public ResponseEntity<WorkspaceMembership> findByUserIdAndWorkspaceId(@PathVariable Long workspaceId, @PathVariable Long userId) {
        return membershipService.findByWorkspaceIdAndUserId(workspaceId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new member in the workspace", description = "Creates a new membership based on the userId, workspaceId, and role.")
    @PostMapping("/add")
    public ResponseEntity<WorkspaceMembership> add(@RequestParam Long workspaceId, @RequestParam Long userId, @RequestParam Role role) {
        return membershipService.addMember(workspaceId, userId, role)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Updates an existing membership", description = "Updates an user's role by its user and workspace ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<WorkspaceMembership> update(@PathVariable Long id, @RequestParam Long memberId, @RequestParam Role role) {
        return membershipService.updateRole(id, memberId, role)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a membership", description = "Deletes a member from the workspace by its IDs.")
    @DeleteMapping("/delete/{workspaceId}/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long workspaceId, @PathVariable Long userId) {
        if (membershipService.findByWorkspaceIdAndUserId(workspaceId, userId).isPresent()) {
            membershipService.removeMember(workspaceId, userId);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
