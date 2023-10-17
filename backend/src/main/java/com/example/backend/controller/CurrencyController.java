package com.example.backend.controller;

import com.example.backend.security.services.CurrencyExchangeService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping("/convert")
    public Float convertCurrency(
            @RequestParam String inputCurrency,
            @RequestParam String outputCurrency,
            @RequestParam Double value) {

        try {
            return currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, value).floatValue();
        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0F;
        }
    }

}
