package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

	@Test
	void addPet_addsOnlyNewPets() {
		Owner owner = new Owner();

		Pet newPet = new Pet();
		newPet.setName("Basil");
		assertTrue(newPet.isNew());

		Pet existingPet = new Pet();
		existingPet.setId(5);
		existingPet.setName("Pepper");
		assertFalse(existingPet.isNew());

		owner.addPet(existingPet);
		owner.addPet(newPet);

		assertEquals(1, owner.getPets().size());
		assertSame(newPet, owner.getPets().get(0));
	}

	@Test
	void getPet_byName_isCaseInsensitive_andRespectsIgnoreNewFlag() {
		Owner owner = new Owner();

		Pet newPet = new Pet();
		newPet.setName("Fluffy");
		assertTrue(newPet.isNew());

		Pet existingPet = new Pet();
		existingPet.setId(10);
		existingPet.setName("Spot");

		owner.getPets().add(newPet);
		owner.getPets().add(existingPet);

		assertSame(newPet, owner.getPet("fluffy"));
		assertNull(owner.getPet("fluffy", true));
		assertSame(existingPet, owner.getPet("SPOT", true));
	}

	@Test
	void getPet_byId_returnsOnlyNonNewPets() {
		Owner owner = new Owner();

		Pet newPet = new Pet();
		newPet.setId(null);
		newPet.setName("Newbie");

		Pet existingPet = new Pet();
		existingPet.setId(99);
		existingPet.setName("Oldie");

		owner.getPets().add(newPet);
		owner.getPets().add(existingPet);

		assertNull(owner.getPet(99 + 1));
		assertSame(existingPet, owner.getPet(99));
	}

	@Test
	void addVisit_addsVisitToMatchingPet_andValidatesArguments() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(7);
		owner.getPets().add(pet);

		Visit visit = new Visit();
		visit.setDescription("checkup");

		owner.addVisit(7, visit);
		assertEquals(1, pet.getVisits().size());

		assertThrows(IllegalArgumentException.class, () -> owner.addVisit(null, visit));
		assertThrows(IllegalArgumentException.class, () -> owner.addVisit(7, null));
		assertThrows(IllegalArgumentException.class, () -> owner.addVisit(123, visit));
	}

	@Test
	void toString_includesKeyFields() {
		Owner owner = new Owner();
		owner.setFirstName("Liz");
		owner.setLastName("Jaramillo");
		owner.setAddress("123 Main");
		owner.setCity("Boston");
		owner.setTelephone("0123456789");

		String text = owner.toString();

		assertAll(() -> assertTrue(text.contains("firstName")), () -> assertTrue(text.contains("Liz")),
				() -> assertTrue(text.contains("lastName")), () -> assertTrue(text.contains("Jaramillo")),
				() -> assertTrue(text.contains("address")), () -> assertTrue(text.contains("123 Main")),
				() -> assertTrue(text.contains("city")), () -> assertTrue(text.contains("Boston")),
				() -> assertTrue(text.contains("telephone")), () -> assertTrue(text.contains("0123456789")));
	}

}
