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
@Table(name = "chats_pins")
public class ChatsPinsEntity {
    @Id
    private Long chatId;

    private Integer placeId;
}
