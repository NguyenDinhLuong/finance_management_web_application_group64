package com.example.backend.security.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.text.DecimalFormat;

@Service
public class CurrencyExchangeService {
    @Autowired
    private final RestTemplate restTemplate;

    public CurrencyExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getExchangeRates() throws ResourceAccessException {
        String API_URL = "https://openexchangerates.org/api/latest.json?app_id=4552b74e7b1948acad92905d4886177a&base=USD&callback=someCallbackFunction";

        String response;
        try {
            response = restTemplate.getForObject(API_URL, String.class);
        } catch(ResourceAccessException e) {
            System.out.println("Problem accessing the exchange rate API. Check internet connection.");
            return "";
        }


        if(response != null) {
            response = response.replace("someCallbackFunction(", "");
            response = response.replace(")", "");
        }

        return response;
    }

    public Double rate(String inputCurrencyCode, String outputCurrencyCode) throws JSONException {

        if (inputCurrencyCode == null || outputCurrencyCode == null || inputCurrencyCode.isEmpty() || outputCurrencyCode.isEmpty()) {
            System.out.println("Currency codes not valid.");
            return 0.0;
        }

        inputCurrencyCode = inputCurrencyCode.toUpperCase();
        outputCurrencyCode = outputCurrencyCode.toUpperCase();

        String exchangeRates = getExchangeRates();

        if (exchangeRates.isEmpty()) {
            return 0.0;
        }

        JSONObject object = new JSONObject(exchangeRates);

        double inputExchangeRate;
        double outputExchangeRate;

        try {
            inputExchangeRate = object.getJSONObject("rates").getDouble(inputCurrencyCode);
            outputExchangeRate = object.getJSONObject("rates").getDouble(outputCurrencyCode);
        } catch (JSONException exception) {
            System.out.println("Currency code not found.");
            return 0.0;
        }

        double rate = outputExchangeRate/inputExchangeRate;

        DecimalFormat format = new DecimalFormat("0.00");
        rate = Double.parseDouble(format.format(rate));

        return rate;
    }

    public Double convertCurrency(String inputCurrencyCode, String outputCurrencyCode, Double currentValue) throws JSONException {
        if (currentValue == null || currentValue == 0 || currentValue < 0 || currentValue.isNaN()) {
            System.out.println("Invalid input currency value");
            return 0.0;
        }

        if (inputCurrencyCode == null || outputCurrencyCode == null || inputCurrencyCode.isEmpty() || outputCurrencyCode.isEmpty()) {
            System.out.println("Currency codes not valid.");
            return 0.0;
        }

        inputCurrencyCode = inputCurrencyCode.toUpperCase();
        outputCurrencyCode = outputCurrencyCode.toUpperCase();

        String exchangeRates = getExchangeRates();

        if (exchangeRates.isEmpty()) {
            return 0.0;
        }

        JSONObject object = new JSONObject(exchangeRates);

        double inputExchangeRate;
        double outputExchangeRate;

        try {
            inputExchangeRate = object.getJSONObject("rates").getDouble(inputCurrencyCode);
            outputExchangeRate = object.getJSONObject("rates").getDouble(outputCurrencyCode);
        } catch (JSONException exception) {
            System.out.println("Currency code not found.");
            return 0.0;
        }

        double convert = (currentValue / inputExchangeRate) * outputExchangeRate;

        DecimalFormat format = new DecimalFormat("0.00");
        convert = Double.parseDouble(format.format(convert));

        return convert;
    }
}
