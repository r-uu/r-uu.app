module de.ruu.app.demo.client
{
	exports de.ruu.app.demo.client.datamodel.rs.company;
	exports de.ruu.app.demo.client.rs.system;
	exports de.ruu.app.demo.client.datamodel.rs.postaladdress;

	opens de.ruu.app.demo.client.datamodel.fx;
	opens de.ruu.app.demo.client.datamodel.rs.company;

	requires jakarta.annotation;
	requires jakarta.cdi;
	//requires jakarta.el;
	requires jakarta.inject;
	requires jakarta.ws.rs;

	requires static lombok;
//	requires org.slf4j;

	requires microprofile.config.api;
	requires javafx.fxml;

	requires de.ruu.app.demo.common;
	requires de.ruu.lib.util;
	requires de.ruu.lib.fx.comp;
	requires de.ruu.lib.cdi.se;
	requires de.ruu.app.datamodel.postaladdress.common;
	requires de.ruu.app.datamodel.postaladdress.jpadto;
	requires de.ruu.app.datamodel.company.common;
	requires r.uu.app.datamodel.company.jpadto;
}