package mk.ukim.finki.emt.workspaces.model;

import jakarta.persistence.*;

@Entity
public class WorkspaceMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Workspace workspace;
    @Enumerated(EnumType.STRING)
    private Role role;

    public WorkspaceMembership(){}

    public WorkspaceMembership(User user, Workspace workspace, Role role) {
        this.user = user;
        this.workspace = workspace;
        this.role = role;
    }
}
