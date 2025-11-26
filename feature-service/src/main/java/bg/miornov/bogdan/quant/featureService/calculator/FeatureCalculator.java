package bg.miornov.bogdan.quant.featureService.calculator;

import bg.miornov.bogdan.quant.featureService.model.FeaturePrice;
import bg.miornov.bogdan.quant.featureService.model.RawPrice;
import org.springframework.stereotype.Component;

@Component
public class FeatureCalculator {
    private static final int HISTORY_CAPACITY = 50;

    private static final int SMA_SHORT = 5;
    private static final int SMA_MEDIUM = 20;
    private static final int SMA_LONG = 50;

    private static final int EMA_PERIOD = 20;
    private static final int VOL_WINDOW = 20;

    private static final int RETURN_1 = 1;
    private static final int RETURN_5 = 5;
    private static final int RETURN_10 = 10;

    private static final int RSI_PERIOD = 14;

    private final PriceHistory history = new PriceHistory(HISTORY_CAPACITY);

    public FeaturePrice compute(RawPrice raw) {
        double price = Double.parseDouble(raw.price());
        history.add(price);

        return new FeaturePrice(
            raw.symbol(),
            price,
            history.sma(SMA_SHORT),
            history.sma(SMA_MEDIUM),
            history.sma(SMA_LONG),
            history.ema(EMA_PERIOD),
            history.volatility(VOL_WINDOW),
            history.returnAt(RETURN_1),
            history.returnAt(RETURN_5),
            history.returnAt(RETURN_10),
            history.rsi(RSI_PERIOD)
        );
    }
}