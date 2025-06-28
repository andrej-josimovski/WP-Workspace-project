package mk.ukim.finki.emt.workspaces.service.application;

import mk.ukim.finki.emt.workspaces.model.domain.Content;
import mk.ukim.finki.emt.workspaces.model.dto.CreateContentDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayContentDto;

import java.util.List;
import java.util.Optional;

public interface ContentApplicationService {
    List<DisplayContentDto> findAll();
    Optional<DisplayContentDto> findById(Long id);
    Optional<DisplayContentDto> save(CreateContentDto createContentDto);
    Optional<DisplayContentDto> update(Long id, CreateContentDto createContentDto);
    void deleteById(Long id);
}
