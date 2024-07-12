package com.app.telegrambot.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceType {

    IMPERIA_PIZZA("Империя Пицца"),

    PIZZA_MIA("Пицца Мия"),

    OTHER("Другие");

    private final String name;
}
