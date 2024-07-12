package com.app.telegrambot.telegram;

import com.app.telegrambot.config.properties.BotProperties;
import com.app.telegrambot.models.enums.Consts;
import com.app.telegrambot.telegram.callbacks.CallbacksHandler;
import com.app.telegrambot.telegram.commands.CommandsHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotProperties properties;

    private final CommandsHandler commandsHandler;

    private final CallbacksHandler callbacksHandler;

    @Override
    public String getBotUsername() {
        return properties.getName();
    }

    @Override
    public String getBotToken(){
        return properties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            if (update.getMessage().getText().startsWith("/")) {
                sendMessage(commandsHandler.handleCommands(update));
            }else {
                sendMessage(new SendMessage(chatId, Consts.CANT_UNDERSTAND));
            }
        }else if (update.hasCallbackQuery()){
            sendMessage(callbacksHandler.handleCallbacks(update));
        }
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        }catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
}
