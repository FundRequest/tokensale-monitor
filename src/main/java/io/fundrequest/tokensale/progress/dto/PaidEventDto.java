package io.fundrequest.tokensale.progress.dto;

import java.util.Objects;

public class PaidEventDto {

    private String transactionHash;
    private String beneficiary;
    private String weiAmount;
    private String tokenAmount;
    private Long timestamp;
    private Boolean personalCapActive;

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getWeiAmount() {
        return weiAmount;
    }

    public void setWeiAmount(String weiAmount) {
        this.weiAmount = weiAmount;
    }

    public String getTokenAmount() {
        return tokenAmount;
    }

    public void setTokenAmount(String tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getPersonalCapActive() {
        return personalCapActive;
    }

    public void setPersonalCapActive(Boolean personalCapActive) {
        this.personalCapActive = personalCapActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaidEventDto that = (PaidEventDto) o;
        return Objects.equals(transactionHash, that.transactionHash) &&
                Objects.equals(beneficiary, that.beneficiary) &&
                Objects.equals(weiAmount, that.weiAmount) &&
                Objects.equals(tokenAmount, that.tokenAmount) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(personalCapActive, that.personalCapActive);
    }

    @Override
    public int hashCode() {

        return Objects.hash(transactionHash, beneficiary, weiAmount, tokenAmount, timestamp, personalCapActive);
    }
}
