package com.app.telegrambot.telegram.callbacks;

import com.app.telegrambot.models.enums.Consts;
import com.app.telegrambot.models.enums.PlaceType;
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
public class TypeChooseCallback implements CallbackHandler {

    private final PlacesRepository placesRepository;

    @Override
    public SendMessage apply(Callback callback, Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        PlaceType placeType = PlaceType.valueOf(callback.getData());
        SendMessage answer = new SendMessage(String.valueOf(chatId), String.format(Consts.CHOSE_TYPE_MESSAGE, placeType.name()));
        addAddressesKeyboard(answer, placeType, chatId);
        return answer;
    }


    private void addAddressesKeyboard(SendMessage answer, PlaceType placeType, long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (var address : placesRepository.getAddressesOfType(placeType, chatId)) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(address.toString());
            String jsonCallback = JsonHandler.toJson(List.of(CallbackType.ADDRESS_CHOOSE, address.getId()));
            inlineKeyboardButton.setCallbackData(jsonCallback);
            keyboardButtonsRow.add(inlineKeyboardButton);
            rows.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rows);
        answer.setReplyMarkup(inlineKeyboardMarkup);
    }

}
