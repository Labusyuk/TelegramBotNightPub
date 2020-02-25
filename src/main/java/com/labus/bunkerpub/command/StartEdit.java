package com.labus.bunkerpub.command;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.toIntExact;

public class StartEdit implements Command {
    private Update update;
    @Override
    public EditMessageText execute() {
        String text = EmojiParser.parseToUnicode("Оберіть послугу:\n");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(new InlineKeyboardButton().setText("Замовити стіл\uD83D\uDECE").setCallbackData("book_a_table"));
        row1.add(new InlineKeyboardButton().setText("Меню\uD83D\uDC68\u200D\uD83C\uDF73").setCallbackData("menu"));
        rowsInline.add(row1);
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Instagram\uD83D\uDDBC").setCallbackData("/insta")));
        rowsInline.add(Collections.singletonList(new InlineKeyboardButton().setText("Контакти\uD83D\uDCDE").setCallbackData("tl_")));
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        EditMessageText message = new EditMessageText() // Create a message object object
                .setMessageId(toIntExact(message_id))
                .setChatId(chat_id)
                .setText(text);
        message.setReplyMarkup(markupInline);
        return message;
    }

    @Override
    public void setUpdate(Update update) {
        this.update = update;
    }
}
