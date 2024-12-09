package it.college.AnnouncementBackend.core.domain.model.entity;

import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
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

    @Column
    private String authorID;

    @Column
    private LocalDateTime ZoneDateTime;

    @Column
    private AStatus status;
}
