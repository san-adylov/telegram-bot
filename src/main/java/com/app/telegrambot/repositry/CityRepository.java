package com.app.telegrambot.repositry;

import com.app.telegrambot.models.entities.CityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepository extends BaseRepository<CityEntity> {

   public List<CityEntity> findAll() {
        return em.createQuery("""
                        SELECT c FROM CityEntity c
                        """, CityEntity.class)
                .getResultList();
    }

   public String getName(int id) {
        return em.createQuery("""
                SELECT c.name FROM CityEntity c
                WHERE c.id = :id
                """, String.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
