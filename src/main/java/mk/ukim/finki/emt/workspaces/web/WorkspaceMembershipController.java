package mk.ukim.finki.emt.workspaces.web;

import mk.ukim.finki.emt.workspaces.model.Role;
import mk.ukim.finki.emt.workspaces.model.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.service.UserService;
import mk.ukim.finki.emt.workspaces.service.WorkspaceMembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspace/membership")
public class WorkspaceMembershipController {

    private final WorkspaceMembershipService membershipService;
    private final UserService userService;

    public WorkspaceMembershipController(WorkspaceMembershipService membershipService, UserService userService) {
        this.membershipService = membershipService;
        this.userService = userService;
    }

    @GetMapping
    public List<WorkspaceMembership> getMemberships() {
        return membershipService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceMembership> findById(@PathVariable Long id) {
        return membershipService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<WorkspaceMembership> add(@RequestParam Long workspaceId, @RequestParam Long userId) {
        return membershipService.addMember(workspaceId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<WorkspaceMembership> update(@PathVariable Long id, @RequestParam Long memberId, @RequestParam Role role) {
        return membershipService.updateRole(id, memberId, role)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{workspaceId}/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long workspaceId, @PathVariable Long userId) {
        if (membershipService.findById(workspaceId).isPresent() && userService.findById(userId).isPresent()) {
            membershipService.removeMember(workspaceId, userId);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
