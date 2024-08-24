package de.ruu.app.jeeeraaah.common;

import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

public interface Task
{
	Long      id();
	@NonNull
	String    name();
	String    description();
	LocalDate startEstimated();
	LocalDate finishEstimated();
	Duration  effortEstimated();
	LocalDate startActual();
	LocalDate finishActual();
	Duration  effortActual();

	/** @return tasks that have to be finished before this task can start */
	Collection<Task> predecessors();
	/** @return tasks that can only start after this task is finished */
	Collection<Task> successors();

	/** @return superordinate task */
	Task             parent();
	/** @return subordinate   tasks */
	Collection<Task> children();
}