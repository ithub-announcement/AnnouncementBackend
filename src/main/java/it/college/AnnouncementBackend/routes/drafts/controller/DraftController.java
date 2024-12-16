package it.college.AnnouncementBackend.routes.drafts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.college.AnnouncementBackend.routes.drafts.model.DraftPayload;
import it.college.AnnouncementBackend.routes.drafts.servicies.DraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис черновиков объявлений")
@RestController
@RequestMapping("/draft")
@RequiredArgsConstructor
public class DraftController {

    private final DraftService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все черновики пользователя")
    public ResponseEntity findAllDraftByAuthorId(@RequestHeader("Authorization") String authorization){
        return this.service.findAllDraftByAuthor(authorization);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Получить черновик по UUID")
    public ResponseEntity findDraftByUUID(@RequestHeader("Authorization") String authorization,
                                          @PathVariable("uuid") String uuid)
    {
        return this.service.findDraftByUUID(authorization, uuid);
    }

    @PostMapping
    @Operation(summary = "Сохранить черновик")
    public ResponseEntity saveDraft(@RequestHeader("Authorization") String authorization,
                                    @PathVariable(required = false) String uuid,
                                    @RequestBody DraftPayload payload)
    {
        return this.service.saveDraft(authorization, uuid, payload);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Удалить черновик")
    public ResponseEntity deleteDraftByUUID(@RequestHeader("Authorization") String authorization,
                                            @PathVariable("uuid") String uuid)
    {
        return this.service.deleteDraftByUUID(authorization, uuid);
    }

}
