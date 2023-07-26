package com.amelinroman.spring_boot_telegram_bot.telegram_bot_exchange_rates.bot;

import com.amelinroman.spring_boot_telegram_bot.telegram_bot_exchange_rates.configuration.BotConfig;
import com.amelinroman.spring_boot_telegram_bot.telegram_bot_exchange_rates.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramBot extends TelegramLongPollingBot {

    private BotConfig botConfig;
    private CurrencyService service;

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String currency = "";

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                default -> {
                    currency = service.getCurrentRate(messageText);
                    sendMessage(chatId, currency);
                }
            }
        }

    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Привет, " + name + ", рад тебя видеть!" + "\n" +
                "Введите валюту, официальный курс которой" + "\n" +
                "вы хотите знать в отношении RUB." + "\n" +
                "Например: USD";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) { }
    }
}
