package io.fundrequest.tokensale.progress;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fundrequest.tokensale.progress.dto.PaidEventDto;
import io.fundrequest.tokensale.progress.dto.TransferEventDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AzraelMessageListener {

    private ObjectMapper objectMapper;
    private ApplicationEventPublisher eventPublisher;
    private ProgressService progressService;

    public AzraelMessageListener(ObjectMapper objectMapper, ApplicationEventPublisher eventPublisher, ProgressService progressService) {
        this.objectMapper = objectMapper;
        this.eventPublisher = eventPublisher;
        this.progressService = progressService;
    }

    public void receivePaidMessage(String message) throws IOException {
        PaidEventDto result = objectMapper.readValue(message, PaidEventDto.class);
        if (!progressService.transactionIsAlreadyProcessed(result)) {
            eventPublisher.publishEvent(result);
        }
    }

    public void receiveTransferMessage(String message) throws IOException {
        TransferEventDto result = objectMapper.readValue(message, TransferEventDto.class);
        if (!progressService.transactionIsAlreadyProcessed(result)) {
            eventPublisher.publishEvent(result);
        }
    }
}
