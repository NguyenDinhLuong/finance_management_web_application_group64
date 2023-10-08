package com.example.backend;

import com.example.backend.service.CurrencyService;
import org.springframework.web.client.RestTemplate;
import org.json.*;

import java.text.DecimalFormat;

public class Currency {

    public static void main(String[] args) {
        Currency c = new Currency();
        System.out.println(c.convertCurrency("USD", "VND", 20));
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

        System.out.print(object);


        return object.getJSONObject("rates").getDouble(currencyCode);

    }

    /**
     * converts currency value
     * @param inputCurrencyCode this is the currency code of the original value
     * @param outputCurrencyCode currency code you wish to convert to
     * @param currentValue original value of currency to convert
     * @return converted currency value
     */
    public double convertCurrency(String inputCurrencyCode, String outputCurrencyCode, double currentValue) {
        Currency currency = new Currency();
        double inputExchangeRate = currency.getExchangeRate(inputCurrencyCode);
        double outputExchangeRate = currency.getExchangeRate(outputCurrencyCode);

        double convert = (currentValue / inputExchangeRate) * outputExchangeRate;

        DecimalFormat format = new DecimalFormat("0.00");
        convert = Double.parseDouble(format.format(convert));
        return convert;


    }

}