package de.ruu.app.jeeeraaah.common;

import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

/**
 * Generic, technology (JPA, JSONB, JAXB, MapStruct, ...) agnostic interface for tasks
 *
 * @param <T>  {@link Task} implementation for {@link #parent()}, {@link #children()}, {@link #predecessors()} and
 *             {@link #successors()} of this task
 * @param <TG> {@link TaskGroup} implementation for the task group that all {@link #parent()}, {@link #children()},
 *             {@link #predecessors()} and {@link #successors()} belong to
 */
public interface Task<TG extends TaskGroup<T>, T extends Task<TG, T>>
{
	@NonNull TG                  taskGroup      ();
	@NonNull String              name           ();
	@NonNull Optional<String>    description    ();
	@NonNull Optional<LocalDate> startEstimated ();
	@NonNull Optional<LocalDate> startActual    ();
	@NonNull Optional<LocalDate> finishEstimated();
	@NonNull Optional<LocalDate> finishActual   ();
	@NonNull Optional<Duration>  effortEstimated();
	@NonNull Optional<Duration>  effortActual   ();

	@NonNull Task<TG, T> description    (String    description    );
	@NonNull Task<TG, T> startEstimated (LocalDate startEstimated );
	@NonNull Task<TG, T> startActual    (LocalDate startActual    );
	@NonNull Task<TG, T> finishEstimated(LocalDate finishEstimated);
	@NonNull Task<TG, T> finishActual   (LocalDate finishActual   );
	@NonNull Task<TG, T> effortEstimated(Duration  effortEstimated);
	@NonNull Task<TG, T> effortActual   (Duration  effortActual   );

	/** @return superordinate task */
	@NonNull Optional<T> parent();
	@NonNull          T  parent(T parent);

	/**
	 * @return subordinate tasks
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load children, present optional but empty {@code Set} means, no children could be loaded
	 */
	@NonNull Optional<Set<T>> children();
	boolean addChild   (T child);
	boolean removeChild(T child);

	/**
	 * @return tasks that have to be finished before this task can start
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load predecessors, present optional but empty {@code Set} means, no predecessors could be
	 *         loaded
	 */
	@NonNull Optional<Set<T>> predecessors();
	boolean addPredecessor   (T child);
	boolean removePredecessor(T child);

	/**
	 * @return tasks that can not start until this task is finished
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load successors, present optional but empty {@code Set} means, no successors could be loaded
	 */
	@NonNull Optional<Set<T>> successors();
	boolean addSuccessor   (T child);
	boolean removeSuccessor(T child);
}