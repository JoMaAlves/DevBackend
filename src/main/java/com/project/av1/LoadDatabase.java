package com.project.av1;

import com.project.av1.coveredGood.CoveredGood;
import com.project.av1.coveredGood.CoveredGoodRepository;
import com.project.av1.good.Good;
import com.project.av1.good.GoodRepository;
import com.project.av1.insurance.Insurance;
import com.project.av1.insurance.InsuranceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(InsuranceRepository insuranceRepository, GoodRepository goodRepository, CoveredGoodRepository coveredGoodRepository) {

        List<Good> goods = new ArrayList<>();
        List<Insurance> insurances = new ArrayList<>();
        List<CoveredGood> coveredGoods = new ArrayList<>();

        goods.add(new Good("Corsa 2010", 10000, 0.5));
        goods.add(new Good("RTX4090", 14000, 0.2));
        goods.add(new Good("Oculus Rift", 5000, 0.8));

        coveredGoods.add(new CoveredGood(1L, 2, 2));
        coveredGoods.add(new CoveredGood(2L, 3));
        coveredGoods.add(new CoveredGood(3L, 4));

        insurances.add(new Insurance("726.481.570-69", "2020-06-05", List.of(1L,2L)));
        insurances.add(new Insurance("566.506.260-07", "2022-08-17", List.of(3L)));

        return args -> {
            log.info("Preloading " + goodRepository.save(goods.get(0)));
            log.info("Preloading " + goodRepository.save(goods.get(1)));
            log.info("Preloading " + goodRepository.save(goods.get(2)));

            log.info("Preloading " + insuranceRepository.save(insurances.get(0)));
            log.info("Preloading " + insuranceRepository.save(insurances.get(1)));

            log.info("Preloading " + coveredGoodRepository.save(coveredGoods.get(0)));
            log.info("Preloading " + coveredGoodRepository.save(coveredGoods.get(1)));
            log.info("Preloading " + coveredGoodRepository.save(coveredGoods.get(2)));
        };
    }
}