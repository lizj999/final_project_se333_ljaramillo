package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.cache.autoconfigure.JCacheManagerCustomizer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CacheConfigurationTest {

	@Test
	void petclinicCacheConfigurationCustomizer_createsVetsCache() {
		CacheConfiguration configuration = new CacheConfiguration();
		JCacheManagerCustomizer customizer = configuration.petclinicCacheConfigurationCustomizer();

		assertNotNull(customizer);
	}

}
