package com.example.Crypto_Scanner.service;

import java.util.List;

import com.example.Crypto_Scanner.entity.MarketData;

public interface CoinScannerService {

    List<MarketData> getTopGainers();

    List<MarketData> getTopLosers();

    List<MarketData> getHighVolumeCoins();
}