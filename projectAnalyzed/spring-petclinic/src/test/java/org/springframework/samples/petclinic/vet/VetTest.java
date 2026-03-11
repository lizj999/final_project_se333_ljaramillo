package org.springframework.samples.petclinic.vet;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VetTest {

	@Test
	void getSpecialties_whenNoneAdded_returnsEmptyListAndZeroCount() {
		Vet vet = new Vet();

		assertTrue(vet.getSpecialties().isEmpty());
		assertEquals(0, vet.getNrOfSpecialties());
	}

	@Test
	void addSpecialty_incrementsCount_andAppearsInReturnedList() {
		Vet vet = new Vet();
		Specialty dentistry = new Specialty();
		dentistry.setName("dentistry");

		vet.addSpecialty(dentistry);

		assertEquals(1, vet.getNrOfSpecialties());
		assertEquals(List.of(dentistry), vet.getSpecialties());
	}

	@Test
	void getSpecialties_returnsSpecialtiesSortedByName() {
		Vet vet = new Vet();

		Specialty radiology = new Specialty();
		radiology.setName("radiology");
		Specialty dentistry = new Specialty();
		dentistry.setName("dentistry");

		vet.addSpecialty(radiology);
		vet.addSpecialty(dentistry);

		List<Specialty> specialties = vet.getSpecialties();
		assertEquals(2, specialties.size());
		assertEquals("dentistry", specialties.get(0).getName());
		assertEquals("radiology", specialties.get(1).getName());
	}

}
