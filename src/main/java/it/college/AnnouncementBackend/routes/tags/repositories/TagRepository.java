package it.college.AnnouncementBackend.routes.tags.repositories;

import it.college.AnnouncementBackend.routes.tags.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
}
