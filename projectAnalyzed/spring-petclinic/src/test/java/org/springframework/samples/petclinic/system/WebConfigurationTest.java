package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebConfigurationTest {

	@Test
	void localeResolver_isSessionLocaleResolverWithEnglishDefault() {
		WebConfiguration configuration = new WebConfiguration();

		LocaleResolver resolver = configuration.localeResolver();

		assertInstanceOf(SessionLocaleResolver.class, resolver);
	}

	@Test
	void localeChangeInterceptor_usesLangParameter() {
		WebConfiguration configuration = new WebConfiguration();

		LocaleChangeInterceptor interceptor = configuration.localeChangeInterceptor();

		assertNotNull(interceptor);
	}

}
