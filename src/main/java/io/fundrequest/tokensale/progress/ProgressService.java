package io.fundrequest.tokensale.progress;

import io.fundrequest.tokensale.progress.dto.PaidEventDto;
import io.fundrequest.tokensale.progress.dto.TransferEventDto;

public interface ProgressService {
    boolean transactionIsAlreadyProcessed(PaidEventDto paidEvent);

    boolean transactionIsAlreadyProcessed(TransferEventDto transferEvent);
}
