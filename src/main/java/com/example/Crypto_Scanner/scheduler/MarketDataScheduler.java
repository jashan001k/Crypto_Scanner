package com.example.Crypto_Scanner.scheduler;

import com.example.Crypto_Scanner.service.CryptoApiService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarketDataScheduler {

    private final CryptoApiService cryptoApiService;

    private final String[] symbols = {
            "BTCUSDT",
            "ETHUSDT",
            "BNBUSDT",
            "SOLUSDT",
            "XRPUSDT"
    };

    public MarketDataScheduler(CryptoApiService cryptoApiService) {
        this.cryptoApiService = cryptoApiService;
    }

    @Scheduled(fixedRate = 10000)
    public void updateMarketData() {

        for (String symbol : symbols) {

            try {

                cryptoApiService.fetchAndSaveMarketData(symbol);

                System.out.println(
                        symbol + " market data updated successfully."
                );

            } catch (Exception e) {

                System.out.println(
                        "Failed to update "
                                + symbol
                                + ": "
                                + e.getMessage()
                );
            }
        }
    }
}