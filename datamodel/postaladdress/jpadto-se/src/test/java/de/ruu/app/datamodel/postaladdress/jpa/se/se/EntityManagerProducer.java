package de.ruu.app.datamodel.postaladdress.jpa.se.se;

import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.lib.jpa.se.hibernate.postgres.AbstractEntityManagerProducer;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.util.List;

@Singleton public class EntityManagerProducer extends AbstractEntityManagerProducer
{
	@Override public List<Class<?>> managedClasses() { return List.of(PostalAddressEntity.class); }

	@Override @Produces public EntityManager produce() { return super.produce(); }
}