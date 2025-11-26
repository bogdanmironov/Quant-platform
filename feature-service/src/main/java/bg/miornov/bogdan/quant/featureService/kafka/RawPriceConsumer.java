package bg.miornov.bogdan.quant.featureService.kafka;

import bg.miornov.bogdan.quant.featureService.processor.RawPriceProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RawPriceConsumer {
    private final RawPriceProcessor processor;

    public RawPriceConsumer(RawPriceProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(topics = "${app.kafka.topics.raw}", groupId = "${app.kafka.groups.feature}")
    public void consume(String json) {
        processor.process(json);
    }

}
