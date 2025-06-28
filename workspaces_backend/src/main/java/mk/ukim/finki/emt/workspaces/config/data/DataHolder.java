package mk.ukim.finki.emt.workspaces.config.data;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.emt.workspaces.model.domain.Content;
import mk.ukim.finki.emt.workspaces.model.enumerations.ContentType;
import mk.ukim.finki.emt.workspaces.model.enumerations.Role;
import mk.ukim.finki.emt.workspaces.model.domain.User;
import mk.ukim.finki.emt.workspaces.model.domain.Workspace;
import mk.ukim.finki.emt.workspaces.model.domain.WorkspaceMembership;
import mk.ukim.finki.emt.workspaces.repository.ContentRepository;
import mk.ukim.finki.emt.workspaces.repository.UserRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceMembershipRepository;
import mk.ukim.finki.emt.workspaces.repository.WorkspaceRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMembershipRepository workspaceMembershipRepository;
    private final PasswordEncoder passwordEncoder;
    private final ContentRepository contentRepository;

    public DataHolder(UserRepository userRepository,
                      WorkspaceRepository workspaceRepository,
                      WorkspaceMembershipRepository workspaceMembershipRepository,
                      PasswordEncoder passwordEncoder,
                      ContentRepository contentRepository) {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.workspaceMembershipRepository = workspaceMembershipRepository;
        this.passwordEncoder = passwordEncoder;
        this.contentRepository = contentRepository;
    }

    @PostConstruct
    public void init() {
        // Create Users
        User u1 = new User("Andrej", passwordEncoder.encode("password1"), "andrej@example.com");
        User u2 = new User("Elena", passwordEncoder.encode("password2"), "elena@example.com");
        User u3 = new User("Marko", passwordEncoder.encode("password3"), "marko@example.com");
        User u4 = new User("Iva", passwordEncoder.encode("password4"), "iva@example.com");
        User u5 = new User("Filip", passwordEncoder.encode("password5"), "filip@example.com");

        userRepository.saveAll(List.of(u1, u2, u3, u4, u5));

        // Create Workspaces
        Workspace w1 = new Workspace("AI Research", new ArrayList<>());
        Workspace w2 = new Workspace("UI Design Team", new ArrayList<>());
        Workspace w3 = new Workspace("DevOps Tasks", new ArrayList<>());
        Workspace w4 = new Workspace("Thesis Drafts", new ArrayList<>());
        Workspace w5 = new Workspace("Hackathon Prep", new ArrayList<>());

        // Create Contents with Workspace reference
        Content c1 = new Content("AI paper draft","NeuralNets.pdf",ContentType.PDF,   LocalDateTime.now().minusDays(3));
        Content c2 = new Content("UI sketch","homepage_mockup.png", ContentType.PNG,  LocalDateTime.now().minusDays(2));
        Content c3 = new Content("Deployment checklist", "devops_notes.txt", ContentType.PNG, LocalDateTime.now().minusDays(1));
        Content c4 = new Content("Thesis overview", "thesis_outline.docx", ContentType.TEXT, LocalDateTime.now());
        Content c5 = new Content("Hackathon promo", "poster.jpg", ContentType.ZIP, LocalDateTime.now().minusHours(12));

        // Add contents to workspaces
        w1.getContents().add(c1);
        w2.getContents().add(c2);
        w3.getContents().add(c3);
        w4.getContents().add(c4);
        w5.getContents().add(c5);

        // Save workspaces first to assign IDs
        workspaceRepository.saveAll(List.of(w1, w2, w3, w4, w5));

        // Save contents after assigning workspace & user
        contentRepository.saveAll(List.of(c1, c2, c3, c4, c5));

        // Create and link WorkspaceMemberships
        List<WorkspaceMembership> memberships = List.of(
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
        );

        // Add memberships to each workspace
        for (WorkspaceMembership m : memberships) {
            m.getWorkspace().getMemberships().add(m);
        }

        workspaceMembershipRepository.saveAll(memberships);
        workspaceRepository.saveAll(List.of(w1, w2, w3, w4, w5));
    }
}