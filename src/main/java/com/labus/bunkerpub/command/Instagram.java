package com.labus.bunkerpub.command;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Instagram implements Command<SendMessage> {
    private Update update;
    @Override
    public SendMessage execute() {
        String text = EmojiParser.parseToUnicode("Наша спільнота в Instagram:\nhttps://instagram.com/bunker_pub2019\uD83D\uDCF8");
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(text);
        return message;
    }

    @Override
    public void setUpdate(Update update) {
        this.update = update;
    }
}
