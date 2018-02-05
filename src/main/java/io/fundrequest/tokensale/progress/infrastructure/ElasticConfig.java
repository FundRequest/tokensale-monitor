package io.fundrequest.tokensale.progress.infrastructure;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticConfig {

    @Bean
    public TransportClient transportClient(@Value("${elasticsearch.host}") String host, @Value("${elasticsearch.port}") int port) {
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "docker-cluster").build();
            TransportClient transportClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
            transportClient.admin().indices().create(new CreateIndexRequest("paid"));
            transportClient.admin().indices().create(new CreateIndexRequest("transfer"));
            return transportClient;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
