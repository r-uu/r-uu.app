package de.ruu.app.jeeeraaah.client.fx.taskgroup;

import de.ruu.lib.fx.comp.DefaultFXCView;
import de.ruu.lib.fx.comp.FXCViewService;

/**
 * Java FX Component {@link TaskGroupManagement}
 * <p>
 * generated by {@code de.ruu.lib.gen.java.fx.comp.GeneratorFXCView} at 2024.10.12 07:54:38:615
 */
public class TaskGroupManagement extends DefaultFXCView
{
	@Override public TaskGroupManagementService getService()
	{
		FXCViewService service = super.getService();

		if (service instanceof FXCViewService) return (TaskGroupManagementService) service;

		throw new IllegalStateException(
				"unexpected service type " + service.getClass().getName()
						+ ", expected " + TaskGroupManagementService.class.getName());
	}
}