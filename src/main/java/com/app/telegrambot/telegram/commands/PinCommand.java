package com.app.telegrambot.telegram.commands;

import com.app.telegrambot.models.enums.Consts;
import com.app.telegrambot.repositry.ChatsPinsRepository;
import com.app.telegrambot.repositry.PlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@RequiredArgsConstructor
public class PinCommand implements Command {

    private final ChatsPinsRepository chatsPinsRepository;

    private final PlacesRepository placesRepository;

    @Transactional
    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        long userId = update.getMessage().getFrom().getId();
        Integer placeId = chatsPinsRepository.findByChatId(chatId);
        String pin = update.getMessage().getText().split(" ")[1];

        SendMessage sendMessage;
        if (placeId == null) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText(Consts.PIN_WRONG_ORDER);
        } else {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText(Consts.PIN_ADD_MSG);
            placesRepository.updatePin(placeId, pin, userId);
            chatsPinsRepository.deleteByChatId(chatId);
        }
        return sendMessage;
    }
}
