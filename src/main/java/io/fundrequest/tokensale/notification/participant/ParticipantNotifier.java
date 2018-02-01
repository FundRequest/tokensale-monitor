package io.fundrequest.tokensale.notification.participant;

import io.fundrequest.tokensale.notification.participant.mailchimp.AddSubscriberCommand;
import io.fundrequest.tokensale.notification.participant.mailchimp.MailChimpClient;
import io.fundrequest.tokensale.progress.dto.PaidEventDto;
import io.fundrequest.tokensale.registration.RegistrationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ParticipantNotifier {

    private MailChimpClient mailChimpClient;
    private RegistrationService registrationService;

    public ParticipantNotifier(MailChimpClient mailChimpClient, RegistrationService registrationService) {
        this.mailChimpClient = mailChimpClient;
        this.registrationService = registrationService;
    }

    @EventListener
    public void tokensBought(PaidEventDto event) {
        AddSubscriberCommand command = new AddSubscriberCommand();
        registrationService.getParticipant(event.getBeneficiary()).ifPresent(participant -> {
            BigDecimal amount = new BigDecimal(event.getTokenAmount()).divide(BigDecimal.TEN.pow(18).setScale(2, RoundingMode.HALF_UP), RoundingMode.HALF_UP);
            command.setAddress(event.getBeneficiary());
            command.setTransactionId(event.getTransactionHash());
            command.setTokenAmount(amount);
            command.setEmail(participant.getEmail());
            command.setFirstName(participant.getFirstName());
            command.setLastName(participant.getLastName());
            command.setFirstName(participant.getFirstName());
            mailChimpClient.addSubscriber(command);
        });


    }

}
