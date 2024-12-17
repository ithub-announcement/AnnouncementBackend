package it.college.AnnouncementBackend.routes.drafts.servicies;

import it.college.AnnouncementBackend.core.config.Mapper;
import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import it.college.AnnouncementBackend.core.domain.repository.AnnouncementRepository;
import it.college.AnnouncementBackend.core.domain.service.AuthService;
import it.college.AnnouncementBackend.routes.drafts.model.DraftPayload;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * TODO: Сделать проверку доступа по токену
 */

/**
 * ## Сервис черновиков объявлений
 *
 * @author Горелов Дмитрий
 * */

@Service
@RequiredArgsConstructor
public class DraftService {
    private final AnnouncementRepository repository;

    private final Mapper mapper;

    private final AuthService auth;

    /**
     * Получить все черновики одного пользователя
     */

    public ResponseEntity findAllDraftByAuthor(String token){
        try {
            return new ResponseEntity<>(this.repository.findAllByAuthorIDAndStatus(auth.auth(token), AStatus.Draft), HttpStatus.OK);
        }catch (Exception err){
            err.printStackTrace();
            System.err.println("Ошибка при получение всех черновиков - " + err.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Получить черновик
     */

    public ResponseEntity findDraftByUUID(String token, String uuid){
        try {
            Optional<Announcement> OptionalAnnouncement = this.repository.findById(java.util.UUID.fromString(uuid));

            if (OptionalAnnouncement.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Announcement announcement = OptionalAnnouncement.get();

           if (!announcement.getAuthorID().equals(auth.auth(token))) {
               return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
           }

            return new ResponseEntity(announcement, HttpStatus.OK);
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
     * Изменить/создать черновик
     */

    public ResponseEntity saveDraft(String token, String uuid, DraftPayload payload){
        try {
            Optional<Announcement> current = this.repository.findById(UUID.fromString(uuid));

            String author = auth.auth(token);

            if (current.isEmpty()){
                Announcement announcement = mapper.getMapper().map(payload, Announcement.class);

                announcement.setStatus(AStatus.Draft);
                announcement.setZoneDateTime(LocalDateTime.now());
                announcement.setAuthorID(author);

                this.repository.save(announcement);
                return new ResponseEntity(announcement, HttpStatus.OK);
            }

            if (!current.get().getAuthorID().equals(author)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            current.get().setJsonContent(payload.getJsonContent());
            this.repository.save(current.get());

            return new ResponseEntity(current.get(), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
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
     * Удалить черновик
     */

    public ResponseEntity deleteDraftByUUID(String token, String uuid){
        try {
            Announcement announcement = this.repository.findById(UUID.fromString(uuid)).get();

            if (!announcement.getAuthorID().equals(auth.auth(token))){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            this.repository.deleteById(UUID.fromString(uuid));
            return ResponseEntity.ok().build();
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
