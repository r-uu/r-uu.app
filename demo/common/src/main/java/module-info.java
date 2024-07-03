module de.ruu.app.demo.common
{
	exports de.ruu.app.demo.common;
	exports de.ruu.app.demo.common.datamodel;
	exports de.ruu.app.demo.common.datamodel.dto;
	exports de.ruu.app.demo.common.datamodel.jpa;
	exports de.ruu.app.demo.common.jpa;
	exports de.ruu.app.demo.common.jpa.ee;

	opens de.ruu.app.demo.common.datamodel.dto;
	opens de.ruu.app.demo.common.datamodel.jpa;
	opens de.ruu.app.demo.common.jpa.ee;

	requires static lombok;

	requires jakarta.annotation;
	requires jakarta.cdi;
	requires jakarta.inject;
	requires jakarta.persistence;
	requires jakarta.transaction;

	requires org.mapstruct;
	requires org.slf4j;

	requires de.ruu.lib.jpa.core;
	requires de.ruu.lib.util;
	requires de.ruu.lib.mapstruct;
	requires de.ruu.lib.jpa.core.mapstruct;
}