module de.ruu.app.demo.client
{
	exports de.ruu.app.demo.client.rs.company;
	exports de.ruu.app.demo.client.rs.system;

	requires jakarta.cdi;
	requires jakarta.el;
	requires jakarta.inject;

	requires static lombok;

	requires microprofile.config.api;
	requires jakarta.ws.rs;
	requires jakarta.annotation;

	requires de.ruu.app.demo.common;
	requires de.ruu.lib.util;
}