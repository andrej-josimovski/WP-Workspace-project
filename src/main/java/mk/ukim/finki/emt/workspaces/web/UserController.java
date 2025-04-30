package mk.ukim.finki.emt.workspaces.web;

import mk.ukim.finki.emt.workspaces.model.User;
import mk.ukim.finki.emt.workspaces.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return this.userService.findById(id)
                .map(a-> ResponseEntity.ok().body(a))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<User> save(@RequestBody User user){
        return userService.save(user)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return this.userService.update(id, user)
                .map(a->ResponseEntity.ok().body(a))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
