package io.fundrequest.tokensale.notification.participant.mailchimp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddSubscriberCommand {

    @JsonProperty("email_address")
    private String email;
    @JsonProperty("status")
    private String status = "subscribed";
    @JsonProperty("merge_fields")
    private MergeFields mergeFields = new MergeFields();


    public void setEmail(String email) {
        this.email = email;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setFirstName(String firstName) {
        mergeFields.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        mergeFields.setLastName(lastName);
    }

    public void setTokenAmount(String tokenAmount) {
        mergeFields.setTokenAmount(tokenAmount);
    }

    public void setEtherAmount(String etherAmount) {
        mergeFields.setEtherAmount(etherAmount);
    }



    public void setAddress(String address) {
        mergeFields.setAddress(address);
    }

    public void setTransactionId(String transactionId) {
        mergeFields.setTransactionId(transactionId);
    }

    protected MergeFields getMergeFields() {
        return mergeFields;
    }
}
