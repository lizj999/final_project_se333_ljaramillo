package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

class PetValidatorTest {

	static class SpecialPet extends Pet {

	}

	@Test
	void supports_onlyPetTypes() {
		PetValidator validator = new PetValidator();

		assertTrue(validator.supports(Pet.class));
		assertTrue(validator.supports(SpecialPet.class));
		assertFalse(validator.supports(Owner.class));
	}

	@Test
	void validate_whenMissingFields_rejectsExpectedFields() {
		Pet pet = new Pet();
		pet.setName(" ");
		pet.setBirthDate(null);
		pet.setType(null);
		assertTrue(pet.isNew());

		PetValidator validator = new PetValidator();
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		validator.validate(pet, errors);

		assertTrue(errors.hasFieldErrors("name"));
		assertTrue(errors.hasFieldErrors("type"));
		assertTrue(errors.hasFieldErrors("birthDate"));
	}

	@Test
	void validate_whenNotNew_allowsNullType_butStillRequiresNameAndBirthDate() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("");
		pet.setBirthDate(null);
		pet.setType(null);
		assertFalse(pet.isNew());

		PetValidator validator = new PetValidator();
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		validator.validate(pet, errors);

		assertTrue(errors.hasFieldErrors("name"));
		assertTrue(errors.hasFieldErrors("birthDate"));
		assertFalse(errors.hasFieldErrors("type"));
	}

	@Test
	void validate_whenAllFieldsPresent_hasNoErrors() {
		Pet pet = new Pet();
		pet.setName("Mochi");
		pet.setBirthDate(LocalDate.of(2021, 6, 1));
		PetType type = new PetType();
		type.setName("cat");
		pet.setType(type);

		PetValidator validator = new PetValidator();
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		validator.validate(pet, errors);

		assertFalse(errors.hasErrors());
	}

}
