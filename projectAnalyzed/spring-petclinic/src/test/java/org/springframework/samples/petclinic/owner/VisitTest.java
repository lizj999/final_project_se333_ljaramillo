package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitTest {

	@Test
	void constructor_setsDateToNow() {
		Visit visit = new Visit();

		assertEquals(LocalDate.now(), visit.getDate());
	}

	@Test
	void dateAndDescription_roundTripThroughSetters() {
		Visit visit = new Visit();
		LocalDate date = LocalDate.of(2020, 2, 29);

		visit.setDate(date);
		visit.setDescription("annual checkup");

		assertEquals(date, visit.getDate());
		assertEquals("annual checkup", visit.getDescription());
	}

}
