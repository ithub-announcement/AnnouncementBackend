package it.college.AnnouncementBackend.routes.drafts.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.college.AnnouncementBackend.routes.drafts.servicies.DraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Сервис черновиков объявлений")
@RestController
@RequestMapping("/draft")
@RequiredArgsConstructor
public class DraftController {

    private final DraftService service;
}
