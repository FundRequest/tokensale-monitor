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

    public AzraelMessageListener(ObjectMapper objectMapper, ApplicationEventPublisher eventPublisher) {
        this.objectMapper = objectMapper;
        this.eventPublisher = eventPublisher;
    }

    public void receivePaidMessage(String message) throws IOException {
        PaidEventDto result = objectMapper.readValue(message, PaidEventDto.class);
        eventPublisher.publishEvent(result);
    }

    public void receiveTransferMessage(String message) throws IOException {
        TransferEventDto result = objectMapper.readValue(message, TransferEventDto.class);
        eventPublisher.publishEvent(result);
    }
}
