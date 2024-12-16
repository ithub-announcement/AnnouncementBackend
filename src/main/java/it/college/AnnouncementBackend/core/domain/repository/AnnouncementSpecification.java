package it.college.AnnouncementBackend.core.domain.repository;

import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AnnouncementSpecification {

    public static Specification<Announcement> statusIsPublic() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), AStatus.Public);
    }

    public static Specification<Announcement> statusIsReview() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), AStatus.Review);
    }

    public static Specification<Announcement> search(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                // Создаем условие для поиска
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("jsonContent")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("authorID")), searchPattern)
                );
            }
            return criteriaBuilder.conjunction(); // Возвращаем "все", если строка поиска пуста
        };
    }

    public static Specification<Announcement> tagsIn(List<Long> tags) {
        return (root, query, criteriaBuilder) -> {
            if (tags == null || tags.isEmpty()) {
                return criteriaBuilder.conjunction(); // Возвращаем "все" если список тегов пуст
            }
            return root.join("tags").get("id").in(tags);
        };
    }
}
