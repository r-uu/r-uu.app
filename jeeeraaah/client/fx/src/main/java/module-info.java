module de.ruu.app.jeeeraaah.client.fx
{
	exports de.ruu.app.jeeeraaah.client.fx;
	exports de.ruu.app.jeeeraaah.client.fx.task.view;

	opens de.ruu.app.jeeeraaah.client.fx.task;
	opens de.ruu.app.jeeeraaah.client.fx.task.view;

	requires de.ruu.lib.fx.comp;
	requires de.ruu.app.jeeeraaah.jpadto;
	requires de.ruu.app.jeeeraaah.common;
	requires de.ruu.lib.cdi.se;
	requires de.ruu.lib.fx.core;

	requires static lombok;
	requires org.slf4j;
}