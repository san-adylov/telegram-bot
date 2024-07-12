package com.app.telegrambot.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_cities")
public class ChatCitiesEntity {

    @Id
    private Long id;

    private Integer cityId;
}
