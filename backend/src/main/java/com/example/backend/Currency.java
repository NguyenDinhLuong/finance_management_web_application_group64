package com.example.backend;

import com.example.backend.service.CurrencyService;
import org.springframework.web.client.RestTemplate;
import org.json.*;

public class Currency {

    public static void main(String[] args) {

    }


    /**
     * get exchange rate of currencyCode entered.
     * Note that the result is in relation to USD
     * @param currencyCode input currency code
     * @return exchange rate
     */
    public double getExchangeRate(String currencyCode) {
        RestTemplate template = new RestTemplate();
        CurrencyService service = new CurrencyService(template);

        String exchangeRates = service.getExchangeRates();

        JSONObject object = new JSONObject(exchangeRates);

        return object.getJSONObject("rates").getDouble("GBP");

    }

    public static double convertCurrency(String inputCurrencyCode, String outputCurrencyCode, double currentValue) {
        Currency currency = new Currency();
        double inputExchangeRate = currency.getExchangeRate(inputCurrencyCode);
//        double outputExchangeRate


    }

}