module de.ruu.app.datamodel.postaladdress.jpadto
{
	exports de.ruu.app.datamodel.postaladdress.jpadto;
	exports de.ruu.app.datamodel.postaladdress.jpa;
	exports de.ruu.app.datamodel.postaladdress.dto;

	opens de.ruu.app.datamodel.postaladdress.dto;

	requires static lombok;

	requires jakarta.annotation;
	requires jakarta.persistence;
	requires org.mapstruct;

	requires de.ruu.lib.jpa.core;
	requires de.ruu.app.datamodel.postaladdress.common;
	requires de.ruu.lib.mapstruct;
	requires de.ruu.lib.jpa.core.mapstruct;
}