package io.fundrequest.tokensale.progress;

import io.fundrequest.tokensale.progress.dto.PaidEventDto;
import io.fundrequest.tokensale.progress.dto.TransferEventDto;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class ProgressServiceImpl implements ProgressService {

    private TransportClient transportClient;

    private static final Logger logger = LoggerFactory.getLogger(ProgressService.class);

    public ProgressServiceImpl(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    @EventListener
    public void updateEs(PaidEventDto paidEvent) {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("beneficiary", paidEvent.getBeneficiary());
            json.put("timestamp", toLocalDateTime(paidEvent.getTimestamp()));
            json.put("transaction_hash", paidEvent.getTransactionHash());
            if (StringUtils.isNotBlank(paidEvent.getTokenAmount())) {
                json.put("token_amount", new BigDecimal(paidEvent.getTokenAmount()).doubleValue());
            }
            if (StringUtils.isNotBlank(paidEvent.getWeiAmount())) {
                json.put("wei_amount", new BigDecimal(paidEvent.getWeiAmount()).doubleValue());
            }
            json.put("personal_cap_active", paidEvent.getPersonalCapActive());
            IndexRequestBuilder requestBuilder = transportClient.prepareIndex("paid", "paid", paidEvent.getTransactionHash()).setSource(json);
            requestBuilder.get();

        } catch (Exception e) {
            logger.error("error when updating es", e);
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
            if (StringUtils.isNotBlank(transferEvent.getAmount())) {
                json.put("amount", new BigDecimal(transferEvent.getAmount()).doubleValue());
            }
            IndexRequestBuilder requestBuilder = transportClient.prepareIndex("transfer", "transfer", transferEvent.getTransactionHash()).setSource(json);
            requestBuilder.get();

        } catch (Exception e) {
            logger.error("error when updating es", e);
        }
    }

    @Override
    public boolean transactionIsAlreadyProcessed(PaidEventDto paidEvent) {
        try {
            return transportClient.get(new GetRequest("paid", "paid", paidEvent.getTransactionHash())).get().isExists();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("error when contacting es", e);
            return false;
        }
    }

    @Override
    public boolean transactionIsAlreadyProcessed(TransferEventDto transferEvent) {
        try {
            return transportClient.get(new GetRequest("transfer", "transfer", transferEvent.getTransactionHash())).get().isExists();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("error when contacting es", e);
            return false;
        }
    }

    private LocalDateTime toLocalDateTime(Long time) {
        return time == null ? null :
                Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }
}
