package bg.miornov.bogdan.quant.featureService.calculator;

import java.util.ArrayDeque;
import java.util.Deque;

public class PriceHistory {
    private final int capacity;
    private final Deque<Double> prices = new ArrayDeque<>();

    public PriceHistory(int capacity) {
        this.capacity = capacity;
    }

    public void add(double price) {
        if (prices.size() == capacity) {
            prices.removeFirst();
        }
        prices.addLast(price);
    }

    public double sma(int window) {
        if (prices.size() < window) return Double.NaN;

        return prices.stream()
            .skip(prices.size() - window)
            .mapToDouble(v -> v)
            .average()
            .orElse(Double.NaN);
    }

    public double ema(int window) {
        if (prices.size() < window) return Double.NaN;

        double alpha = 2.0 / (window + 1);

        return prices.stream()
            .skip(prices.size() - window)
            .reduce(sma(window), (acc, curr) -> alpha*curr + (1-alpha)*acc);
    }

    public double volatility(int window) {
        if (prices.size() < window) return Double.NaN;

        double avg = prices.stream()
            .skip(prices.size() - window)
            .mapToDouble(v -> v)
            .summaryStatistics()
            .getAverage();

        double variance = prices.stream()
            .skip(prices.size() - window)
            .mapToDouble(p -> p)
            .map(p -> (p - avg) * (p - avg))
            .average()
            .orElse(Double.NaN);

        return Math.sqrt(variance);
    }

    public double returnAt(int n) {
        if (prices.size() <= n) return Double.NaN;

        double last = prices.getLast();
        double old = prices.stream()
            .skip(prices.size() - n - 1)
            .findFirst()
            .orElse(Double.NaN);

        return (last - old) / old;
    }

    public double rsi(int window) {
        if (prices.size() < window + 1) return Double.NaN;

        double gains = 0;
        double losses = 0;

        Double prev = null;

        int skip = prices.size() - (window + 1);

        int i = 0;
        for (double p : prices) {
            if (i++ < skip) continue;

            if (prev != null) {
                double diff = p - prev;

                if (diff > 0) gains += diff;
                else losses -= diff;
            }

            prev = p;
        }

        double avgGain = gains / window;
        double avgLoss = losses / window;

        if (avgLoss == 0) return 100;

        double rs = avgGain / avgLoss;

        return 100 - (100 / (1 + rs));
    }
}
