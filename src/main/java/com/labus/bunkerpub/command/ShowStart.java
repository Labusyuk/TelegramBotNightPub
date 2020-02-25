package com.labus.bunkerpub.command;

import com.vdurmont.emoji.EmojiParser;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ShowStart implements Command {
    private Update update;
    private SendMessage message;
    @Override
    public SendMessage execute() {
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
        long chat_id = update.getMessage().getChatId();
        message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(text);
        message.setReplyMarkup(markupInline);
        return message;
    }
}
