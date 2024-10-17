module de.ruu.app.jeeeraaah.client.fx
{
	exports de.ruu.app.jeeeraaah.client.fx;
	exports de.ruu.app.jeeeraaah.client.fx.task.view;
	exports de.ruu.app.jeeeraaah.client.fx.taskgroup;

	opens de.ruu.app.jeeeraaah.client.fx;
	opens de.ruu.app.jeeeraaah.client.fx.task;
	opens de.ruu.app.jeeeraaah.client.fx.task.view;
	opens de.ruu.app.jeeeraaah.client.fx.task.editor;
	opens de.ruu.app.jeeeraaah.client.fx.taskgroup;

	requires de.ruu.lib.fx.comp;
	requires de.ruu.lib.cdi.se;
	requires de.ruu.lib.fx.core;

	requires de.ruu.app.jeeeraaah.client.rs;

	requires de.ruu.app.jeeeraaah.jpadto;
	requires de.ruu.app.jeeeraaah.common;

	requires de.ruu.lib.jpa.core;
	requires de.ruu.lib.util;

	requires static lombok;
	requires org.slf4j;
}