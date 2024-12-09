package it.college.AnnouncementBackend.routes.drafts.servicies;

import it.college.AnnouncementBackend.core.domain.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DraftService {
    private final AnnouncementRepository repository;

}
