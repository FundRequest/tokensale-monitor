package io.fundrequest.tokensale.progress.dto;

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
}
