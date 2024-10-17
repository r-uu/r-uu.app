package de.ruu.app.jeeeraaah.common;

public interface Paths
{
	/** general purpose constants */
	String BY_ID = "/{id}";

	/** constants for application */
	String PATH_TO_APP               = "/jeee-raaa";

	/** constants for domain taskgroup */
	String PATH_TO_DOMAIN_TASK_GROUP = PATH_TO_APP + "/taskgroup";
	/** constants for domain task */
	String PATH_TO_DOMAIN_TASK       = PATH_TO_APP + "/task";

	/** special purpose constants */
	String BY_ID_WITH_TASKS          = BY_ID + "/optionalTasks";
}