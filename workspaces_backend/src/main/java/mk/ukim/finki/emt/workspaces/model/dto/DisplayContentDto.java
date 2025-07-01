package mk.ukim.finki.emt.workspaces.model.dto;

import mk.ukim.finki.emt.workspaces.model.domain.*;
import mk.ukim.finki.emt.workspaces.model.enumerations.ContentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DisplayContentDto(
        Long id,
        String name,
        ContentType type,
        String description,
        LocalDateTime uploadedAt) {

    public static DisplayContentDto from(Content content) {
        return new DisplayContentDto(
                content.getId(),
                content.getName(),
                content.getType(),
                content.getDescription(),
                content.getUploadedAt());
    }

    public static List<DisplayContentDto> from(List<Content> contents) {
        return contents.stream().map(DisplayContentDto::from).collect(Collectors.toList());
    }
}
