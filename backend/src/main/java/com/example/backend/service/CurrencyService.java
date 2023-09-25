package com.example.backend.service;/*
Handle communication between openexchangerates
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class CurrencyService {
    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getExchangeRates() {
        String API_URL = "https://openexchangerates.org/api/latest.json?app_id=371446c007bb4489a465d02220f4d516&base=USD&callback=someCallbackFunction";
        String response = restTemplate.getForObject(API_URL, String.class);

        if(response != null) {
            response = response.replace("someCallbackFunction(", "");
            response = response.replace(")", "");
        }

        return response;

    }
}