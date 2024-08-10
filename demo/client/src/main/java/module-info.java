module de.ruu.app.demo.client
{
	exports de.ruu.app.demo.client.datamodel.rs.company;
	exports de.ruu.app.demo.client.rs.system;
	exports de.ruu.app.demo.client.datamodel.fx.postaladdress;
	exports de.ruu.app.demo.client.datamodel.rs.postaladdress;

	opens de.ruu.app.demo.client.datamodel.fx;
	opens de.ruu.app.demo.client.datamodel.fx.postaladdress;
	opens de.ruu.app.demo.client.datamodel.fx.postaladdress.edit;
	opens de.ruu.app.demo.client.datamodel.rs.company;

	requires static lombok;
	requires org.slf4j;

	requires jakarta.annotation;
	requires jakarta.cdi;
	requires jakarta.inject;
	requires jakarta.ws.rs;

	requires javafx.controls;
	requires javafx.fxml;

	requires microprofile.config.api;

	requires de.ruu.app.demo.common;
	requires de.ruu.lib.util;
	requires de.ruu.lib.fx.comp;
	requires de.ruu.lib.cdi.se;
	requires de.ruu.app.datamodel.postaladdress.common;
	requires de.ruu.app.datamodel.postaladdress.jpadto;
	requires de.ruu.app.datamodel.company.common;
	requires de.ruu.app.datamodel.company.jpadto;
	requires de.ruu.lib.fx.core;
	requires de.ruu.lib.jsonb;
	requires de.ruu.lib.jpa.core;
}