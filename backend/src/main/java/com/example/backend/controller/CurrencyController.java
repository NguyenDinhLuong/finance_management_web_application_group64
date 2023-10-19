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

    @GetMapping("/rate/{inputCurrency}/{outputCurrency}")
    public float getRate(@PathVariable String inputCurrency, @PathVariable String outputCurrency) {
        try {
            return currencyExchangeService.rate(inputCurrency, outputCurrency).floatValue();
        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0F;
        }
    }
}
