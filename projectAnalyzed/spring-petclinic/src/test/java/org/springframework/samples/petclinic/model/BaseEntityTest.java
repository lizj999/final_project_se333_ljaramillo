package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseEntityTest {

	@Test
	void isNew_whenIdNull_returnsTrue() {
		BaseEntity entity = new BaseEntity();

		assertNull(entity.getId());
		assertTrue(entity.isNew());
	}

	@Test
	void isNew_whenIdSet_returnsFalse() {
		BaseEntity entity = new BaseEntity();
		entity.setId(123);

		assertEquals(123, entity.getId());
		assertFalse(entity.isNew());
	}

}
