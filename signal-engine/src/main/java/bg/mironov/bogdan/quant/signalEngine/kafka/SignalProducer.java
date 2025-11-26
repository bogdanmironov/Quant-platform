package bg.mironov.bogdan.quant.signalEngine.kafka;

import bg.mironov.bogdan.quant.signalEngine.model.TradingSignal;
import bg.mironov.bogdan.quant.signalEngine.util.util.JsonHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SignalProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topics.signals}")
    private String signalsTopic;

    public SignalProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(TradingSignal s) {
        kafkaTemplate.send(signalsTopic, JsonHelper.toJson(s));
    }
}
