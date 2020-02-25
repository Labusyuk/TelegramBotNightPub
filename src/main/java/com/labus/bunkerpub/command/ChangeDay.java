package com.labus.bunkerpub.command;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.toIntExact;

public class ChangeDay implements Command<EditMessageText> {
    private Update update;
    @Override
    public EditMessageText execute() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy"),
                dateFormatBD = new SimpleDateFormat("dd.MM.yyyy");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        Date date = new Date();
        for(int i=0;i<14;i++){
            rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText(dateFormat.format(date)).setCallbackData("cd_"+dateFormatBD.format(date))));
            date.setTime(date.getTime()+86400000l);
        }
        List<InlineKeyboardButton> bottomButton = new ArrayList<>();
        bottomButton.add(new InlineKeyboardButton().setText("⬅").setCallbackData("start"));
        bottomButton.add(new InlineKeyboardButton().setText("\uD83D\uDCDE").setCallbackData("tl_book_a_table"));
        rowsInline.add(bottomButton);
        markupInline.setKeyboard(rowsInline);
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        String answer = "Оберіть день:";
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
