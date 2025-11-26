package bg.mironov.bogdan.quant.backend.service;

import bg.mironov.bogdan.quant.backend.model.TradingSignal;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SignalIndexService {
    @Value("${app.opensearch.index-signals}")
    private String indexName;

    private final OpenSearchClient client;

    public SignalIndexService(OpenSearchClient client) {
        this.client = client;
    }

    public void indexSignal(TradingSignal signal) {
        try {
            client.index(i -> i
                .index(indexName)
                .id(signal.timestamp() + '_' + signal.symbol())
                .document(signal)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
