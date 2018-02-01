package io.fundrequest.tokensale.progress;

import io.fundrequest.tokensale.progress.dto.PaidEventDto;
import io.fundrequest.tokensale.progress.dto.TransferEventDto;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProgressServiceImpl implements ProgressService {

    private TransportClient transportClient;

    public ProgressServiceImpl(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    @EventListener
    public void updateEs(PaidEventDto paidEvent) {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("beneficiary", paidEvent.getBeneficiary());
            json.put("token_amount", paidEvent.getTokenAmount());
            json.put("timestamp", toLocalDateTime(paidEvent.getTimestamp()));
            json.put("transaction_hash", paidEvent.getTransactionHash());
            json.put("wei_amount", paidEvent.getWeiAmount());
            IndexRequestBuilder requestBuilder = transportClient.prepareIndex("fr-token-sale", "paid", paidEvent.getTransactionHash()).setSource(json);
            requestBuilder.get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventListener
    public void updateEs(TransferEventDto transferEvent) {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("from", transferEvent.getFrom());
            json.put("to", transferEvent.getTo());
            json.put("timestamp", toLocalDateTime(transferEvent.getTimestamp()));
            json.put("transaction_hash", transferEvent.getTransactionHash());
            json.put("amount", transferEvent.getAmount());
            IndexRequestBuilder requestBuilder = transportClient.prepareIndex("fr-token-sale", "transfer", transferEvent.getTransactionHash()).setSource(json);
            requestBuilder.get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LocalDateTime toLocalDateTime(Long time) {
        return time == null ? null :
                Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }
}
