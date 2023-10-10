package com.example.backend.service;/*
Handle communication between openexchangerates
 */

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;



@Service
public class CurrencyService {
    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getExchangeRates() throws ResourceAccessException {
        String API_URL = "https://openexchangerates.org/api/latest.json?app_id=371446c007bb4489a465d02220f4d516&base=USD&callback=someCallbackFunction";

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
}