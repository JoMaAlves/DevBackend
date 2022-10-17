package com.project.av1;

import com.project.av1.insurance.Insurance;
import com.project.av1.insurance.InsuranceValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

}
