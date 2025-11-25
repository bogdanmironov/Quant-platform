package bg.mironov.bogdan.quant.ingestor.http;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MarketClient {
    private static final String ENDPOINT_BTCUSDT = "/api/v3/ticker/price?symbol=BTCUSDT";

    private final WebClient client = WebClient.create("https://api.binance.com");

    public String getPrice() {
        return client.get()
            .uri(ENDPOINT_BTCUSDT)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

}
