package bg.mironov.bogdan.quant.backend.config;

import org.apache.hc.core5.http.HttpHost;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig {
    @Value("${app.opensearch.host}")
    private String host;

    @Value("${app.opensearch.port}")
    private int port;

    @Value("${app.opensearch.scheme}")
    private String scheme;

    @Bean
    public OpenSearchClient openSearchClient() {
        HttpHost httpHost = new HttpHost(scheme, host, port);

        OpenSearchTransport transport = ApacheHttpClient5TransportBuilder
            .builder(httpHost)
            .build();

        return new OpenSearchClient(transport);
    }
}
