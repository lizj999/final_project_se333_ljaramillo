package org.springframework.samples.petclinic.vet;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VetsTest {

	@Test
	void getVetList_lazilyInitializesAndReturnsSameListInstance() {
		Vets vets = new Vets();

		List<Vet> first = vets.getVetList();
		assertNotNull(first);
		assertTrue(first.isEmpty());

		first.add(new Vet());

		List<Vet> second = vets.getVetList();
		assertSame(first, second);
		assertEquals(1, second.size());
	}

}
