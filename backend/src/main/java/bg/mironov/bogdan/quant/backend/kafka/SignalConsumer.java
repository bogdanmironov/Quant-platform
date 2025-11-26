package bg.mironov.bogdan.quant.backend.kafka;

import bg.mironov.bogdan.quant.backend.model.TradingSignal;
import bg.mironov.bogdan.quant.backend.service.SignalIndexService;
import bg.mironov.bogdan.quant.backend.util.JsonHelper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SignalConsumer {
    private final SignalIndexService indexService;

    public SignalConsumer(SignalIndexService indexService) {
        this.indexService = indexService;
    }

    @KafkaListener(topics = "${app.kafka.topics.signals}")
    public void consume(String json) {
        TradingSignal signal = JsonHelper.fromJson(json, TradingSignal.class);
        indexService.indexSignal(signal);
    }
}
