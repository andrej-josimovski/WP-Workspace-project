package mk.ukim.finki.emt.workspaces.data;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.repository.UserRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceMembershipRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataHolder {
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMembershipRepository workspaceMembershipRepository;

    public DataHolder(UserRepository userRepository, WorkspaceRepository workspaceRepository, WorkspaceMembershipRepository workspaceMembershipRepository) {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.workspaceMembershipRepository = workspaceMembershipRepository;
    }

    @PostConstruct
    public void init() {
        User u1 = new User("Andrej", "password1", "andrej@example.com");
        User u2 = new User("Elena", "password2", "elena@example.com");
        User u3 = new User("Marko", "password3", "marko@example.com");
        User u4 = new User("Iva", "password4", "iva@example.com");
        User u5 = new User("Filip", "password5", "filip@example.com");

        userRepository.saveAll(List.of(u1, u2, u3, u4, u5));

        // Create Workspaces
        Workspace w1 = new Workspace();
        w1.setName("AI Research");
        Workspace w2 = new Workspace();
        w2.setName("UI Design Team");
        Workspace w3 = new Workspace();
        w3.setName("DevOps Tasks");
        Workspace w4 = new Workspace();
        w4.setName("Thesis Drafts");
        Workspace w5 = new Workspace();
        w5.setName("Hackathon Prep");

        workspaceRepository.saveAll(List.of(w1, w2, w3, w4, w5));

        // Create Workspace Memberships with roles
        workspaceMembershipRepository.saveAll(List.of(
                new WorkspaceMembership(u1, w1, Role.OWNER),
                new WorkspaceMembership(u2, w1, Role.MEMBER),
                new WorkspaceMembership(u3, w1, Role.MEMBER),

                new WorkspaceMembership(u2, w2, Role.OWNER),
                new WorkspaceMembership(u1, w2, Role.MEMBER),
                new WorkspaceMembership(u5, w2, Role.MEMBER),

                new WorkspaceMembership(u3, w3, Role.OWNER),
                new WorkspaceMembership(u2, w3, Role.MEMBER),
                new WorkspaceMembership(u4, w3, Role.MEMBER),

                new WorkspaceMembership(u4, w4, Role.OWNER),
                new WorkspaceMembership(u1, w4, Role.MEMBER),
                new WorkspaceMembership(u3, w4, Role.MEMBER),
                new WorkspaceMembership(u5, w4, Role.MEMBER),

                new WorkspaceMembership(u5, w5, Role.OWNER),
                new WorkspaceMembership(u1, w5, Role.MEMBER),
                new WorkspaceMembership(u2, w5, Role.MEMBER),
                new WorkspaceMembership(u4, w5, Role.MEMBER)
        ));
    }
}
