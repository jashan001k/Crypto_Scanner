package com.example.Crypto_Scanner.service;

import com.example.Crypto_Scanner.entity.MarketData;
import com.example.Crypto_Scanner.repository.MarketDataRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CryptoApiService {

    private final WebClient webClient;
    private final MarketDataRepository marketDataRepository;
    private final ObjectMapper objectMapper;

    public CryptoApiService(
            WebClient.Builder webClientBuilder,
            MarketDataRepository marketDataRepository,
            ObjectMapper objectMapper) {

        this.webClient = webClientBuilder
                .baseUrl("https://api.binance.com")
                .build();

        this.marketDataRepository = marketDataRepository;
        this.objectMapper = objectMapper;
    }

    public String get24HourMarketData(String symbol) {

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/ticker/24hr")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public MarketData fetchAndSaveMarketData(String symbol) {

        try {

            String response = get24HourMarketData(symbol);

            JsonNode data = objectMapper.readTree(response);

            MarketData marketData = new MarketData();

            marketData.setSymbol(data.get("symbol").asText());

            marketData.setCurrentPrice(
                    Double.parseDouble(
                            data.get("lastPrice").asText()
                    )
            );

            marketData.setPriceChange24h(
                    Double.parseDouble(
                            data.get("priceChangePercent").asText()
                    )
            );

            marketData.setHigh24h(
                    Double.parseDouble(
                            data.get("highPrice").asText()
                    )
            );

            marketData.setLow24h(
                    Double.parseDouble(
                            data.get("lowPrice").asText()
                    )
            );

            marketData.setVolume24h(
                    Double.parseDouble(
                            data.get("volume").asText()
                    )
            );

            marketData.setBidPrice(
                    Double.parseDouble(
                            data.get("bidPrice").asText()
                    )
            );

            marketData.setAskPrice(
                    Double.parseDouble(
                            data.get("askPrice").asText()
                    )
            );

            marketData.setTimestamp(
                    System.currentTimeMillis()
            );

            return marketDataRepository.save(marketData);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to fetch market data: "
                            + e.getMessage()
            );
        }
    }
}