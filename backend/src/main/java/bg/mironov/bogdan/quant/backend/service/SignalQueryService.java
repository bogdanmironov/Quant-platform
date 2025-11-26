package bg.mironov.bogdan.quant.backend.service;

import bg.mironov.bogdan.quant.backend.model.TradingSignal;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SignalQueryService {
    @Value("${app.opensearch.index-signals}")
    private String indexName;

    private final OpenSearchClient openSearchClient;

    public SignalQueryService(OpenSearchClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    public List<TradingSignal> getLatestSignals(int limit) {
        try {
            SearchResponse<TradingSignal> response = openSearchClient.search(s -> s
                    .index(indexName)
                    .sort(sort -> sort.field(f -> f.field("timestamp").order(SortOrder.Desc)))
                    .size(limit),
                TradingSignal.class);

            return response.hits().hits().stream()
                .map(Hit::source)
                .toList();
        } catch (Exception e) {
            throw new RuntimeException("OpenSearch error", e);
        }
    }

    public List<TradingSignal> getSignalsBySymbol(String symbol) {
        try {
            var response = openSearchClient.search(s -> s
                .index(indexName)
                .query(q -> q
                    .match(m -> m
                        .field("symbol")
                        .query(FieldValue.of(symbol))
                    )
                ),
                TradingSignal.class);

            return response.hits().hits().stream()
                .map(Hit::source)
                .toList();
        } catch (IOException e) {
            throw new RuntimeException("OpenSearch error", e);
        }
    }

    public List<TradingSignal> getSignalsByType(String type) {
        try {
            var response = openSearchClient.search(s -> s
                    .index(indexName)
                    .query(q -> q
                        .match(m -> m
                            .field("signal")
                            .query(FieldValue.of(type))
                        )
                    ),
                TradingSignal.class);

            return response.hits().hits().stream()
                .map(Hit::source)
                .toList();

        } catch (Exception e) {
            throw new RuntimeException("OpenSearch error", e);
        }
    }

}
