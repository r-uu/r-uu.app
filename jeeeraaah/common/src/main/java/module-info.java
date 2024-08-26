module de.ruu.app.jeee_raaa.common
{
	exports de.ruu.app.jeeeraaah.common;
	opens   de.ruu.app.jeeeraaah.common;

	requires static lombok;
	requires de.ruu.lib.jpa.core.mapstruct;
	requires org.mapstruct;
	requires de.ruu.lib.mapstruct;
	requires de.ruu.lib.util;
	requires jakarta.persistence;
	requires jakarta.annotation;
	requires de.ruu.lib.jpa.core;
}