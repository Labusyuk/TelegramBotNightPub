package com.labus.bunkerpub.command;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    public static final Map<String, Command> commandsMap = new HashMap<>();

    static {
        commandsMap.put("/start", new ShowStart());
        commandsMap.put("start", new StartEdit());
        commandsMap.put("/help", null);
        commandsMap.put("book_a_table", new ChangeDay());
        commandsMap.put("cd", new ChangeTime());
        commandsMap.put("ct", new ChangePlace());
        commandsMap.put("Ресторан", new Restoran());
        commandsMap.put("Кальяна", new Hookah());
        commandsMap.put("Танцпол", new Dancefloor());
        commandsMap.put("tl", new GetContacts());
        commandsMap.put("/insta", new Instagram());
    }

    public static Command defineCommand(Update update) {
        String text = null;
        if (update.hasMessage() && update.getMessage().hasText())
            text = update.getMessage().getText();
        if(update.hasCallbackQuery() && update.getCallbackQuery().getData().isEmpty()==false) {
            text = update.getCallbackQuery().getData();
            if(text.length()>2 && text.substring(0,3).equals("cd_")) text="cd";
            if(text.length()>2 && text.substring(0,3).equals("ct_")) text="ct";
            if(text.length()>2 && text.substring(0,3).equals("tl_")) text="tl";
        }
        Command command = commandsMap.get(text);
        if(command!=null)command.setUpdate(update);
        return command;
    }
}
