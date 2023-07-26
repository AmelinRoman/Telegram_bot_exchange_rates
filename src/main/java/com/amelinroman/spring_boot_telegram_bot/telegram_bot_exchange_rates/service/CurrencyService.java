package com.amelinroman.spring_boot_telegram_bot.telegram_bot_exchange_rates.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CurrencyService {

    private RestTemplate restTemplate;
    private final String URL = "https://www.cbr-xml-daily.ru/daily_json.js";

    public String getCurrentRate(String currencyCode) {
        var answer = "";
        String jsonString = restTemplate.getForObject(URL, String.class);
        if((jsonString == null) || (jsonString.equals(""))) {
            return "На данных момент сервис не доступен";
        } else {
            try {
                JsonNode rootNode = new ObjectMapper().readTree(jsonString);
                JsonNode valuteNode = rootNode.get("Valute");
                var value = valuteNode.get(currencyCode).get("Value").doubleValue();
                answer = "Официальный курс "+ currencyCode+"  на сегодня: "+ value + " RUB";
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return answer;
        }
    }




}
