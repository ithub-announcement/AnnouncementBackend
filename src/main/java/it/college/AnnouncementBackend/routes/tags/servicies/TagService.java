package it.college.AnnouncementBackend.routes.tags.servicies;

import it.college.AnnouncementBackend.core.config.Mapper;
import it.college.AnnouncementBackend.core.domain.dto.SortDto;
import it.college.AnnouncementBackend.core.domain.model.ActionType;
import it.college.AnnouncementBackend.core.domain.model.Log;
import it.college.AnnouncementBackend.routes.tags.models.*;
import it.college.AnnouncementBackend.routes.tags.repositories.TagRepository;
import it.college.AnnouncementBackend.routes.tags.repositories.TagSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: Сделать проверку доступа по токену
 */

/**
 * ## Сервис тэгов объявлений
 *
 * @author Горелов Дмитрий
 * */

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository repository;

    private final Mapper mapper;

    private String fullName = "Иванов Иван Ивановыч";

    /**
     * Получить все тэги
     */

    public ResponseEntity findAllBySort(SortDto dto, String token) {
        try {
            if (dto.getLimit() == 0) {
                List<TagResponse> allTags = this.repository.findAll().stream()
                        .map(this::convertToTagResponse)
                        .collect(Collectors.toList());
                return new ResponseEntity<>(allTags, HttpStatus.OK);
            }

            PageRequest pageRequest = PageRequest.of(dto.getPage() - 1, dto.getLimit());

            if (!dto.getSearch().isEmpty()) {
                Specification<Tag> spec;
                spec = TagSpecification.search(dto.getSearch());
                return new ResponseEntity<>(this.repository.findAll(spec, pageRequest).getContent().stream()
                        .map(this::convertToTagResponse)
                        .collect(Collectors.toList()), HttpStatus.OK);
            }

            return new ResponseEntity<>(this.repository.findAll(pageRequest).getContent().stream()
                    .map(this::convertToTagResponse)
                    .collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception err) {
            System.err.println(err.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Добавить новый тэг
     */

    public ResponseEntity save(TagDto dto, String token) {
        try {
            Tag tag = mapper.getMapper().map(dto, Tag.class);

            tag.setLogs(new ArrayList<>());
            tag.getLogs().add(new Log(fullName, ActionType.CREATED, "Добавил новую категорию: " + dto.getTitle()));

            return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(tag));
        } catch (DataIntegrityViolationException e) {
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
     * Изменить тэг
     */

    public ResponseEntity saveEdit(Long id, TagDto dto, String token) {
        try {
            Tag tag = this.repository.findById(id).get();
            tag.getLogs().add(new Log(fullName, ActionType.EDIT, "Изменил категорию: " + tag.toString() + "на " + tag.toString()));

            mapper.getMapper().map(dto, Tag.class);
            return ResponseEntity.ok(this.repository.save(tag));
        } catch (DataIntegrityViolationException e) {
            System.err.println("Data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) {
            System.err.println("Not found: " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (ConstraintViolationException e) {
            System.err.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Internal Server Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * TODO: Узнать про логи и удаление
     *
     * Удалить тэг
     */

    public ResponseEntity<Void> deleteById(Long id, String token) {
        try {
            this.repository.deleteById(id);
            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            System.err.println("Not found: " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            System.err.println("Data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Получить тэг
     */

    public ResponseEntity findById(Long id, String token){
        try {
            return new ResponseEntity(this.repository.findById(id).get(), HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            System.err.println("Not found: " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception err){
            System.err.println(err.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Вспомогательный метод для Возврата с сервера
     */

    private TagResponse convertToTagResponse(Tag tag) {
        TagResponse response = new TagResponse();
        response.setId(tag.getId());
        response.setTitle(tag.getTitle());
        response.setColor(tag.getColor());
        return response;
    }
}
