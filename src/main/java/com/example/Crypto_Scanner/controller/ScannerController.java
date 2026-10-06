package com.example.Crypto_Scanner.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Crypto_Scanner.entity.MarketData;
import com.example.Crypto_Scanner.service.CoinScannerService;

@RestController
@RequestMapping("/api/scanner")
public class ScannerController {

    private final CoinScannerService coinScannerService;

    public ScannerController(
            CoinScannerService coinScannerService) {

        this.coinScannerService = coinScannerService;
    }

    @GetMapping("/gainers")
    public List<MarketData> getTopGainers() {

        return coinScannerService.getTopGainers();
    }

    @GetMapping("/losers")
    public List<MarketData> getTopLosers() {

        return coinScannerService.getTopLosers();
    }

    @GetMapping("/high-volume")
    public List<MarketData> getHighVolumeCoins() {

        return coinScannerService.getHighVolumeCoins();
    }
}