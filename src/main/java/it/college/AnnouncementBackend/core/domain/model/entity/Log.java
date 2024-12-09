package it.college.AnnouncementBackend.core.domain.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Log {
    private String fullName;

    private ActionType type;

    private String details;
}
