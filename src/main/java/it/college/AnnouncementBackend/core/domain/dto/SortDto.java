package it.college.AnnouncementBackend.core.domain.dto;

import it.college.AnnouncementBackend.routes.tags.models.Tag;
import lombok.Data;

import java.util.List;

@Data
public class SortDto {
    private int limit;

    private int page;

    private String search;

    private boolean paranoid;

    private String sortDir; // asc для сортировки по возрастанию
}
