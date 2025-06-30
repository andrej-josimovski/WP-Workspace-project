package mk.ukim.finki.emt.workspaces.web.controlers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayWorkspaceMembershipDto;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;
import mk.ukim.finki.emt.workspaces.service.application.WorkspaceMembershipApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspace/membership")
@Tag(name = "Workspace Membership API", description = "Endpoints for managing workspace memberships.")
public class WorkspaceMembershipController {

    private final WorkspaceMembershipApplicationService workspaceMembershipApplicationService;

    public WorkspaceMembershipController(WorkspaceMembershipApplicationService workspaceMembershipApplicationService) {
        this.workspaceMembershipApplicationService = workspaceMembershipApplicationService;
    }

    @Operation(summary = "Get all memberships", description = "Retrieves a list of all existing memberships.")
    @GetMapping
    public List<DisplayWorkspaceMembershipDto> getMemberships() {
        return workspaceMembershipApplicationService.findAll();
    }

    @Operation(summary = "Get all memberships by workspace ID", description = "Retrieves a list of all existing memberships by the workspace ID.")
    @GetMapping("/{workspaceId}")
    public List<DisplayWorkspaceMembershipDto> findByWorkspaceId(@PathVariable Long workspaceId) {
        return workspaceMembershipApplicationService.findWorkspaceMembershipById(workspaceId);
    }

    @Operation(summary = "Get user by ID and workspace ID", description = "Finds an user by its ID and the workspace ID.")
    @GetMapping("/{workspaceId}/{userId}")
    public ResponseEntity<DisplayWorkspaceMembershipDto> findByUserIdAndWorkspaceId(@PathVariable Long workspaceId, @PathVariable Long userId) {
        return workspaceMembershipApplicationService.findByWorkspaceIdAndUserId(workspaceId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new member in the workspace", description = "Creates a new membership based on the userId, workspaceId, and role.")
    @PostMapping("/add")
    public ResponseEntity<DisplayWorkspaceMembershipDto> addMember(@RequestParam Long workspaceId,
                                                                   @RequestParam Long userId,
                                                                   @RequestParam Role role,
                                                                   @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        return workspaceMembershipApplicationService.addMember(workspaceId, userId, role, currentUser.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Workspace> createWorkspace(@RequestParam String name,
                                                     @AuthenticationPrincipal User ownerId) {
        System.out.println("Authenticated user" + ownerId);
        Workspace workspace = workspaceMembershipApplicationService.createWorkspace(name, ownerId.getId());
        return ResponseEntity.ok(workspace);
    }

    @Operation(summary = "Updates an existing membership", description = "Updates an user's role by its user and workspace ID.")
    @PutMapping("/edit/{workspaceId}/{memberId}")
    public ResponseEntity<DisplayWorkspaceMembershipDto> update(@PathVariable Long workspaceId, @PathVariable Long memberId, @RequestParam Role role, @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        return workspaceMembershipApplicationService.updateRole(workspaceId, memberId, role, currentUser.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a membership", description = "Deletes a member from the workspace by its IDs.")
    @DeleteMapping("/delete/{workspaceId}/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long workspaceId, @PathVariable Long userId, @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        if (workspaceMembershipApplicationService.findByWorkspaceIdAndUserId(workspaceId, userId).isPresent()) {
            workspaceMembershipApplicationService.removeMember(workspaceId, userId, currentUser.getId());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
