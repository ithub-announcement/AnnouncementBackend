package it.college.AnnouncementBackend.core.domain.repository;

import it.college.AnnouncementBackend.core.domain.model.entity.Announcement;
import it.college.AnnouncementBackend.core.domain.model.enums.AStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AnnouncementSpecification {

    public static Specification<Announcement> statusIsPublic() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), AStatus.Public);
    }

    public static Specification<Announcement> search(String searchTerm) {
        // Реализация метода поиска по строке
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return criteriaBuilder.conjunction(); // Возвращает "всегда истину"
            }
            return criteriaBuilder.like(root.get("jsonContent"), "%" + searchTerm + "%");
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
