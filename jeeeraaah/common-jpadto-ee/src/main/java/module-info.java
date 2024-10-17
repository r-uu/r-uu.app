module de.ruu.app.jeeeraaah.common.jpadto.ee
{
	exports de.ruu.app.jeeeraaah.common.jpadto.ee;

	requires static lombok;
	requires org.slf4j;

	requires jakarta.persistence;
	requires jakarta.transaction;

	requires de.ruu.app.jeeeraaah.jpadto;
	requires de.ruu.lib.jpa.core;
}