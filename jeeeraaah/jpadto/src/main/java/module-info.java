module de.ruu.app.jeeeraaah.jpadto
{
	exports de.ruu.app.jeeeraaah.common.dto;
	exports de.ruu.app.jeeeraaah.common.jpa;
	exports de.ruu.app.jeeeraaah.common.jpadto;

	opens de.ruu.app.jeeeraaah.common.jpa;

	requires de.ruu.app.jeeeraaah.common;
	requires de.ruu.lib.jpa.core;
	requires de.ruu.lib.jpa.core.mapstruct;
	requires de.ruu.lib.mapstruct;
	requires de.ruu.lib.util;

	requires static lombok;
	requires org.slf4j;

	requires jakarta.cdi;
	requires jakarta.persistence;
	requires org.mapstruct;
}