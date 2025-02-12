package com.IHKProject.finance_simulation.Service;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.util.apitype.MarketDataWebsocketSourceType;
import net.jacobpeterson.alpaca.model.util.apitype.TraderAPIEndpointType;
import net.jacobpeterson.alpaca.openapi.marketdata.ApiException;
import net.jacobpeterson.alpaca.openapi.marketdata.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlpacaService {
    final String keyID = "PK32V7NPUQUMQF834AVD";
    final String secretKey = "IB1dMzHIOcZ4eg5ZU05r1DiY4OFAaYHjFauFO01E";
    final TraderAPIEndpointType endpointType = TraderAPIEndpointType.PAPER; // or 'LIVE'
    final MarketDataWebsocketSourceType sourceType = MarketDataWebsocketSourceType.IEX; // or 'SIP'
    final AlpacaAPI alpacaAPI = new AlpacaAPI(keyID, secretKey, endpointType, sourceType);


    // Gibt den letzten Trade in Echtzeit zur Aktie an.
    public void printLatestTSLATrade() {
        try {
            StockTrade latestTSLATrade = alpacaAPI.marketData().stock()
                    .stockLatestTradeSingle("AAPL", StockFeed.IEX, null)
                    .getTrade();
            
            System.out.printf("Latest TSLA trade: price=%s, size=%s\n", 
                              latestTSLATrade.getP(), latestTSLATrade.getS());
            System.out.println("time: "+ latestTSLATrade.getT());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gibt die Daten zum Opening und Closing des MARKTS zur Aktie an.
    //Opening: 14:30    Closing: 21:00
    public void stockAuctionApple() {
        OffsetDateTime start = OffsetDateTime.parse("2025-02-11T00:00:00+00:00");
        OffsetDateTime end = OffsetDateTime.parse("2025-02-11T23:59:59+00:00");

        try {
            List<StockDailyAuctions> auctionsList = alpacaAPI.marketData()
                .stock()
                .stockAuctionSingle(
                    "AAPL", start, end, 100L, null, StockFeed.SIP, "USD", null, Sort.ASC)
                .getAuctions();

            //Gibt die OpeningPreise an
            auctionsList.forEach(dailyAuction -> {
                // Iteriere über die Liste von StockAuction innerhalb jedes StockDailyAuctions
                dailyAuction.getO().forEach(stockAuction -> {
                    // Zugriff auf das Preisfeld 'p' in jedem StockAuction
                    Double price = stockAuction.getP();
                    // Ausgabe oder Verarbeitungslogik für den Preis
                    System.out.println("Price: " + price);
                });
            });

            //Gibt die ClosingPreise an
            auctionsList.forEach(dailyAuction -> {
                // Iteriere über die Liste von StockAuction innerhalb jedes StockDailyAuctions
                dailyAuction.getC().forEach(stockAuction -> {
                    // Zugriff auf das Preisfeld 'p' in jedem StockAuction
                    Double price = stockAuction.getP();
                    // Ausgabe oder Verarbeitungslogik für den Preis
                    System.out.println("Price: " + price);
                });
            });


        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    
}