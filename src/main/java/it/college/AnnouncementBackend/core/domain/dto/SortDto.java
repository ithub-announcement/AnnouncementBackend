package it.college.AnnouncementBackend.core.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SortDto {
    private int limit;

    private int page;

    private String search;

    private List<Long> tags;

    private String sortDir; // asc для сортировки по возрастанию
}
