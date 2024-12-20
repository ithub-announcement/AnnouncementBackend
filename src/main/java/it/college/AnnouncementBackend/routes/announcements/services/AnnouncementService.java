package it.college.AnnouncementBackend.routes.announcements.services;

import it.college.AnnouncementBackend.core.domain.dto.SortDto;
import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import it.college.AnnouncementBackend.core.domain.repository.AnnouncementRepository;
import it.college.AnnouncementBackend.core.domain.repository.AnnouncementSpecification;
import org.springframework.transaction.annotation.Transactional;

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

import java.util.ArrayList;
import java.util.Arrays;

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
public class AnnouncementService {
    private final AnnouncementRepository repository;

    /**
     * Получить все объявления по сортировке: поиск по контенту или автору. По возростанию
     */

    @Transactional(readOnly = true)
    public ResponseEntity findAllWithSort(int page, int limit, String search, String sort, Long[]tags) {
        try {
            // Проверяем, если dto равно null, возвращаем все объявления со статусом Public
            if (limit == 0) {
                return new ResponseEntity<>(repository.findAllByStatus(AStatus.Public), HttpStatus.OK);
            }

            // Создаем объект Pageable для пагинации и сортировки по publishDate
            Pageable pageable = PageRequest.of(page, limit,
                    "asc".equalsIgnoreCase(sort) ? Sort.by("publishDate").ascending() : Sort.by("publishDate").descending());

            // Создаем спецификацию для поиска
            Specification<Announcement> spec = AnnouncementSpecification.search(search);

            // Добавляем фильтрацию по тегам
            if (tags != null && tags.length != 0) {
                spec = spec.and(AnnouncementSpecification.tagsIn(new ArrayList<Long>(Arrays.asList(tags))));
            }

            // Получаем список объявлений со статусом Public с учетом фильтрации и пагинации
            Page<Announcement> announcements = repository.findAll(spec.and(AnnouncementSpecification.statusIs(AStatus.Public)), pageable);

            return new ResponseEntity<>(announcements.getContent(), HttpStatus.OK);
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
}
