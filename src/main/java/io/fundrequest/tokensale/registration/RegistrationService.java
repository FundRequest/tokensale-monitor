package io.fundrequest.tokensale.registration;

import java.util.Optional;

public interface RegistrationService {


    Optional<Participant> getParticipant(String address);
}
