package com.example.backend;

import com.example.backend.service.CurrencyService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

public class Currency {

    public static void main(String[] args) {
        Currency c = new Currency();
        System.out.println(c.convertCurrency("AUD", "VND", 20.0));
    }

    /**
     * get exchange rate of currencyCode entered.
     * Note that the result is in relation to USD
     * @param currencyCode input currency code
     * @return exchange rate
     */
    public Double getExchangeRate(String currencyCode) throws JSONException {

        if(currencyCode.isEmpty()) {
            System.out.println("Invalid currency code.");
            return 0.0;
        }

        currencyCode = currencyCode.toUpperCase();

        RestTemplate template = new RestTemplate();
        CurrencyService service = new CurrencyService(template);

        String exchangeRates = service.getExchangeRates();

        if(exchangeRates.isEmpty()) {
            return 0.0;
        }

        JSONObject object = new JSONObject(exchangeRates);

        double value;
        try {
            value = object.getJSONObject("rates").getDouble(currencyCode);
        } catch(JSONException exception) {
            System.out.println("Currency code not found.");
            value = 0;
        }

        return value;

    }

    /**
     * converts currency value
     * @param inputCurrencyCode this is the currency code of the original value
     * @param outputCurrencyCode currency code you wish to convert to
     * @param currentValue original value of currency to convert
     * @return converted currency value
     */
    public Double convertCurrency(String inputCurrencyCode, String outputCurrencyCode, Double currentValue) {

        /*
         * ERROR HANDLING
         */

        if(currentValue == 0) {
            return 0.0;
        } else if(currentValue < 0) {
            System.out.println("Invalid input currency");
            return 0.0;
        } else if(currentValue.isNaN()) {
            System.out.println("Current value not valid");
        }

        if(inputCurrencyCode == null || outputCurrencyCode == null) {
            System.out.println("Currency codes not valid.");
            return 0.0;
        }



        Currency currency = new Currency();
        double inputExchangeRate = currency.getExchangeRate(inputCurrencyCode);
        double outputExchangeRate = currency.getExchangeRate(outputCurrencyCode);

        double convert = (currentValue / inputExchangeRate) * outputExchangeRate;

        DecimalFormat format = new DecimalFormat("0.00");
        convert = Double.parseDouble(format.format(convert));
        return convert;


    }

}