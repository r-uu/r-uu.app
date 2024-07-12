module de.ruu.app.datamodel.postaladdress.jpadto.ee
{
	requires jakarta.annotation;
	requires jakarta.cdi;
	requires jakarta.inject;
	requires jakarta.persistence;
	requires jakarta.transaction;

	requires static lombok;
	requires org.slf4j;

	requires de.ruu.app.datamodel.postaladdress.jpadto;
	requires de.ruu.lib.jpa.core;
}