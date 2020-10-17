package me.kakaopay.homework.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public abstract class AbstractRepositorySupport extends QuerydslRepositorySupport {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public AbstractRepositorySupport(Class<?> domainClass) {
        super(domainClass);
    }

    @PersistenceContext
    @Override
    public void setEntityManager(@SuppressWarnings("NullableProblems") EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }
}
