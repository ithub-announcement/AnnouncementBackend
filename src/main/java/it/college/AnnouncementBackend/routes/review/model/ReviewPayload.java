package it.college.AnnouncementBackend.routes.review.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReviewPayload {
    private List<Long> tags;

    private LocalDate publishDate;

    private LocalDate deleteDate;
}
