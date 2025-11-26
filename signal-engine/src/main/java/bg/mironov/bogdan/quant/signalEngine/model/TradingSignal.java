package bg.mironov.bogdan.quant.signalEngine.model;

public record TradingSignal (
    String symbol,
    double price,
    String signal,
    long timestamp
) {}
