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

    @ManyToMany
    @JoinTable(
            name = "announcement_tags",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "delete_date")
    private LocalDate deleteDate;

    @Column
    private String reason;
}
