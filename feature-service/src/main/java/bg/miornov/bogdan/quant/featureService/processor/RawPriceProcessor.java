package bg.miornov.bogdan.quant.featureService.processor;

import bg.miornov.bogdan.quant.featureService.calculator.FeatureCalculator;
import bg.miornov.bogdan.quant.featureService.kafka.FeatureProducer;
import bg.miornov.bogdan.quant.featureService.model.FeaturePrice;
import bg.miornov.bogdan.quant.featureService.model.RawPrice;
import bg.miornov.bogdan.quant.featureService.util.JsonHelper;
import org.springframework.stereotype.Component;

@Component
public class RawPriceProcessor {
    private final FeatureCalculator calculator;
    private final FeatureProducer producer;

    public RawPriceProcessor(FeatureCalculator calculator, FeatureProducer producer) {
        this.calculator = calculator;
        this.producer = producer;
    }

    public void process(String json) {
        RawPrice raw = deserialize(json);
        FeaturePrice feature = calculator.compute(raw);
        producer.send(feature);
    }

    private RawPrice deserialize(String json) {
        try {
            return JsonHelper.fromJson(json, RawPrice.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON:\n" + json, e);
        }
    }
}
