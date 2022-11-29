package com.project.av1.av2;

import com.project.av1.coveredGood.CoveredGood;
import com.project.av1.good.Good;
import com.project.av1.insurance.Insurance;

import java.io.*;
import java.util.List;

public class TxtBuilder {

    public void buildTxt(List<Good> goods, List<Insurance> insurances, List<CoveredGood> coveredGoods) {
        try {
            FileWriter goodWriter = new FileWriter("goodTxt.txt");
            FileWriter insuranceWriter = new FileWriter("insuranceTxt.txt");
            FileWriter coveredGoodWriter = new FileWriter("coveredGoodTxt.txt");


            for(Good good: goods) {
                goodWriter.write(good.toString());
            }

            for(Insurance insurance: insurances) {
                String value = insurance.toString();
                insuranceWriter.write(value);
            }

            for(CoveredGood coveredGood: coveredGoods) {
                coveredGoodWriter.write(coveredGood.toString());
            }

            goodWriter.close();
            insuranceWriter.close();
            coveredGoodWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
