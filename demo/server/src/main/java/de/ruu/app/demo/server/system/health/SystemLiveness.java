package de.ruu.app.demo.server.system.health;

import de.ruu.app.demo.server.system.System;
import jakarta.enterprise.context.ApplicationScoped;

import java.lang.management.MemoryMXBean;
import java.lang.management.ManagementFactory;

import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Liveness
@ApplicationScoped
public class SystemLiveness implements HealthCheck
{
	@Override public HealthCheckResponse call()
	{
		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		long         memUsed = memBean.getHeapMemoryUsage().getUsed();
		long         memMax  = memBean.getHeapMemoryUsage().getMax();

		return
				HealthCheckResponse
						.named(System.class.getSimpleName() + " Liveness Check")
						.status(memUsed < memMax * 0.9) // mem is fine if usage is below 90%
						.build();
	}
}