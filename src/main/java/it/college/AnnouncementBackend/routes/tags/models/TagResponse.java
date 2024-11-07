package it.college.AnnouncementBackend.routes.tags.models;

import lombok.Data;

@Data
public class TagResponse {
    private Long id;

    private String title;

    private String color;
}
