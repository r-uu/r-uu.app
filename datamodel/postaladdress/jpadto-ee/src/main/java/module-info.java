module de.ruu.app.datamodel.postaladdress.jpadto.ee
{
	exports de.ruu.app.datamodel.postaladdress.jpa.ee;

	requires static lombok;
	requires org.slf4j;

	requires jakarta.annotation;
	requires jakarta.cdi;
	requires jakarta.persistence;
	requires jakarta.transaction;

	requires de.ruu.app.datamodel.postaladdress.jpadto;
	requires de.ruu.lib.jpa.core;
}