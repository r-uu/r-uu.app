package de.ruu.app.jeeeraaah.common;

import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface Task
{
	Long                       id();
	@NonNull
	String                     name();
	@NonNull
	String                     description();
	@NonNull
	Optional<LocalDate>        startEstimated();
	@NonNull
	Optional<LocalDate>        finishEstimated();
	@NonNull
	Optional<Duration>         effortEstimated();
	@NonNull
	Optional<LocalDate>        startActual();
	@NonNull
	Optional<LocalDate>        finishActual();
	@NonNull
	Optional<Duration>         effortActual();

	/** @return tasks that have to be finished before this task can start */
	@NonNull
	Collection<? extends Task> predecessors();
	/** @return tasks that can only start after this task is finished */
	@NonNull
	Collection<? extends Task> successors();

	/** @return superordinate task */
	Optional<? extends Task>   parent();
	/** @return subordinate   tasks */
	@NonNull
	Collection<? extends Task> children();
}