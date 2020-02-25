package com.labus.bunkerpub.command;

import com.labus.bunkerpub.dao.Check;
import com.labus.bunkerpub.database.DatabaseManager;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Data
public class Dancefloor implements Command<EditMessageText> {
    private Update update;

    @Override
    public EditMessageText execute() {
        int userId = update.getCallbackQuery().getFrom().getId();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        String call_data = update.getCallbackQuery().getData();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            for (int k = 1; k < 3; k++) {
                buttons.add(new InlineKeyboardButton().setText("Стіл №" + (2 * i + k)).setCallbackData("table_" + (2 * i + k)));
            }
            rowsInline.add(buttons);
        }
        List<InlineKeyboardButton> bottomButton = new ArrayList<>();
        bottomButton.add(new InlineKeyboardButton().setText("⬅").setCallbackData("ct_"));
        bottomButton.add(new InlineKeyboardButton().setText("\uD83D\uDCDE").setCallbackData("tl_Танцпол"));
        rowsInline.add(bottomButton);
        markupInline.setKeyboard(rowsInline);
        Check check = DatabaseManager.getCheck(userId);
        if (check == null) {
            check = new Check();
            check.setUserId(userId);
        }
        check.setRoom(call_data);
        DatabaseManager.setCheck(check);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        String answer = dateFormat.format(check.getDate()) + "\n" + check.getLocaltime() + "\n" + check.getRoom() + "\nОберіть стіл :";

        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(toIntExact(message_id))
                .setText(answer)
                .setReplyMarkup(markupInline);
        return new_message;
    }
}