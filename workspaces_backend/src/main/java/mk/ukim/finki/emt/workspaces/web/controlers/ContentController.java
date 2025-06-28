package mk.ukim.finki.emt.workspaces.web.controlers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.emt.workspaces.model.dto.CreateContentDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayContentDto;
import mk.ukim.finki.emt.workspaces.service.application.ContentApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@Tag(name = "Content API", description = "Endpoints for managing content.")
public class ContentController {

    private final ContentApplicationService contentApplicationService;

    public ContentController(ContentApplicationService contentApplicationService) {
        this.contentApplicationService = contentApplicationService;
    }

    @Operation(summary = "Get all contents", description = "Retrieves a list of all contents.")
    @GetMapping
    public List<DisplayContentDto> findAll() {
        return contentApplicationService.findAll();
    }

    @Operation(summary = "Get content by ID", description = "Finds an content by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayContentDto> findById(@PathVariable Long id) {
        return this.contentApplicationService.findById(id)
                .map(a -> ResponseEntity.ok().body(a))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new content", description = "Creates a new content based on the given ContentDto.")
    @PostMapping("/add")
    public ResponseEntity<DisplayContentDto> save(@RequestBody CreateContentDto contentDto) {
        return contentApplicationService.save(contentDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing content", description = "Updates an content by its ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayContentDto> update(@PathVariable Long id, @RequestBody CreateContentDto contentDto) {
        return this.contentApplicationService.update(id, contentDto)
                .map(a -> ResponseEntity.ok().body(a))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a content", description = "Deletes an content by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (contentApplicationService.findById(id).isPresent()) {
            contentApplicationService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
