package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WelcomeControllerTest {

	@Test
	void welcome_returnsWelcomeViewName() {
		WelcomeController controller = new WelcomeController();

		String viewName = controller.welcome();

		assertEquals("welcome", viewName);
	}

}
