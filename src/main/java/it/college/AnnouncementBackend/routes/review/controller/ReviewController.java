package it.college.AnnouncementBackend.routes.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.college.AnnouncementBackend.routes.review.model.ReviewPayload;
import it.college.AnnouncementBackend.routes.review.servicies.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис модерации")
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PutMapping("/uuid")
    @Operation(summary = "Отправить на проверку черновик")
    public ResponseEntity sentToReview(@RequestHeader("Authorization") String authorization,
                                       @RequestParam("uuid") String uuid,
                                       @RequestBody ReviewPayload payload){
        return null;
    }

    @PostMapping("/accept/{uuid}")
    @Operation(summary = "Принять черновик")
    public ResponseEntity accept(@RequestHeader("Authorization") String authorization,
                                 @RequestParam("uuid") String uuid){
        return null;
    }

    @PostMapping("/reject/{uuid}")
    @Operation(summary = "Отклонить черновик")
    public ResponseEntity reject(@RequestHeader("Authorization") String authorization,
                                 @RequestParam("uuid") String uuid,
                                 @RequestBody String reason){
        return null;
    }
}
