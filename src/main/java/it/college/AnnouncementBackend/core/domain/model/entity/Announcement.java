package it.college.AnnouncementBackend.core.domain.model.entity;

import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(unique = true, nullable = false, updatable = false)
    private long UUID;

    @Column
    private String jsonContent;

    @Column
    private String authorID;

    @Column
    private LocalDateTime ZoneDateTime;

    @Column
    private AStatus status;
}
