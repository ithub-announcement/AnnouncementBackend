package it.college.AnnouncementBackend.core.domain.repository;

import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Long, Announcement> {
}
