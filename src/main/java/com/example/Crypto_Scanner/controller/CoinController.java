package com.example.Crypto_Scanner.controller;

import com.example.Crypto_Scanner.entity.Coin;
import com.example.Crypto_Scanner.service.CoinService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {

    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    // 1. Create a coin
    @PostMapping
    public ResponseEntity<Coin> createCoin(
            @RequestBody Coin coin) {

        Coin savedCoin = coinService.createCoin(coin);

        return ResponseEntity.ok(savedCoin);
    }

    // 2. Get all cryptocurrencies
    @GetMapping
    public ResponseEntity<List<Coin>> getAllCoins() {

        return ResponseEntity.ok(
                coinService.getAllCoins()
        );
    }

    // 3. Search coin by symbol
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<Coin> getCoinBySymbol(
            @PathVariable String symbol) {

        return ResponseEntity.ok(
                coinService.getCoinBySymbol(symbol)
        );
    }

    // 4. Search coin by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Coin> getCoinByName(
            @PathVariable String name) {

        return ResponseEntity.ok(
                coinService.getCoinByName(name)
        );
    }

    // 5. Get coin details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Coin> getCoinById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                coinService.getCoinById(id)
        );
    }

    // 6. Get active coins
    @GetMapping("/active")
    public ResponseEntity<List<Coin>> getActiveCoins() {

        return ResponseEntity.ok(
                coinService.getActiveCoins()
        );
    }

    // 7. Get inactive coins
    @GetMapping("/inactive")
    public ResponseEntity<List<Coin>> getInactiveCoins() {

        return ResponseEntity.ok(
                coinService.getInactiveCoins()
        );
    }

    // 8. Get coins by exchange
    @GetMapping("/exchange/{exchange}")
    public ResponseEntity<List<Coin>> getCoinsByExchange(
            @PathVariable String exchange) {

        return ResponseEntity.ok(
                coinService.getCoinsByExchange(exchange)
        );
    }
}