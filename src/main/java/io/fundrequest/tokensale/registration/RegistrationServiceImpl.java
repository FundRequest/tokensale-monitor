package io.fundrequest.tokensale.registration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private static final String SHEET_RANGE = "A3:F";
    private Map<String, Participant> participantsByAddress = new HashMap<>();
    private String spreadsheetId;
    private String googleClientSecret;

    public RegistrationServiceImpl(@Value("${io.fundrequest.tokensale.spreadsheet-id}") String spreadsheetId,
                                   @Value("${io.fundrequest.tokensale.google-sheets-client-secret}") String googleClientSecret) {
        this.spreadsheetId = spreadsheetId;
        this.googleClientSecret = googleClientSecret;
    }

    @PostConstruct
    public void load() {
        try {
            this.participantsByAddress = importFromSheets();
            LOGGER.info("Imported new data");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("unable to load");
        }
    }

    @Override
    public Optional<Participant> getParticipant(String address) {
        return Optional.ofNullable(participantsByAddress.get(address));
    }

    private Map<String, Participant> importFromSheets() throws Exception {
        final ValueRange response = getSheetsService().spreadsheets().values()
                .get(spreadsheetId, SHEET_RANGE)
                .execute();
        final List<List<Object>> values = response.getValues();

        return values.stream()
                .map(this::createParticipant)
                .collect(Collectors.toMap(Participant::getAddress, Function.identity(), (p1, p2) -> p2));
    }

    private Participant createParticipant(List<Object> row) {
        Participant p = new Participant();
        p.setAddress(getRowValue(row, 5));
        p.setEmail(getRowValue(row, 2));
        p.setFirstName(getRowValue(row, 0));
        p.setLastName(getRowValue(row, 1));
        return p;
    }

    private String getRowValue(final List row, final int i) {
        return i < row.size() ? row.get(i).toString() : null;
    }

    private Sheets getSheetsService() throws Exception {
        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), authorize())
                .setApplicationName("TOKENSALE")
                .build();
    }

    private Credential authorize() throws IOException {
        final List<String> scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS);
        return GoogleCredential
                .fromStream(new ByteArrayInputStream(this.googleClientSecret.getBytes()))
                .createScoped(scopes);
    }


}
