package it.college.AnnouncementBackend.routes.drafts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.college.AnnouncementBackend.routes.drafts.model.DraftPayload;
import it.college.AnnouncementBackend.routes.drafts.servicies.DraftService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис черновиков объявлений")
@RestController
@RequestMapping("/draft")
@RequiredArgsConstructor
public class DraftController {

    private final DraftService service;

    @GetMapping
    @Operation(summary = "Получить все черновики пользователя")
    public ResponseEntity findAllDraftByAuthorId(@RequestHeader("Authorization") String authorization){
        return null;
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Получить черновик по UUID")
    public ResponseEntity findDraftByUUID(@RequestHeader("Authorization") String authorization,
                                          @RequestParam("uuid") String uuid)
    {
        return null;
    }

    @PostMapping
    @Operation(summary = "Сохранить черновик")
    public ResponseEntity saveDraft(@RequestHeader("Authorization") String authorization,
                                    @RequestParam(required = false) String uuid,
                                    @RequestBody DraftPayload payload)
    {
        return null;
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Удалить черновик")
    public ResponseEntity deleteDraftByUUID(@RequestHeader("Authorization") String authorization,
                                            @RequestParam("uuid") String uuid)
    {
        return null;
    }

}
