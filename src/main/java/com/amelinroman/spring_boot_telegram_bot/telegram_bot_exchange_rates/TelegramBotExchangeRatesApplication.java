package com.amelinroman.spring_boot_telegram_bot.telegram_bot_exchange_rates;

import com.amelinroman.spring_boot_telegram_bot.telegram_bot_exchange_rates.configuration.MyConfig;
import com.amelinroman.spring_boot_telegram_bot.telegram_bot_exchange_rates.service.CurrencyService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class TelegramBotExchangeRatesApplication {

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(TelegramBotExchangeRatesApplication.class, args);
    }

}
