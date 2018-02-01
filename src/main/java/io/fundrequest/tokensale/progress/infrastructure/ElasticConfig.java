package io.fundrequest.tokensale.progress.infrastructure;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticConfig {

    @Bean
    public TransportClient transportClient() {
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "docker-cluster").build();
            TransportClient transportClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getLocalHost(), 9300));
            transportClient.admin().indices().create(new CreateIndexRequest("fundrequest"));
            return transportClient;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
