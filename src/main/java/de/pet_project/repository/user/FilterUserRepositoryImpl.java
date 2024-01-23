package de.pet_project.repository.user;

import de.pet_project.domain.User;
import de.pet_project.dto.user.UserFilter;
import de.pet_project.repository.user.FilterUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    @Override
    public Page<User> findByFilter(UserFilter filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> user = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.firstname() != null) {
            predicates.add(criteriaBuilder.like(user.get("firstname"), "%" + filter.firstname() + "%"));
        }
        if (filter.lastname() != null) {
            predicates.add(criteriaBuilder.like(user.get("lastname"), "%" + filter.lastname() + "%"));
        }
        if (filter.nickname() != null) {
            predicates.add(criteriaBuilder.like(user.get("nickname"), "%" + filter.nickname() + "%"));
        }
        if (filter.birthDate() != null) {
            predicates.add(criteriaBuilder.lessThan(user.get("birthDate"), filter.birthDate()));
        }

        query.where(predicates.toArray(Predicate[]::new));

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<User> resultList = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(User.class))).where(predicates.toArray(Predicate[]::new));
        Long totalResults = entityManager.createQuery(countQuery).getSingleResult(); // todo has error

        return new PageImpl<>(resultList, pageable, totalResults);
    }
}
