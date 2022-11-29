package com.project.av1.good;

import com.project.av1.coveredGood.CoveredGood;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Good {
    private @Id Long code;
    private String name;
    private Integer expectedValue;
    private Double aliquot;

    public Good() {
    }

    public Good(Long code, String name, Integer expectedValue, Double aliquot) {
        this.code = code;
        this.name = name;
        this.expectedValue = expectedValue;
        this.aliquot = aliquot;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(Integer expectedValue) {
        this.expectedValue = expectedValue;
    }

    public Double getAliquot() {
        return aliquot;
    }

    public void setAliquot(Double aliquot) {
        this.aliquot = aliquot;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Good good))
            return false;
        return Objects.equals(this.code, good.code) && Objects.equals(this.name, good.name)
                && Objects.equals(this.expectedValue, good.expectedValue) && Objects.equals(this.aliquot, good.aliquot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code, this.name, this.expectedValue, this.aliquot);
    }

    @Override
    public String toString() {
        return "\"" + this.code + "\",\"" + this.name + "\",\"" + this.expectedValue + "\",\"" + this.aliquot + "\"\n";
    }
}
