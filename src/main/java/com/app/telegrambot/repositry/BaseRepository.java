package com.app.telegrambot.repositry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseRepository<T> {

    @PersistenceContext
    protected EntityManager em;

    @Transactional
    public T persist(T e) {
        em.persist(e);
        return e;
    }

    ;

    @Transactional
    public T merge(T e) {
        em.merge(e);
        return e;
    }
}
