package it.college.AnnouncementBackend.routes.tags.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.college.AnnouncementBackend.core.domain.dto.SortDto;
import it.college.AnnouncementBackend.routes.tags.models.TagDto;
import it.college.AnnouncementBackend.routes.tags.servicies.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис тэгов объявлений")
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService service;

    @GetMapping("/all")
    @Operation(summary = "Получить все тэги")
    public ResponseEntity findAllTagBySort(@RequestHeader("Authorization") String authorization,
                                           @RequestParam int page,
                                           @RequestParam int limit){
        return this.service.findAllBySort(page, limit, authorization);
    }

    @PostMapping
    @Operation(summary = "Добавить новый тэг")
    public ResponseEntity saveTag(@RequestHeader("Authorization") String authorization, @RequestBody TagDto tagDto){
        return this.service.save(tagDto, authorization);
    }

    @PutMapping
    @Operation(summary = "Изменить существующий тэг")
    public ResponseEntity saveEditTag(@RequestHeader("Authorization") String authorization, @RequestBody TagDto tagDto, @RequestParam("id") Long id){
        return this.service.saveEdit(id, tagDto, authorization);
    }

    @GetMapping
    @Operation(summary = "Найти существующий тэг")
    public ResponseEntity findTagById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id){
        return this.service.findById(id, authorization);
    }

    @DeleteMapping
    @Operation(summary = "Удалить тэг")
    public ResponseEntity deleteTagById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id){
        return this.service.deleteById(id, authorization);
    }
}
