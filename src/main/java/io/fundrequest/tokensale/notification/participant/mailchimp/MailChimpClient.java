package io.fundrequest.tokensale.notification.participant.mailchimp;

import io.fundrequest.MailChimpFeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "mailchimp-client",
        url = "${io.fundrequest.tokensale.mailchimp.url}",
        configuration = MailChimpFeignConfiguration.class
)
public interface MailChimpClient {

    @RequestMapping(method = RequestMethod.POST, value = "/lists/d9f08d0875/members/", consumes = "application/json")
    void addSubscriber(AddSubscriberCommand addSubscriberCommand);

}