package de.ruu.app.jeeeraaah.common;

import jakarta.annotation.Nullable;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface Task
{
//	@NonNull
//	UUID      uuid();
	@NonNull
	TaskGroup taskGroup();
	@NonNull
	Long      id();
	@NonNull
	String    name();
	@Nullable
	String    description();
	@Nullable
	LocalDate startEstimated();
	@Nullable
	LocalDate finishEstimated();
	@NonNull
	LocalDate startActual();
	@NonNull
	LocalDate finishActual();
	@NonNull
	Duration  effortEstimated();
	@NonNull
	Duration  effortActual();

	/**
	 * @return tasks that have to be finished before this task can start
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load predecessors, present optional but empty {@code Set} means, no predecessors could be
	 *         loaded
	 */
	@NonNull
	Optional<Set<Task>> predecessors();

	/**
	 * @return tasks that can not start until this task is finished
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load successors, present optional but empty {@code Set} means, no successors could be loaded
	 */
	@NonNull
	Optional<Set<Task>> successors();

	/** @return superordinate task */
	@NonNull
	Optional<Task> parent();

	/**
	 * @return subordinate tasks
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load children, present optional but empty {@code Set} means, no children could be loaded
	 */
	@NonNull
	Optional<Set<Task>> children();
}