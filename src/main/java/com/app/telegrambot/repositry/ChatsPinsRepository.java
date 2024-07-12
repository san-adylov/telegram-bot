package com.app.telegrambot.repositry;

import com.app.telegrambot.models.entities.ChatsPinsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ChatsPinsRepository extends BaseRepository<ChatsPinsEntity> {

    public Integer findByChatId(Long chatId) {
        return em.createQuery("""
                        SELECT c.placeId FROM ChatsPinsEntity c
                        WHERE c.chatId = :chatId
                        """, Integer.class)
                .setParameter("chatId", chatId)
                .getResultStream().findFirst().orElse(null);
    }

    public int deleteByChatId(Long chatId) {
        return em.createQuery("""
                        DELETE FROM ChatsCitiesEntity c WHERE c.chatId = :chatId
                        """)
                .setParameter("chatId", chatId)
                .executeUpdate();
    }
}
