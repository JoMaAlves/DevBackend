package com.project.av1.insurance;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Insurance {

    private @Id @GeneratedValue Long policy;
    private String cpf;
    private String startDate;

    Insurance() {
    }

    Insurance(String cpf, String startDate) {
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
        return "Insurance{" + "policy=" + this.policy + ", cpf='" + this.cpf + '\'' + ", startDate='" + this.startDate
                + '\'' + '}';
    }
}