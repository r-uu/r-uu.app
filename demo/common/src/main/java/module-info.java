module de.ruu.app.demo.common
{
	exports de.ruu.app.demo.common;
	opens   de.ruu.app.demo.common;

	requires static lombok;

	requires jakarta.persistence;
	requires de.ruu.lib.jpa.core;
	requires de.ruu.app.datamodel.company.jpadto;
}