package mk.ukim.finki.emt.workspaces.service.application.impl;

import mk.ukim.finki.emt.workspaces.model.dto.CreateContentDto;
import mk.ukim.finki.emt.workspaces.model.dto.DisplayContentDto;
import mk.ukim.finki.emt.workspaces.service.application.ContentApplicationService;
import mk.ukim.finki.emt.workspaces.service.domain.ContentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentApplicationServiceImpl implements ContentApplicationService {

    private final ContentService contentService;

    public ContentApplicationServiceImpl(ContentService contentService) {
        this.contentService = contentService;
    }

    @Override
    public List<DisplayContentDto> findAll() {
        return DisplayContentDto.from(contentService.findAll());
    }

    @Override
    public Optional<DisplayContentDto> findById(Long id) {
        return contentService.findById(id)
                .map(DisplayContentDto::from);
    }

    @Override
    public Optional<DisplayContentDto> save(CreateContentDto createContentDto) {
        return contentService.save(createContentDto.toContent())
                .map(DisplayContentDto::from);
    }

    @Override
    public Optional<DisplayContentDto> update(Long id, CreateContentDto createContentDto) {
        return contentService.update(id, createContentDto.toContent())
                .map(DisplayContentDto::from);
    }

    @Override
    public void deleteById(Long id) {
        contentService.deleteById(id);
    }
}
