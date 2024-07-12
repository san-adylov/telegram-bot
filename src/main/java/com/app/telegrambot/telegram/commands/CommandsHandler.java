package com.app.telegrambot.telegram.commands;

import com.app.telegrambot.models.enums.Consts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CommandsHandler {

    private final Map<String, Command> commands;

    public CommandsHandler(StartCommand startCommand,
                           PinCommand pinCommand) {
        this.commands = Map.of(
                "/start", startCommand,
                "/pin", pinCommand
        );
    }


    public SendMessage handleCommands(Update update) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            return commandHandler.apply(update);
        }else {
            return new SendMessage(String.valueOf(chatId), Consts.UNKNOWN_COMMAND);
        }
    }
}
