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
@Table(name = "chats_citites")
public class ChatsCitiesEntity {

    @Id
    private Long chatId;

    private int cityId;
}
