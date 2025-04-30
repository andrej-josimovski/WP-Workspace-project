package mk.ukim.finki.emt.workspaces.web;

import mk.ukim.finki.emt.workspaces.model.Workspace;
import mk.ukim.finki.emt.workspaces.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {

    private final WorkspaceService workspaceService;


    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public List<Workspace> getAll(){
        return workspaceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workspace> findById(@PathVariable Long id){
        return workspaceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Workspace> save(@RequestBody Workspace workspace){
        return workspaceService.save(workspace)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Workspace> update (@PathVariable Long id, @RequestBody Workspace workspace){
        return workspaceService.update(id, workspace)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (workspaceService.findById(id).isPresent()){
            workspaceService.deleteById(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
