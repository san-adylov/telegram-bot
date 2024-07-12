package com.app.telegrambot.dto;

import com.app.telegrambot.models.enums.PinState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PinDto {

    private String pin;

    private PinState pinState;
}
