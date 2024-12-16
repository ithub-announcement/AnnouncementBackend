package it.college.AnnouncementBackend.core.domain.repository;

import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> , JpaSpecificationExecutor<Announcement> {
    List<Announcement> findAllByAuthorIDAndStatus(String authorID, AStatus status);

    List<Announcement> findAllByStatus(AStatus status);
}
