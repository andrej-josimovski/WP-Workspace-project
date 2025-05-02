package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayUserDto(String name, String password, String email) {
    public static DisplayUserDto from(User user) {
        return new DisplayUserDto(user.getName(), user.getPassword(), user.getEmail());
    }
    public static List<DisplayUserDto> from(List<User> users) {
        return users.stream().map(DisplayUserDto::from).collect(Collectors.toList());
    }
}
