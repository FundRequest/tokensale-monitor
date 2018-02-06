package io.fundrequest.tokensale.notification.participant;

import io.fundrequest.tokensale.notification.participant.mailchimp.AddSubscriberCommand;
import io.fundrequest.tokensale.notification.participant.mailchimp.MailChimpClient;
import io.fundrequest.tokensale.progress.dto.PaidEventDto;
import io.fundrequest.tokensale.registration.RegistrationService;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ParticipantNotifier {

    private static final Logger logger = LoggerFactory.getLogger(ParticipantNotifier.class);

    private MailChimpClient mailChimpClient;
    private RegistrationService registrationService;
    private String day1ListId;
    private String day2ListId;

    public ParticipantNotifier(MailChimpClient mailChimpClient, RegistrationService registrationService, @Value("${mailchaimp.list.day1.id}") String day1ListId, @Value("${mailchaimp.list.day2.id}") String day2ListId) {
        this.mailChimpClient = mailChimpClient;
        this.registrationService = registrationService;
        this.day1ListId = day1ListId;
        this.day2ListId = day2ListId;
    }

    @EventListener
    public void tokensBought(PaidEventDto event) {
        AddSubscriberCommand command = new AddSubscriberCommand();
        registrationService.getParticipant(event.getBeneficiary()).ifPresent(participant -> {
            BigDecimal tokenAmount = toEther(event.getTokenAmount());
            BigDecimal etherAmount = toEther(event.getWeiAmount());
            command.setAddress(event.getBeneficiary());
            command.setTransactionId(event.getTransactionHash());
            command.setTokenAmount(tokenAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            command.setEtherAmount(etherAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            command.setEmail(participant.getEmail());
            command.setLastName(participant.getLastName());
            command.setFirstName(participant.getFirstName());
            String listId = BooleanUtils.isTrue(event.getPersonalCapActive()) ? day1ListId : day2ListId;
            try {
                mailChimpClient.addSubscriber(listId, command);
            } catch (Exception e) {
                logger.error("error when contacting mailchimp", e);
            }
        });


    }

    public static BigDecimal toEther(String amount) {
        return new BigDecimal(amount).divide(BigDecimal.TEN.pow(18).setScale(2, RoundingMode.HALF_UP), RoundingMode.HALF_UP);
    }

}
