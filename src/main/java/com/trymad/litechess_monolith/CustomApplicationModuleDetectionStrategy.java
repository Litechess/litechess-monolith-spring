package com.trymad.litechess_monolith;

import java.util.stream.Stream;

import org.springframework.modulith.core.ApplicationModuleDetectionStrategy;
import org.springframework.modulith.core.ApplicationModuleInformation;
import org.springframework.modulith.core.JavaPackage;
import org.springframework.modulith.core.NamedInterfaces;

public class CustomApplicationModuleDetectionStrategy implements ApplicationModuleDetectionStrategy {

  	private final ApplicationModuleDetectionStrategy delegate =
    	ApplicationModuleDetectionStrategy.directSubPackage();

	@Override
	public Stream<JavaPackage> getModuleBasePackages(JavaPackage rootPackage) {
		return delegate.getModuleBasePackages(rootPackage);
	}

	@Override
	public NamedInterfaces detectNamedInterfaces(JavaPackage basePackage,
												ApplicationModuleInformation information) {
		final NamedInterfaces apiInterfaces = NamedInterfaces.builder(basePackage)
			.recursive()
			.matching("api")
			.build();
			
		final NamedInterfaces annotatedInterfaces = delegate.detectNamedInterfaces(basePackage, information);
		
		return apiInterfaces.and(annotatedInterfaces);
	}
	
}
