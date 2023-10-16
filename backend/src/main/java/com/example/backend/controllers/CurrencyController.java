package com.example.backend.controllers;

import com.example.backend.Currency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyController {
    @GetMapping("/conversion")
    public Double getCurrencyConversion(
            @RequestParam String inputCurrencyCode,
            @RequestParam String outputCurrencyCode,
            @RequestParam Double inputCurrencyValue) {

        Currency currency = new Currency();
        return currency.convertCurrency(inputCurrencyCode, outputCurrencyCode, inputCurrencyValue);

    }

}
