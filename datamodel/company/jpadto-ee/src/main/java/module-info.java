module de.ruu.app.datamodel.company.jpadto.ee
{
	exports de.ruu.app.datamodel.company.jpa.ee;

	requires static lombok;
	requires org.slf4j;

	requires jakarta.annotation;
	requires jakarta.cdi;
	requires jakarta.inject;
	requires jakarta.transaction;

	requires de.ruu.app.datamodel.company.jpadto;
}