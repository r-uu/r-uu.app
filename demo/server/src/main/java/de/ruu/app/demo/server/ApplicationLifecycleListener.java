package de.ruu.app.demo.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.Startup;

import java.io.File;

@Singleton
@Startup
@Slf4j
public class ApplicationLifecycleListener
{
	@PostConstruct public void init()
	{
		log.debug("working directory {}", new File(".").getAbsolutePath());
	}

	@PreDestroy public void destroy()
	{
		log.debug("working directory {}", new File(".").getAbsolutePath());
	}
}