package com.project.av1;

import com.project.av1.av2.MapObjectsHandlerSax;
import com.project.av1.av2.TxtBuilder;
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
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private static final String FILENAME = "src/main/resources/insurances.xml";

    @Bean
    CommandLineRunner initDatabase(InsuranceRepository insuranceRepository, GoodRepository goodRepository, CoveredGoodRepository coveredGoodRepository) {
        List<Good> goods = new ArrayList<>();
        List<Insurance> insurances = new ArrayList<>();
        List<CoveredGood> coveredGoods = new ArrayList<>();

        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {

            SAXParser saxParser = factory.newSAXParser();

            MapObjectsHandlerSax handler = new MapObjectsHandlerSax();

            saxParser.parse(FILENAME, handler);
            log.info("XML File Found, deleting database and using its content");

            goods = handler.getGoods();
            insurances = handler.getInsurances();
            coveredGoods = handler.getCoveredGoods();

            goodRepository.deleteAll();
            coveredGoodRepository.deleteAll();
            insuranceRepository.deleteAll();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.info("No XML File Found, using stored information");
        }

        for (Good good: goods) {
            goodRepository.save(good);
        }

        for (CoveredGood coveredGood: coveredGoods) {
            coveredGoodRepository.save(coveredGood);
        }

        for (Insurance insurance: insurances) {
            insuranceRepository.save(insurance);
        }

        return args -> {
            log.info("Loaded Database");
        };
    }


}