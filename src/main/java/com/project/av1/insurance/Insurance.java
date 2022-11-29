package com.project.av1.insurance;

import com.project.av1.coveredGood.CoveredGood;
import com.project.av1.coveredGood.CoveredGoodRepository;
import net.minidev.json.annotate.JsonIgnore;

import java.util.*;

import javax.persistence.*;

@Entity
public class Insurance {

    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long policy;
    private String cpf;
    private String startDate;
    @ElementCollection()
    private List<Long> coveredGoods = new ArrayList<>();

    public Insurance() {
    }

    public Insurance(String cpf, String startDate, List<Long> coveredGoods ) {
        this.cpf = cpf;
        this.startDate = startDate;
    }

    public Long getPolicy() {
        return this.policy;
    }


    public String getCpf() {
        return this.cpf;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setPolicy(Long policy) {
        this.policy = policy;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<Long> getCoveredGoods() {
        return coveredGoods;
    }

    public void setCoveredGoods(List<Long> coveredGoods) {
        this.coveredGoods = coveredGoods;
    }

    public void addCoveredGood(Long code) {
        this.coveredGoods.add(code);
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Insurance insurance))
            return false;
        return Objects.equals(this.policy, insurance.policy) && Objects.equals(this.cpf, insurance.cpf)
                && Objects.equals(this.startDate, insurance.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.policy, this.cpf, this.startDate);
    }

    @Override
    public String toString() {
        String coveredString = "";

        for(Long item : coveredGoods){
            coveredString +=  item + ",";
        }

        coveredString = coveredString.substring(0, coveredString.length() - 1);

        return "\"" + this.policy + "\",\"" + this.cpf + "\",\"" + this.startDate + "\",\"" + coveredString + "\"\n";
    }
}