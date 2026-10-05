package com.example.Crypto_Scanner.service;

import com.example.Crypto_Scanner.entity.MarketData;
import com.example.Crypto_Scanner.repository.MarketDataRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketDataService {

    private final MarketDataRepository marketDataRepository;

    public MarketDataService(
            MarketDataRepository marketDataRepository) {

        this.marketDataRepository = marketDataRepository;
    }

    // Save market data
    public MarketData saveMarketData(MarketData marketData) {

        return marketDataRepository.save(marketData);
    }

    // Get latest market data for a coin
    public MarketData getLatestMarketData(String symbol) {

        return marketDataRepository
                .findTopBySymbolOrderByTimestampDesc(symbol)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Market data not found for: " + symbol
                        )
                );
    }

    // Get historical market data
    public List<MarketData> getHistoricalMarketData(
            String symbol) {

        return marketDataRepository
                .findBySymbolOrderByTimestampDesc(symbol);
    }
}