package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.User;

public record CreateUserDto(
        String name,
        String password,
        String email
) {
    public User toUser() {
        return new User(name, password, email);
    }
}
