package de.ruu.app.demo.server.system.health;

import de.ruu.app.demo.server.system.System;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Readiness
@ApplicationScoped
public class SystemReadiness implements HealthCheck
{
    private static final String READINESS_CHECK = System.class.getSimpleName() + " Readiness Check";

    @Inject
    @ConfigProperty(name = "de.ruu.app.demo.server.system.SystemResource_inMaintenance")
    Provider<String> inMaintenance;

    @Override public HealthCheckResponse call()
    {
        if (inMaintenance != null && inMaintenance.get().equalsIgnoreCase("true"))
        {
            return HealthCheckResponse.down(READINESS_CHECK);
        }
        return HealthCheckResponse.up(READINESS_CHECK);
    }
}