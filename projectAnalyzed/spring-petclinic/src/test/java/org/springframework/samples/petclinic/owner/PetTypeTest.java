package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetTypeTest {

	@Test
	void petType_canBeInstantiated_andHasNameProperty() {
		PetType type = new PetType();

		type.setName("hamster");
		assertEquals("hamster", type.getName());
		assertEquals("hamster", type.toString());
	}

}
