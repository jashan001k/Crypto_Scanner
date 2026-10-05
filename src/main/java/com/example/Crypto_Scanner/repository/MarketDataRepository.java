package com.example.Crypto_Scanner.repository;

import com.example.Crypto_Scanner.entity.MarketData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MarketDataRepository
        extends MongoRepository<MarketData, String> {

    Optional<MarketData> findTopBySymbolOrderByTimestampDesc(
            String symbol);

    List<MarketData> findBySymbolOrderByTimestampDesc(
            String symbol);
}