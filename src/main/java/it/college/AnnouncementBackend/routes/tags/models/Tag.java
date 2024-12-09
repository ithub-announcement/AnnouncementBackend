package it.college.AnnouncementBackend.routes.tags.models;

import it.college.AnnouncementBackend.core.domain.model.entity.Log;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column
    private String color;

    @ElementCollection
    private List<Log> logs;
}
