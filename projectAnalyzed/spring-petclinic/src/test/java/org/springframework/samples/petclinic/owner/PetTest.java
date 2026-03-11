package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {

	@Test
	void birthDateAndType_roundTripThroughSetters() {
		Pet pet = new Pet();
		LocalDate birthDate = LocalDate.of(2019, 1, 5);
		PetType type = new PetType();
		type.setName("dog");

		pet.setBirthDate(birthDate);
		pet.setType(type);

		assertEquals(birthDate, pet.getBirthDate());
		assertSame(type, pet.getType());
	}

	@Test
	void visitsCollection_isInitiallyEmpty_andAddVisitAdds() {
		Pet pet = new Pet();
		assertTrue(pet.getVisits().isEmpty());

		Visit visit = new Visit();
		visit.setDescription("vaccination");
		pet.addVisit(visit);

		assertEquals(1, pet.getVisits().size());
		assertTrue(pet.getVisits().contains(visit));
	}

}
