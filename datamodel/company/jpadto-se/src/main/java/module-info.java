module de.ruu.app.datamodel.company.jpadto.se
{
	exports de.ruu.app.datamodel.company.jpa.se;

	requires static lombok;
	requires org.slf4j;

	requires jakarta.annotation;
	requires jakarta.inject;
	requires jakarta.persistence;

	requires de.ruu.app.datamodel.company.jpadto;
	requires de.ruu.lib.jpa.core;
	requires de.ruu.lib.jpa.se;
}