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
@Table(name = "cities")
public class CityEntity {

    @Id
    private Integer id;

    private String name;
    
}
