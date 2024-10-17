module de.ruu.app.jeeeraaah.client.rs
{
	exports de.ruu.app.jeeeraaah.client.rs;

	requires jakarta.annotation;
	requires jakarta.inject;
	requires jakarta.ws.rs;

	requires microprofile.config.api;

	requires static lombok;
	requires org.slf4j;

	requires de.ruu.app.jeeeraaah.common;
	requires de.ruu.app.jeeeraaah.jpadto;
	requires de.ruu.lib.jsonb;
	requires de.ruu.lib.util;
}