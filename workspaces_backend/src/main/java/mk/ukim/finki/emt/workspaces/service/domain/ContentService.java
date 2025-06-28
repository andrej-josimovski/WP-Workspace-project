package mk.ukim.finki.emt.workspaces.service.domain;

import mk.ukim.finki.emt.workspaces.model.domain.Content;

import java.util.List;
import java.util.Optional;

public interface ContentService {
    List<Content> findAll();

    Optional<Content> findById(Long id);

    Optional<Content> save(Content content);

    Optional<Content> update(Long id, Content content);

    void deleteById(Long id);
}

