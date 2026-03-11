package org.springframework.samples.petclinic.vet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyTest {

	@Test
	void specialty_canBeInstantiated_andHasNameProperty() {
		Specialty specialty = new Specialty();

		specialty.setName("surgery");
		assertEquals("surgery", specialty.getName());
		assertEquals("surgery", specialty.toString());
	}

}
