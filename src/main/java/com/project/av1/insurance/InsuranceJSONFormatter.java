package com.project.av1.insurance;

import com.project.av1.coveredGood.CoveredGood;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class InsuranceJSONFormatter {

    private Long policy;
    private String cpf;
    private String startDate;
    private List<CoveredGood> coveredGoods;

    public InsuranceJSONFormatter(Insurance insurance, List<CoveredGood> coveredGoodList) {
        this.policy = insurance.getPolicy();
        this.cpf = insurance.getCpf();
        this.startDate = insurance.getStartDate();
        this.coveredGoods = coveredGoodList;
    }

    public Long getPolicy() {
        return policy;
    }

    public void setPolicy(Long policy) {
        this.policy = policy;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<CoveredGood> getCoveredGoods() {
        return coveredGoods;
    }

    public void setCoveredGoods(List<CoveredGood> coveredGoods) {
        this.coveredGoods = coveredGoods;
    }
}
