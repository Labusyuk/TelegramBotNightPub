package com.labus.bunkerpub;

import com.labus.bunkerpub.command.Command;
import com.labus.bunkerpub.command.CommandFactory;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class BunkerPubBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        Command command = CommandFactory.defineCommand(update);
        if(command==null)return;
            try {
                execute(command.execute());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "BunkerPubBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "904479257:AAEbGGmP2yJNSVEqm5OrF2ojSMKfmqJoFOc";
    }
}