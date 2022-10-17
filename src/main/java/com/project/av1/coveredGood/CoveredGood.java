package com.project.av1.coveredGood;

import javax.persistence.*;

@Entity
public class CoveredGood {

    @Id
    private Long goodCode;

    private Integer deadline;

    private Integer riskFactor;

    private Double totalValue;

    CoveredGood() {
    }

    public CoveredGood(Long goodCode, Integer deadline) {
        this.goodCode = goodCode;
        this.deadline = deadline;
    }

    public CoveredGood(Long goodCode, Integer deadline, Integer riskFactor) {
        this.goodCode = goodCode;
        this.deadline = deadline;
        this.riskFactor = riskFactor;
    }

    public Long getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(Long goodCode) {
        this.goodCode = goodCode;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Integer getRiskFactor() {
        return riskFactor;
    }

    public void setRiskFactor(Integer riskFactor) {
        this.riskFactor = riskFactor;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
}
