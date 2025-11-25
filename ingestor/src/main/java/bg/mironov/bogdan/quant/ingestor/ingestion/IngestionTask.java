package bg.mironov.bogdan.quant.ingestor.ingestion;

import bg.mironov.bogdan.quant.ingestor.http.MarketClient;
import bg.mironov.bogdan.quant.ingestor.kafka.RawPriceProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IngestionTask {
    private final MarketClient client;
    private final RawPriceProducer producer;

    public IngestionTask(MarketClient client, RawPriceProducer producer) {
        this.client = client;
        this.producer = producer;
    }

    @Scheduled(fixedRate = 2000)
    public void ingest() {
        String priceJson = client.getPrice();
        producer.send(priceJson);
        System.out.println("Sent to Kafka: " + priceJson);
    }

}
