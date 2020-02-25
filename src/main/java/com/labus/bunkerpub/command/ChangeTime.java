package com.labus.bunkerpub.command;

import com.labus.bunkerpub.dao.Check;
import com.labus.bunkerpub.database.DatabaseManager;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.lang.Math.toIntExact;
public class ChangeTime implements Command<EditMessageText> {
    private Update update;
    @Override
    public EditMessageText execute() {
        int userId = update.getCallbackQuery().getFrom().getId();
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy"),
                simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Check check = DatabaseManager.getCheck(userId);
        if(check==null){
            check = new Check();
            check.setUserId(userId);
        }
        if(call_data.length()>3) {
            call_data = call_data.substring(3, call_data.length());
            Date date = null;
            try {
                date = simpleDateFormat.parse(call_data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            check.setDate(date);
            DatabaseManager.setCheck(check);
        }
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        LocalTime localTime = LocalTime.of(19,0);
        for(int i=0;i<5;i++){
            localTime = LocalTime.of(19+i,0);
            rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText(localTime.format(DateTimeFormatter.ofPattern("HH:mm"))).setCallbackData("ct_"+localTime.format(DateTimeFormatter.ofPattern("HH:mm")))));
        }
        List<InlineKeyboardButton> bottomButton = new ArrayList<>();
        bottomButton.add(new InlineKeyboardButton().setText("⬅").setCallbackData("book_a_table"));
        bottomButton.add(new InlineKeyboardButton().setText("\uD83D\uDCDE").setCallbackData("tl_cd"));
        rowsInline.add(bottomButton);
        markupInline.setKeyboard(rowsInline);

        String answer = dateFormat.format(check.getDate())+"\nОберіть час:";
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
