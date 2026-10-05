package com.example.Crypto_Scanner.controller;

import com.example.Crypto_Scanner.entity.MarketData;
import com.example.Crypto_Scanner.service.CryptoApiService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crypto-api")
public class CryptoApiController {

    private final CryptoApiService cryptoApiService;

    public CryptoApiController(CryptoApiService cryptoApiService) {
        this.cryptoApiService = cryptoApiService;
    }

    // Get raw 24-hour market data from Binance
    @GetMapping("/24h/{symbol}")
    public ResponseEntity<String> get24HourMarketData(
            @PathVariable String symbol) {

        String data =
                cryptoApiService.get24HourMarketData(symbol);

        return ResponseEntity.ok(data);
    }

    // Fetch market data from Binance and save it to MongoDB
    @GetMapping("/save/{symbol}")
    public ResponseEntity<MarketData> fetchAndSaveMarketData(
            @PathVariable String symbol) {

        MarketData marketData =
                cryptoApiService.fetchAndSaveMarketData(symbol);

        return ResponseEntity.ok(marketData);
    }
}