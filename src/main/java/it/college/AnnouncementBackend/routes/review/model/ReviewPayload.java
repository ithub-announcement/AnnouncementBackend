package it.college.AnnouncementBackend.routes.review.model;

import lombok.Data;

import java.util.List;

@Data
public class ReviewPayload {
    private List<Long> tags;
}
