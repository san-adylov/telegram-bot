package com.app.telegrambot.telegram.callbacks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Callback {

    private CallbackType callbackType;

    private String data;
}
