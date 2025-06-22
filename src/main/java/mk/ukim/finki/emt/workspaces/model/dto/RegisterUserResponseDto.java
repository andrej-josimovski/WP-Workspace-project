package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.User;

public record RegisterUserResponseDto(
        String username,
        String email
) {

    public static RegisterUserResponseDto from(User user) {
        return new RegisterUserResponseDto(
                user.getUsername(),
                user.getEmail()
        );
    }

}