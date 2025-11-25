package bg.mironov.bogdan.quant.ingestor.kafka;

import bg.mironov.bogdan.quant.ingestor.kafka.conf.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RawPriceProducer {
    private final KafkaTemplate<String, String> kafka;
    private final KafkaTopicsProperties topics;

    public RawPriceProducer(KafkaTemplate<String, String> kafka, KafkaTopicsProperties topics) {
        this.kafka = kafka;
        this.topics = topics;
    }

    public void send(String json) {
        kafka.send(topics.getRaw(), json);
    }
}
