package io.fundrequest.tokensale.notification.participant.mailchimp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

class MergeFields {
    @JsonProperty("FNAME")
    private String firstName;
    @JsonProperty("LNAME")
    private String lastName;
    @JsonProperty("TAMOUNT")
    private BigDecimal tokenAmount;
    @JsonProperty("EAMOUNT")
    private BigDecimal etherAmount;
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

    public BigDecimal getTokenAmount() {
        return tokenAmount;
    }

    public void setTokenAmount(BigDecimal tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getEtherAmount() {
        return etherAmount;
    }

    public void setEtherAmount(BigDecimal etherAmount) {
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
