package com.project.av1.av2;

import com.project.av1.coveredGood.CoveredGood;
import com.project.av1.good.Good;
import com.project.av1.insurance.Insurance;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MapObjectsHandlerSax extends DefaultHandler {

    private StringBuilder currentValue = new StringBuilder();
    List<Insurance> insurances;
    List<Good> goods;
    List<CoveredGood> coveredGoods;
    Insurance currentInsurance;
    CoveredGood currentCoveredGood;
    Good currentGood;

    public List<Insurance> getInsurances() {
        return insurances;
    }

    public List<CoveredGood> getCoveredGoods() {
        return coveredGoods;
    }

    public List<Good> getGoods() {
        return goods;
    }

    @Override
    public void startDocument() {
        insurances = new ArrayList<>();
        goods = new ArrayList<>();
        coveredGoods = new ArrayList<>();
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) {

        currentValue.setLength(0);

        if (qName.equalsIgnoreCase("insurance")) {
            // new insurance
            currentInsurance = new Insurance();
        }

        if (qName.equalsIgnoreCase("coveredGood")) {
            currentCoveredGood = new CoveredGood();
        }

        if (qName.equalsIgnoreCase("good")) {
            currentGood = new Good();

            String code = attributes.getValue("code");
            currentGood.setCode(Long.valueOf(code));
            currentCoveredGood.setGoodCode(Long.valueOf(code));
        }
    }

    public void endElement(String uri,
                           String localName,
                           String qName) {

        if (qName.equalsIgnoreCase("cpf")) {
            currentInsurance.setCpf(currentValue.toString());
        }

        if (qName.equalsIgnoreCase("startDate")) {
            currentInsurance.setStartDate(currentValue.toString());
        }

        if (qName.equalsIgnoreCase("name")) {
            currentGood.setName(currentValue.toString());
        }

        if (qName.equalsIgnoreCase("expectedValue")) {
            currentGood.setExpectedValue(Integer.parseInt(currentValue.toString()));
        }

        if (qName.equalsIgnoreCase("aliquot")) {
            currentGood.setAliquot(Double.valueOf(currentValue.toString()));
        }

        if (qName.equalsIgnoreCase("good")) {
            goods.add(currentGood);
        }

        if (qName.equalsIgnoreCase("deadline")) {
            currentCoveredGood.setDeadline(Integer.valueOf(currentValue.toString()));
        }

        if (qName.equalsIgnoreCase("riskFactor")) {
            currentCoveredGood.setRiskFactor(Integer.valueOf(currentValue.toString()));
        }

        if (qName.equalsIgnoreCase("coveredGood")) {
            if(currentCoveredGood.getRiskFactor() == null) {
                currentCoveredGood.setTotalValue(currentGood.getExpectedValue() * currentGood.getAliquot()  * currentCoveredGood.getDeadline());
            } else {
                currentCoveredGood.setTotalValue(currentGood.getExpectedValue() * (currentGood.getAliquot() + currentCoveredGood.getRiskFactor())  * currentCoveredGood.getDeadline());
            }
            coveredGoods.add(currentCoveredGood);
            currentInsurance.addCoveredGood(currentCoveredGood.getGoodCode());
        }

        if (qName.equalsIgnoreCase("insurance")) {
            insurances.add(currentInsurance);
        }

    }

    public void characters(char ch[], int start, int length) {
        currentValue.append(ch, start, length);

    }

}
