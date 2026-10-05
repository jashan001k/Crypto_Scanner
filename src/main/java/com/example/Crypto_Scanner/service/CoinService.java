package com.example.Crypto_Scanner.service;

import com.example.Crypto_Scanner.entity.Coin;
import com.example.Crypto_Scanner.repository.CoinRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinService {

    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    // Create a new coin
    public Coin createCoin(Coin coin) {

        coin.setLastUpdated(System.currentTimeMillis());

        return coinRepository.save(coin);
    }

    // Get all cryptocurrencies
    public List<Coin> getAllCoins() {

        return coinRepository.findAll();
    }

    // Get coin by ID - Coin Details
    public Coin getCoinById(String id) {

        return coinRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Coin not found"));
    }

    // Get coin by symbol
    public Coin getCoinBySymbol(String symbol) {

        return coinRepository.findBySymbolIgnoreCase(symbol)
                .orElseThrow(() ->
                        new RuntimeException("Coin not found"));
    }

    // Get coin by name
    public Coin getCoinByName(String name) {

        return coinRepository.findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new RuntimeException("Coin not found"));
    }

    // Get active coins
    public List<Coin> getActiveCoins() {

        return coinRepository.findByActive(true);
    }

    // Get inactive coins
    public List<Coin> getInactiveCoins() {

        return coinRepository.findByActive(false);
    }

    // Get coins available on an exchange
    public List<Coin> getCoinsByExchange(String exchange) {

        return coinRepository.findAll()
                .stream()
                .filter(coin ->
                        coin.getExchangeSymbols() != null &&
                        coin.getExchangeSymbols().containsKey(exchange)
                )
                .toList();
    }
}