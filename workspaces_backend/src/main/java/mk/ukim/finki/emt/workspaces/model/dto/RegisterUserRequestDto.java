package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.User;

public record RegisterUserRequestDto(
        String username,
        String password,
        String email
) {

    public User toUser() {
        return new User(username, password, email);
    }

}
