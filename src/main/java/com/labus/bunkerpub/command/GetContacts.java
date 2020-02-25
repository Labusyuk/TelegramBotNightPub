package com.labus.bunkerpub.command;

import com.labus.bunkerpub.database.DatabaseManager;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class GetContacts implements Command<EditMessageText> {
    private Update update;
    @Override
    public EditMessageText execute() {
        String call_data = update.getCallbackQuery().getData();
        if(call_data.length()>3) {
            call_data = call_data.substring(3, call_data.length());
        }else call_data="start";
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> bottomButton = new ArrayList<>();
        bottomButton.add(new InlineKeyboardButton().setText("⬅").setCallbackData(call_data));
        rowsInline.add(bottomButton);
        markupInline.setKeyboard(rowsInline);
        String answer = "Номер телефону бармена/кальянщика:\n\t+380 ХХ ХХХХ ХХХ - адміністратор Анна";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(toIntExact(message_id))
                .setText(answer)
                .setReplyMarkup(markupInline);
        return new_message;
    }

    @Override
    public void setUpdate(Update update) {
        this.update = update;
    }
}
