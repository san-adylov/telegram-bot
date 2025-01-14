package com.app.telegrambot.telegram.commands;

import com.app.telegrambot.models.entities.CityEntity;
import com.app.telegrambot.models.enums.Consts;
import com.app.telegrambot.repositry.CityRepository;
import com.app.telegrambot.telegram.callbacks.CallbackType;
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
public class StartCommand implements Command {

    private final CityRepository repository;

    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(Consts.START_MESSAGE);

        List<CityEntity> allCities = repository.findAll();

        addKeyboard(sendMessage, allCities);
        return sendMessage;
    }

    private void addKeyboard(SendMessage sendMessage, List<CityEntity> allCities) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        for (var city : allCities) {
            var inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(city.getName());
            String jsonCallback = JsonHandler.toJson(List.of(CallbackType.CITY_CHOOSE, city.getId().toString()));
            inlineKeyboardButton.setCallbackData(jsonCallback);
            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
}
