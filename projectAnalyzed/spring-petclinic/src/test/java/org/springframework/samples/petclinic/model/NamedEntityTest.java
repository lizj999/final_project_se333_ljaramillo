package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NamedEntityTest {

	@Test
	void name_roundTripsThroughGetterSetter() {
		NamedEntity entity = new NamedEntity();

		entity.setName("Fluffy");
		assertEquals("Fluffy", entity.getName());
	}

	@Test
	void toString_whenNameNonNull_returnsName() {
		NamedEntity entity = new NamedEntity();
		entity.setName("Buddy");

		assertEquals("Buddy", entity.toString());
	}

	@Test
	void toString_whenNameNull_returnsLiteralNullMarker() {
		NamedEntity entity = new NamedEntity();
		entity.setName(null);

		assertEquals("<null>", entity.toString());
	}

}
