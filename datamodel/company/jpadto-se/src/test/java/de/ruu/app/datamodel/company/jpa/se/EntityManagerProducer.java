package de.ruu.app.datamodel.company.jpa.se;

import de.ruu.app.datamodel.company.jpa.CompanyEntity;
import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
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
						  CompanyEntity   .class
						, DepartmentEntity.class
				);
	}

	@Override @Produces public EntityManager produce() { return super.produce(); }
}