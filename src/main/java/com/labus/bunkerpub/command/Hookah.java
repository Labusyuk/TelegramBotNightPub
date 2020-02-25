package com.labus.bunkerpub.command;

import com.labus.bunkerpub.dao.Check;
import com.labus.bunkerpub.database.DatabaseManager;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.toIntExact;

public class Hookah implements Command<EditMessageText> {
    private Update update;
    @Override
    public EditMessageText execute() {
        int userId = update.getCallbackQuery().getFrom().getId();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        String call_data = update.getCallbackQuery().getData();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Стіл №1").setCallbackData("Кальяна_1")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Стіл №2").setCallbackData("Кальяна_2")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Стіл №3").setCallbackData("Кальяна_3")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Стіл №4").setCallbackData("Кальяна_4")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Стіл №5 (Великий)").setCallbackData("Кальяна_5")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Стіл №6").setCallbackData("Кальяна_6")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Стіл №7 (Кабінка)").setCallbackData("Кальяна_7")));
        List<InlineKeyboardButton> bottomButton = new ArrayList<>();
        bottomButton.add(new InlineKeyboardButton().setText("⬅").setCallbackData("ct_"));
        bottomButton.add(new InlineKeyboardButton().setText("\uD83D\uDCDE").setCallbackData("tl_Кальяна"));
        rowsInline.add(bottomButton);
        markupInline.setKeyboard(rowsInline);
        Check check = DatabaseManager.getCheck(userId);
        if(check==null) {
            check = new Check();
            check.setUserId(userId);
        }
        check.setRoom(call_data);
        DatabaseManager.setCheck(check);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        String answer = dateFormat.format(check.getDate())+"\n"+check.getLocaltime()+"\n"+check.getRoom()+"\nОберіть стіл :";

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
