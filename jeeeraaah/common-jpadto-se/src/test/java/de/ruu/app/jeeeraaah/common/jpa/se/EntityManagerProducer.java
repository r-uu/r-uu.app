package de.ruu.app.jeeeraaah.common.jpa.se;

import de.ruu.app.jeeeraaah.common.jpa.TaskEntity;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntity;
import de.ruu.lib.jpa.se.hibernate.postgres.AbstractEntityManagerProducer;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.util.List;

@Singleton public class EntityManagerProducer extends AbstractEntityManagerProducer
{
	@Override public List<Class<?>> managedClasses()
	{
		return
				List.of
				(
						  TaskGroupEntity.class
						, TaskEntity.class
				);
	}

	@Override @Produces public EntityManager produce() { return super.produce(); }
}