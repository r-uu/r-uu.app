package de.ruu.app.jeeeraaah.common;

public interface Paths
{
	/** general purpose constants */
	String BY_ID = "/{id}";

	/** constants for application */
	String PATH_TO_APP                        = "/jeee-raaah";

	/** constants for domain taskgroup */
	String PATH_APPENDER_TO_DOMAIN_TASK_GROUP = "/taskgroup";
	/** constants for domain task */
	String PATH_APPENDER_TO_DOMAIN_TASK       = "/task";

	/** special purpose constants */
	String BY_ID_WITH_TASKS = BY_ID + "/optionalTasks";
}