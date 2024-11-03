module de.ruu.app.jeee_raaa.server
{

	requires de.ruu.app.jeeeraaah.common;
	requires de.ruu.app.jeeeraaah.jpadto;
	requires de.ruu.lib.util;

	requires jakarta.cdi;
	requires jakarta.ws.rs;
	requires microprofile.health.api;
	requires microprofile.metrics.api;
	requires microprofile.openapi.api;

	requires static lombok;
	requires org.slf4j;
	requires jdk.management;
}