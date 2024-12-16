package it.college.AnnouncementBackend.routes.review.servicies;

import it.college.AnnouncementBackend.core.config.Mapper;
import it.college.AnnouncementBackend.core.domain.dto.SortDto;
import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import it.college.AnnouncementBackend.core.domain.repository.AnnouncementRepository;
import it.college.AnnouncementBackend.core.domain.repository.AnnouncementSpecification;
import it.college.AnnouncementBackend.routes.review.model.ReviewPayload;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
     * Получить все черновики находящиеся на проверке
     */

    public ResponseEntity getAllReview(String token, SortDto dto){
        try {
            // Проверяем, если dto равно null, возвращаем все объявления со статусом Public
            if (dto == null) {
                return new ResponseEntity<>(repository.findAllByStatus(AStatus.Public), HttpStatus.OK);
            }

            // Создаем объект Pageable для пагинации и сортировки по publishDate
            Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit(),
                    "asc".equalsIgnoreCase(dto.getSortDir()) ? Sort.by("publishDate").ascending() : Sort.by("publishDate").descending());

            // Создаем спецификацию для поиска
            Specification<Announcement> spec = AnnouncementSpecification.search(dto.getSearch());

            // Добавляем фильтрацию по тегам
            if (dto.getTags() != null && !dto.getTags().isEmpty()) {
                spec = spec.and(AnnouncementSpecification.tagsIn(dto.getTags()));
            }

            // Получаем список объявлений со статусом Review с учетом фильтрации и пагинации
            Page<Announcement> announcements = repository.findAll(spec.and(AnnouncementSpecification.statusIs(AStatus.Review)), pageable);

            return new ResponseEntity<>(announcements.getContent(), HttpStatus.OK);
        }catch (DataIntegrityViolationException e) {
            System.err.println("Data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ConstraintViolationException e) {
            System.err.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Internal Server Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

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
            e.printStackTrace();
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

            if (announcement.getPublishDate().equals(LocalDate.now())){
                announcement.setStatus(AStatus.Public);
            }else {
                announcement.setStatus(AStatus.WaitPublish);
            }

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
            e.printStackTrace();
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
            e.printStackTrace();
            System.err.println("Internal Server Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
