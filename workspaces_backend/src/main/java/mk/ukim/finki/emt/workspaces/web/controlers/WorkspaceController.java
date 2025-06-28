package mk.ukim.finki.emt.workspaces.web.controlers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.dto.CreateContentDto;
import mk.ukim.finki.emt.workspaces.model.dto.CreateWorkspaceDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayWorkspaceDto;
import mk.ukim.finki.emt.workspaces.model.exceptions.AccessDeniedException;
import mk.ukim.finki.emt.workspaces.service.application.WorkspaceApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspace")
@Tag(name = "Workspace API", description = "Endpoints for managing workspaces.")
public class WorkspaceController {

    private final WorkspaceApplicationService workspaceApplicationService;


    public WorkspaceController(WorkspaceApplicationService workspaceApplicationService) {
        this.workspaceApplicationService = workspaceApplicationService;
    }

    @Operation(summary = "Get all workspaces", description = "Retrieves a list of all available workspaces.")
    @GetMapping
    public List<DisplayWorkspaceDto> findAll() {
        return workspaceApplicationService.findAll();
    }

    @Operation(summary = "Get workspace by ID", description = "Finds a workspace by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayWorkspaceDto> findById(@PathVariable Long id) {
        return workspaceApplicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new workspace", description = "Creates a new workspace based on the given WorkspaceDto.")
    @PostMapping("/add")
    public ResponseEntity<DisplayWorkspaceDto> save(@RequestBody CreateWorkspaceDto workspaceDto) {
        return workspaceApplicationService.save(workspaceDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add-content/{workspaceId}")
    public ResponseEntity<DisplayWorkspaceDto> addContent(@PathVariable Long workspaceId, @RequestBody CreateContentDto createContentDto) throws AccessDeniedException {
        return workspaceApplicationService.addContent(workspaceId, createContentDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Operation(summary = "Updates an existing workspace", description = "Updates a workspace by its ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayWorkspaceDto> update(@PathVariable Long id, @RequestBody CreateWorkspaceDto workspaceDto, @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        return workspaceApplicationService.update(id, workspaceDto, currentUser.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a workspace", description = "Deletes a workspace by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        if (workspaceApplicationService.findById(id).isPresent()) {
            workspaceApplicationService.deleteById(id, currentUser.getId());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
