package bg.mironov.bogdan.quant.backend.model;

public record TradingSignal(
    String symbol,
    String signal,
    double price,
    long timestamp
) {
}
