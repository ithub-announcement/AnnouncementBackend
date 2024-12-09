package it.college.AnnouncementBackend.routes.review.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Сервис модерации")
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
}
