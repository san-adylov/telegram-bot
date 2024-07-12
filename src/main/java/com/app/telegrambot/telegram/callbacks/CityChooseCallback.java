package com.app.telegrambot.telegram.callbacks;

import com.app.telegrambot.models.entities.ChatsCitiesEntity;
import com.app.telegrambot.models.enums.Consts;
import com.app.telegrambot.repositry.ChatCitiesRepository;
import com.app.telegrambot.repositry.CityRepository;
import com.app.telegrambot.repositry.PlacesRepository;
import com.app.telegrambot.utils.JsonHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CityChooseCallback implements CallbackHandler {

    private final ChatCitiesRepository chatCitiesRepository;

    private final CityRepository cityRepository;

    private final PlacesRepository placesRepository;

    @Override
    public SendMessage apply(Callback callback, Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int cityId = Integer.parseInt(callback.getData());
        chatCitiesRepository.merge(new ChatsCitiesEntity(chatId, cityId));
        SendMessage answer = new SendMessage(String.valueOf(chatId), String.format(Consts.CHOSE_MESSAGE, cityRepository.getName(cityId)));
        addTypesKeyboard(answer, cityId);
        return answer;
    }

    private void addTypesKeyboard(SendMessage answer, Integer cityId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        for (var type : placesRepository.getTypesOfCity(cityId)) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(type.name());
            String jsonCallback = JsonHandler.toJson(List.of(CallbackType.TYPE_CHOOSE, type));
            inlineKeyboardButton.setCallbackData(jsonCallback);
            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        answer.setReplyMarkup(inlineKeyboardMarkup);
    }
}
