package com.project.av1;

import com.project.av1.good.Good;
import com.project.av1.good.GoodValidator;
import com.project.av1.insurance.Insurance;
import com.project.av1.insurance.InsuranceValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.text.ParseException;
import java.util.List;

@SpringBootTest
class Av1ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void ValidaDataMenor(){
		Insurance n = new Insurance("869.179.470-40","2010-06-06", List.of(1L));
		InsuranceValidator validator = new InsuranceValidator();

		boolean failed = false;
		try {
			validator.insuranceRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		} catch (ParseException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaDataMaior(){
		Insurance n = new Insurance("86917947040","2025-06-06",List.of(1L));
		InsuranceValidator validator = new InsuranceValidator();

		boolean failed = false;
		try {
			validator.insuranceRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		} catch (ParseException e) {
			failed = true;
		}
		Assertions.assertFalse(failed);
	}

	@Test
	void ObrigatoriedadeDataVazia(){
		Insurance n = new Insurance("86917947040", "",List.of(1L));
		InsuranceValidator validator = new InsuranceValidator();

		boolean failed = false;
		try {
			validator.insuranceRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		} catch (ParseException e) {
			failed = true;
		}
		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaCPFNumerosIguais(){
		Insurance n = new Insurance("11111111111","2025-06-06", List.of(1L));
		InsuranceValidator validator = new InsuranceValidator();

		boolean failed = false;
		try {
			validator.insuranceRequiredFieldsValidation(n);
		} catch (InvalidException | ParseException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaCPFVazio(){
		Insurance n = new Insurance("","2025-06-06", List.of(1L));
		InsuranceValidator validator = new InsuranceValidator();

		boolean failed = false;
		try {
			validator.insuranceRequiredFieldsValidation(n);
		} catch (InvalidException | ParseException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaCPFMaior(){
		Insurance n = new Insurance("075033804032","2025-06-06", List.of(1L));
		InsuranceValidator validator = new InsuranceValidator();

		boolean failed = false;
		try {
			validator.insuranceRequiredFieldsValidation(n);
		} catch (InvalidException | ParseException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaBemNomeMaior(){
		Good n = new Good(12L,"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 2000, 0.8);
		GoodValidator validator = new GoodValidator();

		boolean failed = false;
		try {
			validator.goodRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaBemNomeVazio(){
		Good n = new Good(12L,"", 2000, 0.8);
		GoodValidator validator = new GoodValidator();

		boolean failed = false;
		try {
			validator.goodRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaAliquotaMaiorQueUm(){
		Good n = new Good(12L,"PC", 2000, 2.5);
		GoodValidator validator = new GoodValidator();

		boolean failed = false;
		try {
			validator.goodRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaAliquotaMenorQueZero(){
		Good n = new Good(12L,"PC", 2000, -1.5);
		GoodValidator validator = new GoodValidator();

		boolean failed = false;
		try {
			validator.goodRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaValorInexistente(){
		Good n = new Good(12L,"PC", null , 0.6);
		GoodValidator validator = new GoodValidator();

		boolean failed = false;
		try {
			validator.goodRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}

	@Test
	void ValidaAliquotaInexistente(){
		Good n = new Good(12L,"PC", 1200 , null);
		GoodValidator validator = new GoodValidator();

		boolean failed = false;
		try {
			validator.goodRequiredFieldsValidation(n);
		} catch (InvalidException e) {
			failed = true;
		}

		Assertions.assertTrue(failed);
	}
}
