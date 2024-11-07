package it.college.AnnouncementBackend.routes.tags.repositories;

import it.college.AnnouncementBackend.routes.tags.models.Tag;
import org.springframework.data.jpa.domain.Specification;

public class TagSpecification {

    public static Specification<Tag> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<Tag> spec = Specification.where(null);

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("title")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("color")), searchPattern)
                        )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
