package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

	@Test
	void firstName_roundTripsThroughGetterSetter() {
		Person person = new Person();

		person.setFirstName("Liz");
		assertEquals("Liz", person.getFirstName());
	}

	@Test
	void lastName_roundTripsThroughGetterSetter() {
		Person person = new Person();

		person.setLastName("Jaramillo");
		assertEquals("Jaramillo", person.getLastName());
	}

	@Test
	void isNew_delegatesToBaseEntityIdPresence() {
		Person person = new Person();
		assertTrue(person.isNew());

		person.setId(7);
		assertFalse(person.isNew());
	}

}
