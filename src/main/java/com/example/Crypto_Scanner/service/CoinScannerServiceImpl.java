package com.example.Crypto_Scanner.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Crypto_Scanner.entity.MarketData;
import com.example.Crypto_Scanner.repository.MarketDataRepository;

@Service
public class CoinScannerServiceImpl implements CoinScannerService {

    private final MarketDataRepository marketDataRepository;

    public CoinScannerServiceImpl(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
    }

    @Override
    public List<MarketData> getTopGainers() {

        return marketDataRepository.findAll()
                .stream()
                .sorted(
                    Comparator.comparing(
                        MarketData::getPriceChange24h
                    ).reversed()
                )
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarketData> getTopLosers() {

        return marketDataRepository.findAll()
                .stream()
                .sorted(
                    Comparator.comparing(
                        MarketData::getPriceChange24h
                    )
                )
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarketData> getHighVolumeCoins() {

        return marketDataRepository.findAll()
                .stream()
                .sorted(
                    Comparator.comparing(
                        MarketData::getVolume24h
                    ).reversed()
                )
                .limit(10)
                .collect(Collectors.toList());
    }
}