package com.app.telegrambot.telegram.callbacks;

import com.app.telegrambot.models.entities.ChatsPinsEntity;
import com.app.telegrambot.models.enums.Consts;
import com.app.telegrambot.models.enums.PinState;
import com.app.telegrambot.repositry.ChatsPinsRepository;
import com.app.telegrambot.repositry.PlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class PinActionCallback implements CallbackHandler {

    private final PlacesRepository placesRepository;

    private final ChatsPinsRepository chatsPinRepository;

    @Override
    public SendMessage apply(Callback callback, Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long userId = update.getCallbackQuery().getFrom().getId();
        SendMessage answer = new SendMessage();

        Integer addressId = Integer.valueOf(callback.getData());
        if (callback.getCallbackType() == CallbackType.PIN_DONT_ADD) {
            answer = new SendMessage(String.valueOf(chatId), Consts.PIN_DONT_ADD_BYE);
        } else if (callback.getCallbackType() == CallbackType.PIN_ADD) {
            placesRepository.updateState(PinState.OUTDATED, addressId, userId);
            chatsPinRepository.merge(new ChatsPinsEntity(chatId, addressId));
            answer = new SendMessage(String.valueOf(chatId), Consts.PIN_ADD_MSG);
        }
        return answer;
    }

}
