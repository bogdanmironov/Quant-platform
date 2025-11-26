package bg.mironov.bogdan.quant.signalEngine.processor;

import bg.mironov.bogdan.quant.signalEngine.kafka.SignalProducer;
import bg.mironov.bogdan.quant.signalEngine.model.FeaturePrice;
import bg.mironov.bogdan.quant.signalEngine.model.TradingSignal;
import org.springframework.stereotype.Component;

@Component
public class SignalProcessor {
    private final SignalProducer producer;

    public SignalProcessor(SignalProducer producer) {
        this.producer = producer;
    }

    public void process (FeaturePrice feature) {
        String signal;

        if (feature.rsi14() < 30) {
            signal = "BUY (RSI oversold)";
        } else if (feature.rsi14() > 80) {
            signal = "SELL (RSI overbought)";
        } else {
            signal = "HOLD";
        }

        TradingSignal out = new TradingSignal(feature.symbol(), feature.price(), signal);

        producer.send(out);
    }
}
