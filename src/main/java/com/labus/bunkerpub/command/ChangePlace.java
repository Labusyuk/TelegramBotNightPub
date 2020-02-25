package com.labus.bunkerpub.command;

import com.labus.bunkerpub.dao.Check;
import com.labus.bunkerpub.database.DatabaseManager;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
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

public class ChangePlace implements Command<EditMessageText> {
    private Update update;
    @Override
    public EditMessageText execute() {
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        int userId = update.getCallbackQuery().getFrom().getId();
        Check check = DatabaseManager.getCheck(userId);
        if(check==null) {
            check = new Check();
            check.setUserId(userId);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Ресторан\uD83D\uDC68\u200D\uD83C\uDF73").setCallbackData("Ресторан")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Кальяна\uD83D\uDCA8").setCallbackData("Кальяна")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Танцпол\uD83D\uDD7A").setCallbackData("Танцпол")));
        List<InlineKeyboardButton> bottomButton = new ArrayList<>();
        bottomButton.add(new InlineKeyboardButton().setText("⬅").setCallbackData("cd_"));
        bottomButton.add(new InlineKeyboardButton().setText("\uD83D\uDCDE").setCallbackData("tl_ct"));
        rowsInline.add(bottomButton);
        markupInline.setKeyboard(rowsInline);
        String call_data = update.getCallbackQuery().getData();
        if(call_data.length()>3) {
            call_data = call_data.substring(3, call_data.length());
            LocalTime localTime = LocalTime.parse(call_data, DateTimeFormatter.ofPattern("HH:mm"));
            check.setLocaltime(localTime);
            DatabaseManager.setCheck(check);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        String answer = dateFormat.format(check.getDate())+"\n"+check.getLocaltime()+"\nОберіть зал:";
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
