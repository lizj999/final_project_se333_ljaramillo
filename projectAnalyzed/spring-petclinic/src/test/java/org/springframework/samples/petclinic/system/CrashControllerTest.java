package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CrashControllerTest {

	@Test
	void triggerException_throwsRuntimeException() {
		CrashController controller = new CrashController();

		assertThrows(RuntimeException.class, controller::triggerException);
	}

}
