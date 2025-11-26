package bg.mironov.bogdan.quant.signalEngine.kafka;

import bg.mironov.bogdan.quant.signalEngine.model.FeaturePrice;
import bg.mironov.bogdan.quant.signalEngine.processor.SignalProcessor;
import bg.mironov.bogdan.quant.signalEngine.util.util.JsonHelper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FeatureConsumer {
    private final SignalProcessor processor;

    public FeatureConsumer(SignalProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(topics = "${app.kafka.topics.features}")
    public void consume(String json) {
        FeaturePrice feature = JsonHelper.fromJson(json, FeaturePrice.class);
        processor.process(feature);
    }
}
