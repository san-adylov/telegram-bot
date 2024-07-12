package com.app.telegrambot.models.entities;

import com.app.telegrambot.models.enums.PinState;
import com.app.telegrambot.models.enums.PlaceType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "places")
public class PlacesEntity {

    @Id
    private Integer id;

    @Id
    private Integer cityId;

    @Enumerated(EnumType.STRING)
    private PlaceType type;

    private String address;

    private String name;

    private String pin;

    private Long updatedBy;

    @Enumerated(EnumType.STRING)
    private PinState state;
}
