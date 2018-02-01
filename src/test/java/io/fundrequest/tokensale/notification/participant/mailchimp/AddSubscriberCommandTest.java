package io.fundrequest.tokensale.notification.participant.mailchimp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;

public class AddSubscriberCommandTest {

    @Test
    public void testDeserialize() throws JsonProcessingException {
        AddSubscriberCommand command = new AddSubscriberCommand();
        command.setEmail("davy.van.roy@gmail.com");
        command.setFirstName("Davy");
        command.setLastName("Van Roy");
        command.setTokenAmount(BigDecimal.TEN);
        System.out.println(new ObjectMapper().writeValueAsString(command));
    }
}