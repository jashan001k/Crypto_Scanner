package com.example.Crypto_Scanner.repository;

import com.example.Crypto_Scanner.entity.Coin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CoinRepository extends MongoRepository<Coin, String> {

    Optional<Coin> findBySymbolIgnoreCase(String symbol);

    Optional<Coin> findByNameIgnoreCase(String name);

    List<Coin> findByActive(boolean active);
}