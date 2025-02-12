package com.IHKProject.finance_simulation;
import com.IHKProject.finance_simulation.Service.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.util.apitype.MarketDataWebsocketSourceType;
import net.jacobpeterson.alpaca.model.util.apitype.TraderAPIEndpointType;

@SpringBootApplication
public class FinanceSimulationApplication {

	final String keyID = "<your_key_id>";
	final String secretKey = "<your_secret_key>";
	final TraderAPIEndpointType endpointType = TraderAPIEndpointType.PAPER; // or 'LIVE'
	final MarketDataWebsocketSourceType sourceType = MarketDataWebsocketSourceType.IEX; // or 'SIP'
	final AlpacaAPI alpacaAPI = new AlpacaAPI(keyID, secretKey, endpointType, sourceType);
	public static void main(String[] args) {
		SpringApplication.run(FinanceSimulationApplication.class, args);
		
	}

    @Bean
    public CommandLineRunner demo(AlpacaService alpacaService) {
        return args -> {
            alpacaService.printLatestTSLATrade();
			alpacaService.stockAuctionApple();
        };
    }
	

}
