package io.fundrequest.tokensale.notification.participant.mailchimp;

import com.fasterxml.jackson.annotation.JsonProperty;

class MergeFields {
    @JsonProperty("FNAME")
    private String firstName;
    @JsonProperty("LNAME")
    private String lastName;
    @JsonProperty("TAMOUNT")
    private String tokenAmount;
    @JsonProperty("EAMOUNT")
    private String etherAmount;
    @JsonProperty("ETHADDRESS")
    private String address;
    @JsonProperty("TRANSID")
    private String transactionId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTokenAmount() {
        return tokenAmount;
    }

    public void setTokenAmount(String tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public String getAddress() {
        return address;
    }

    public String getEtherAmount() {
        return etherAmount;
    }

    public void setEtherAmount(String etherAmount) {
        this.etherAmount = etherAmount;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
