package bg.miornov.bogdan.quant.featureService.kafka;

import bg.miornov.bogdan.quant.featureService.model.FeaturePrice;
import bg.miornov.bogdan.quant.featureService.util.JsonHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeatureProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topics.features}")
    private String topic;

    public FeatureProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(FeaturePrice feature) {
        kafkaTemplate.send(topic, JsonHelper.toJson(feature));
    }
}
