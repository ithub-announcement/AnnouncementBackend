package it.college.AnnouncementBackend.routes.announcements.services;

import it.college.AnnouncementBackend.core.domain.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
