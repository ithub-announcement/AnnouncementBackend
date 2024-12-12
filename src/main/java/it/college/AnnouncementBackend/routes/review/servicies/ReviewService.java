package it.college.AnnouncementBackend.routes.review.servicies;

import it.college.AnnouncementBackend.core.config.Mapper;
import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import it.college.AnnouncementBackend.core.domain.repository.AnnouncementRepository;
import it.college.AnnouncementBackend.routes.review.model.ReviewPayload;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * TODO: Сделать проверку доступа по токену
 */

/**
 * ## Сервис модерации объявлений
 *
 * @author Горелов Дмитрий
 * */

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final AnnouncementRepository repository;

    private final Mapper mapper;

    /**
     * Отправить на модерацию
     */

    public ResponseEntity sentToReview(String token, String uuid, ReviewPayload tags){
        try {
            Optional<Announcement> optionalAnnouncement = this.repository.findById(UUID.fromString(uuid));
            if (!optionalAnnouncement.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Announcement announcement = optionalAnnouncement.get();
            mapper.getMapper().map(tags, announcement);
            announcement.setStatus(AStatus.Review);

            this.repository.save(announcement);
            return ResponseEntity.ok("Успешно отправлено на модерацию");
        }catch (DataIntegrityViolationException e) {
            System.err.println("Data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ConstraintViolationException e) {
            System.err.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Internal Server Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Одобрить публикацию
     */

    public ResponseEntity acceptToPublish(String token, String uuid){
        try {
            Optional<Announcement> optionalAnnouncement = this.repository.findById(UUID.fromString(uuid));
            if (!optionalAnnouncement.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Announcement announcement = optionalAnnouncement.get();
            announcement.setStatus(AStatus.WaitPublish);

            this.repository.save(announcement);
            return ResponseEntity.ok("Успешно одобрено");
        }catch (DataIntegrityViolationException e) {
            System.err.println("Data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ConstraintViolationException e) {
            System.err.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Internal Server Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Отклонить публикацию
     */

    public ResponseEntity rejectToPublish(String token, String uuid, String reason){
        try {
            Optional<Announcement> optionalAnnouncement = this.repository.findById(UUID.fromString(uuid));
            if (!optionalAnnouncement.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Announcement announcement = optionalAnnouncement.get();
            announcement.setReason(reason);
            announcement.setStatus(AStatus.Draft);

            this.repository.save(announcement);
            return ResponseEntity.ok("Успешно отколнено");
        }catch (DataIntegrityViolationException e) {
            System.err.println("Data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ConstraintViolationException e) {
            System.err.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Internal Server Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
