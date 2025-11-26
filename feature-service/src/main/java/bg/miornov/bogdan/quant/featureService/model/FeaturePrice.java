package bg.miornov.bogdan.quant.featureService.model;

public record FeaturePrice(
    String symbol,
    double price,
    double sma5,
    double sma20,
    double sma50,
    double ema,
    double volatility20,
    double returns1,
    double returns5,
    double returns10,
    double rsi14
) {}