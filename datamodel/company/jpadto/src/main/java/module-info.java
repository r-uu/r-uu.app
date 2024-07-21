module de.ruu.app.datamodel.company.jpadto
{
	exports de.ruu.app.datamodel.company.dto;
	exports de.ruu.app.datamodel.company.jpa;
	exports de.ruu.app.datamodel.company.jpadto;

	opens de.ruu.app.datamodel.company.dto;

	requires static lombok;
	requires org.slf4j;

	requires jakarta.persistence;
	requires jakarta.cdi;
	requires de.ruu.lib.util;
	requires de.ruu.lib.jpa.core.mapstruct;
	requires de.ruu.app.datamodel.company.common;
	requires de.ruu.lib.jpa.core;
	requires de.ruu.lib.mapstruct;
	requires org.mapstruct;
	requires jakarta.json.bind;
}