package it.college.AnnouncementBackend.routes.announcements.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.college.AnnouncementBackend.routes.announcements.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис объявлений")
@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService service;

    @GetMapping
    @Operation(summary = "Получить все объявления")
    public ResponseEntity findAllWithSort(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "0") int limit,
                                          @RequestParam(defaultValue = "") String search,
                                          @RequestParam(defaultValue = "asc") String sort,
                                          @RequestParam Long[] tags){
        return this.service.findAllWithSort(page, limit, search, sort, tags);
    }

    @DeleteMapping
    @Operation(summary = "Удалить принудительно")
    public ResponseEntity deleteByUUID(@RequestHeader("Authorization") String authorization,
                                       @RequestBody String uuid){
        return null;
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Получить объявление")
    public ResponseEntity findByUUID(@RequestHeader("Authorization") String authorization,
                                       @PathVariable("uuid") String uuid){
        return null;
    }
}
