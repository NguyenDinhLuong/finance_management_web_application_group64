package com.example.backend.security;

import com.example.backend.controller.CurrencyController;
import com.example.backend.security.services.CurrencyExchangeService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private CurrencyController currencyController;

    private MockMvc mockMvc;

    public CurrencyControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    public void testGetRate() throws Exception {
        String inputCurrency = "USD";
        String outputCurrency = "EUR";
        Double expectedRate = 0.85;

        when(currencyExchangeService.rate(inputCurrency, outputCurrency)).thenReturn(expectedRate);

        mockMvc.perform(get("/api/currency/rate/" + inputCurrency + "/" + outputCurrency))
                .andExpect(status().isOk())
                .andExpect(content().string("0.85"));
    }

    @Test
    public void testGetRateWithJSONException() throws Exception {
        String inputCurrency = "USD";
        String outputCurrency = "EUR";

        when(currencyExchangeService.rate(inputCurrency, outputCurrency)).thenThrow(new JSONException("JSON Exception"));

        mockMvc.perform(get("/api/currency/rate/" + inputCurrency + "/" + outputCurrency))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));
    }
}
