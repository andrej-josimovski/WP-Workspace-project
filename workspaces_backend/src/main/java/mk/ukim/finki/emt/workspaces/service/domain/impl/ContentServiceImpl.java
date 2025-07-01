package mk.ukim.finki.emt.workspaces.service.domain.impl;

import mk.ukim.finki.emt.workspaces.model.domain.Content;
import mk.ukim.finki.emt.workspaces.repository.ContentRepository;
import mk.ukim.finki.emt.workspaces.service.domain.ContentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public List<Content> findAll() {
        return contentRepository.findAll();
    }

    @Override
    public Optional<Content> findById(Long id) {
        return contentRepository.findById(id);
    }

    @Override
    public Optional<Content> save(Content content) {
        if (content.getName() == null || content.getName().isEmpty() ||
                content.getType() == null ) {
            throw new IllegalArgumentException("Invalid content data");
        }
        if (content.getUploadedAt() == null) {
            content.setUploadedAt(java.time.LocalDateTime.now());
        }
        Content newContent = new Content(
                content.getName(),
                content.getDescription(),
                content.getType(),
                content.getUploadedAt()
        );
        return Optional.of(contentRepository.save(newContent));
    }


    @Override
    public Optional<Content> update(Long id, Content content) {
        return contentRepository.findById(id)
                .map(existingContent -> {
                    if (content.getName() != null && !content.getName().isEmpty()) {
                        existingContent.setName(content.getName());
                    }
                    if (content.getDescription() != null && !content.getDescription().isEmpty()) {
                        existingContent.setDescription(content.getDescription());
                    }
                    if (content.getType() != null) {
                        existingContent.setType(content.getType());
                    }
                    if (content.getUploadedAt() != null) {
                        existingContent.setUploadedAt(content.getUploadedAt());
                    }
                    return contentRepository.save(existingContent);
                });
    }


    @Override
    public void deleteById(Long id) {
        contentRepository.deleteById(id);
    }
}
