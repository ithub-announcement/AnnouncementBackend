package it.college.AnnouncementBackend.core.domain.model.entity;

import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import it.college.AnnouncementBackend.routes.tags.models.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID UUID;

    @Column
    private String jsonContent;

    @Column(name = "author_id")
    private String authorID;

    @Column
    private LocalDateTime ZoneDateTime;

    @Column
    private AStatus status;

    @ElementCollection
    @CollectionTable(name = "announcement_tags", joinColumns = @JoinColumn(name = "announcement_id"))
    @Column(name = "tag_id")
    private List<Long> tagIds;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column
    private String reason;
}
