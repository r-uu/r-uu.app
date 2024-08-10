module de.ruu.app.demo.server
{
	exports de.ruu.app.demo.server;

	requires java.management;

	requires jakarta.ws.rs;
	requires jakarta.cdi;

	requires microprofile.config.api;
	requires microprofile.health.api;
	requires microprofile.metrics.api;
	requires microprofile.openapi.api;

	requires de.ruu.lib.util;

	requires de.ruu.app.demo.common;
	requires de.ruu.app.datamodel.postaladdress.jpadto;
	requires de.ruu.app.datamodel.postaladdress.jpadto.ee;
	requires de.ruu.app.datamodel.company.jpadto;
	requires static lombok;
	requires de.ruu.app.datamodel.company.common;
}