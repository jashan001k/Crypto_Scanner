package com.example.Crypto_Scanner.controller;

import com.example.Crypto_Scanner.entity.MarketData;
import com.example.Crypto_Scanner.service.MarketDataService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(
            MarketDataService marketDataService) {

        this.marketDataService = marketDataService;
    }

    // Save market data
    @PostMapping
    public ResponseEntity<MarketData> saveMarketData(
            @RequestBody MarketData marketData) {

        return ResponseEntity.ok(
                marketDataService.saveMarketData(marketData)
        );
    }

    // Get latest market data
    @GetMapping("/{symbol}")
    public ResponseEntity<MarketData> getLatestMarketData(
            @PathVariable String symbol) {

        return ResponseEntity.ok(
                marketDataService.getLatestMarketData(symbol)
        );
    }

    // Get historical market data
    @GetMapping("/{symbol}/history")
    public ResponseEntity<List<MarketData>> getHistoricalMarketData(
            @PathVariable String symbol) {

        return ResponseEntity.ok(
                marketDataService.getHistoricalMarketData(symbol)
        );
    }
}