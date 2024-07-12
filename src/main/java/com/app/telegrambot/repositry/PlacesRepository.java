package com.app.telegrambot.repositry;

import com.app.telegrambot.dto.AddressDto;
import com.app.telegrambot.dto.PinDto;
import com.app.telegrambot.models.entities.PlacesEntity;
import com.app.telegrambot.models.enums.PinState;
import com.app.telegrambot.models.enums.PlaceType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PlacesRepository extends BaseRepository<PlacesEntity> {
    @Transactional
    public List<AddressDto> getAddressesOfType(PlaceType placeType, long chatId) {
        return em.createQuery("""
                        SELECT NEW com.app.telegrambot.dto.AddressDto(
                        p.id,p.address,p.name)
                        FROM PlacesEntity p
                        INNER JOIN ChatsCitiesEntity c
                        ON p.cityId = c.cityId
                        WHERE p.type =: placeType AND c.chatId =: chatId
                        """, AddressDto.class)
                .setParameter("placeType", placeType)
                .setParameter("chatId", chatId)
                .getResultList();
    }

    @Transactional
    public List<PlaceType> getTypesOfCity(int cityId) {
        return em.createQuery("""
                        SELECT DISTINCT p.type FROM PlacesEntity p
                        WHERE p.cityId = :cityId
                        """, PlaceType.class)
                .setParameter("cityId", cityId)
                .getResultList();
    }

    @Transactional
    public int updatePin(Integer addressId, String pin, Long userId) {
        return em.createQuery("""
                        UPDATE PlacesEntity p
                        SET p.pin = :pin, p.updatedBy = :userId
                        WHERE p.id = :id""")
                .setParameter("pin", pin)
                .setParameter("id", addressId)
                .setParameter("userId", userId)
                .executeUpdate();

    }

    @Transactional
    public PinDto findPin(Integer addressId) {
        return em.createQuery("""
                        SELECT NEW com.app.telegrambot.dto.PinDto(
                        p.pin, p.state)
                        FROM PlacesEntity p
                        WHERE p.id = :id
                        """, PinDto.class)
                .setParameter("id", addressId)
                .getSingleResult();

    }

    @Transactional
    public int updateState(PinState state, Integer addressId, Long userId) {
        return em.createQuery("""
                        update PlacesEntity p
                        set p.state =: state, p.updatedBy =: userId
                        where p.id = :id
                        """)
                .setParameter("id", addressId)
                .setParameter("state", state)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
