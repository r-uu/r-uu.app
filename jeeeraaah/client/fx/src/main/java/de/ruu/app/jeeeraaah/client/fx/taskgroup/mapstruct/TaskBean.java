package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.client.fx.task.TaskFXBean;
import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.jpadto.TaskEntity;
import de.ruu.lib.jpa.core.AbstractEntity;
import de.ruu.lib.mapstruct.BiMappedTarget;
import de.ruu.lib.util.Strings;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * JavaBean {@link TaskBean} implementing business logic
 * <p>
 * could as well extend {@link de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO}
 * but would not be persistable easily ...
 */
@EqualsAndHashCode
@Getter
@Setter
@Accessors(fluent = true)
public class TaskBean
		extends AbstractEntity<TaskDTO>
		implements
				TaskEntity<TaskGroupBean, TaskBean>,
				BiMappedTarget<TaskDTO>,
				BiMappedFXSource<TaskFXBean>
{
	@Setter(AccessLevel.NONE)
	@Nullable private Long  id;
	@Setter(AccessLevel.NONE)
	@Nullable private Short version;

	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@Setter(AccessLevel.NONE)
	@NonNull  private String    name;
	@Nullable private String    description;
	@Nullable private LocalDate startEstimated;
	@Nullable private LocalDate startActual;
	@Nullable private LocalDate finishEstimated;
	@Nullable private LocalDate finishActual;
	@Nullable private Duration  effortEstimated;
	@Nullable private Duration  effortActual;

	/** mutable, but not nullable */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull private TaskGroupBean taskGroup;

	/** mutable nullable */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns optional
	@Setter(AccessLevel.NONE) // provide handmade setter that handles bidirectional relation properly
	@Nullable private TaskBean parent;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addChild(TaskBean)} and
	 * {@link #removeChild(TaskBean)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all, use add method instead
//	@Nullable private Set<Task<TaskGroup<TaskBean>, TaskBean>> children;
	@Nullable private Set<TaskBean> children;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addPredecessor(TaskBean)} and
	 * {@link #removePredecessor(TaskBean)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all
	@Nullable private Set<TaskBean> predecessors;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addSuccessor(TaskBean)} and
	 * {@link #removeSuccessor(TaskBean)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all, use add method instead
	@Nullable private Set<TaskBean> successors;

	///////////////
	// constructors
	///////////////

	/** necessary for mapstruct, ... */
	protected TaskBean() { }

	public TaskBean(@NonNull TaskGroupBean taskGroup) { this.taskGroup = taskGroup; }

	/**
	 * Maps optional return values of {@link TaskDTO} field accessors to java bean style fields. This cannot be done by
	 * mapstruct.
	 *
	 * @param task
	 */
	@Override public void beforeMapping(@NonNull TaskDTO task)
	{
		mapIdAndVersion(task);

		name      = task.name();
		taskGroup = Mapper.INSTANCE.map((TaskGroupDTO) task.taskGroup());

		task.description    ().ifPresent(this::description);
		task.startEstimated ().ifPresent(this::startEstimated);
		task.startActual    ().ifPresent(this::startActual);
		task.finishEstimated().ifPresent(this::finishEstimated);
		task.finishActual   ().ifPresent(this::finishActual);
		task.effortEstimated().ifPresent(this::effortEstimated);
		task.effortActual   ().ifPresent(this::effortActual);

		task.parent      ().ifPresent(t  -> parent(                        Mapper.INSTANCE.lookupOrCreate((TaskDTO) t)));

		task.children    ().ifPresent(ts -> ts.forEach(t -> addChild      (Mapper.INSTANCE.lookupOrCreate((TaskDTO) t))));
		task.predecessors().ifPresent(ts -> ts.forEach(t -> addPredecessor(Mapper.INSTANCE.lookupOrCreate((TaskDTO) t))));
		task.successors  ().ifPresent(ts -> ts.forEach(t -> addSuccessor  (Mapper.INSTANCE.lookupOrCreate((TaskDTO) t))));
	}
	@Override public void afterMapping (@NonNull TaskDTO input) { }

	@Override public @NonNull TaskDTO toSource() { return Mapper.INSTANCE.map(this); }

	@Override public void beforeMapping(@NonNull TaskFXBean input)
	{
		mapIdAndVersion(input);
	}

	@Override public void afterMapping (@NonNull TaskFXBean input) { }

	@Override public @NonNull TaskFXBean toFXTarget() { return MapperFX.INSTANCE.map(this); }

	////////////////////////////////////////////////////////////////////////
	// fluent style accessors generated by lombok if not specified otherwise
	////////////////////////////////////////////////////////////////////////

	/**
	 * manually created fluent setter with extra parameter check (see throws documentation)
	 * @param name non-null, non-empty, non-blank
	 * @return {@code this}
	 * @throws IllegalArgumentException if {@code name} parameter is empty or blank
	 * @throws NullPointerException     if {@code name} parameter is {@code null}
	 */
	@NonNull public TaskBean name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// java bean style accessors for those who do not work with fluent style accessors (mapstruct)
	//////////////////////////////////////////////////////////////////////////////////////////////

	@NonNull
	public String getName()                     { return name;  }
	public void   setName(@NonNull String name) {   name(name); }

	@Nullable
	public String getDescription()                             { return             description; }
	public void   setDescription(@Nullable String description) { this.description = description; }

	@Nullable
	public LocalDate getStartEstimated()                                   { return                startEstimated; }
	public void      setStartEstimated(@Nullable LocalDate startEstimated) { this.startEstimated = startEstimated; }

	@Nullable
	public LocalDate getStartActual()                                {             return startActual; }
	public void      setStartActual(@Nullable LocalDate startActual) { this.startActual = startActual; }

	@Nullable
	public LocalDate getFinishEstimated()                                    { return                 finishEstimated; }
	public void      setFinishEstimated(@Nullable LocalDate finishEstimated) { this.finishEstimated = finishEstimated; }

	@Nullable
	public LocalDate getFinishActual()                                 { return              finishActual; }
	public void      setFinishActual(@Nullable LocalDate finishActual) { this.finishActual = finishActual; }

	@Nullable
	public Duration getEffortEstimated()                                   {                 return effortEstimated; }
	public void     setEffortEstimated(@Nullable Duration effortEstimated) { this.effortEstimated = effortEstimated; }

	@Nullable
	public Duration getEffortActual()                                { return              effortActual; }
	public void     setEffortActual(@Nullable Duration effortActual) { this.effortActual = effortActual; }

	@Override @NonNull public TaskGroupBean       taskGroup      () { return taskGroup; }
	@Override @NonNull public String              name           () { return name;      }
	@Override @NonNull public Optional<String>    description    () { return Optional.ofNullable(description);     }
	@Override @NonNull public Optional<LocalDate> startEstimated () { return Optional.ofNullable(startEstimated);  }
	@Override @NonNull public Optional<LocalDate> finishEstimated() { return Optional.ofNullable(finishEstimated); }
	@Override @NonNull public Optional<LocalDate> startActual    () { return Optional.ofNullable(startActual);     }
	@Override @NonNull public Optional<LocalDate> finishActual   () { return Optional.ofNullable(finishActual);    }
	@Override @NonNull public Optional<Duration>  effortEstimated() { return Optional.ofNullable(effortEstimated); }
	@Override @NonNull public Optional<Duration>  effortActual   () { return Optional.ofNullable(effortActual);    }

	@Override @NonNull public Optional<TaskBean>  parent         () { return Optional.ofNullable(parent);          }

	/** @return {@link #children wrapped in unmodifiable      */
	@Override @NonNull public Optional<Set<TaskBean>> children()
	{
		if (isNull(children)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(children));
	}
	/** @return {@link #predecessors} wrapped in unmodifiable */
	@Override @NonNull public Optional<Set<TaskBean>> predecessors()
	{
		if (isNull(predecessors)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(predecessors));
	}
	/** @return {@link #successors} wrapped in unmodifiable   */
	@Override @NonNull public Optional<Set<TaskBean>> successors()
	{
		if (isNull(successors)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(successors));
	}

	///////////////////////
	// additional accessors
	///////////////////////

	////////////////////////
	// relationship handling
	////////////////////////

	/** @throws IllegalArgumentException if {@code parent} is {@code this} */
	@NonNull public Optional<TaskBean> parent(@Nullable TaskBean parent)
	{
		if (parent == this) throw new IllegalArgumentException("parent must not be this");

		this.parent = parent;

		if (parent != null)
				parent.nonNullChildren().add(this);

		return Optional.of(this);
	}

	/**
	 * @param task the {@link Task} to be added as child
	 * @return {@code true} if operation succeeded, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code task} is identical to {@code this} task
	 * @throws IllegalArgumentException if {@code task} is a predecessor of {@code this} task
	 * @throws IllegalArgumentException if {@code task} is a successor of {@code this} task
	 * @throws IllegalArgumentException if {@code task} is already child of {@code this} task
	 */
	@NonNull public boolean addChild(@NonNull TaskBean task)
	{
		if (task == this)
				throw new IllegalArgumentException("task can not be child of itself");
		if (predecessorsContains(task))
				throw new IllegalArgumentException(
						"task can not be predecessor for the same task at the same time");
		if (successorsContains(task))
				throw new IllegalArgumentException(
						"task can not be successor for the same task at the same time");

		if (childrenContains(task)) return false; // no-op

		// update bidirectional relation
		task.parent = this;
		nonNullChildren().add(task);

		return true;
	}

	/**
	 * @param task the {@link Task} to be added as predecessor
	 * @return {@code this}
	 * @throws IllegalArgumentException if {@code task} is identical to {@code this} task
	 * @throws IllegalArgumentException if {@code task} is already predecessor of {@code this} task
	 * @throws IllegalArgumentException if {@code task} is a child of {@code this} task
	 * @throws IllegalStateException    if {@code this} could not be added to {@link #successors} of {@code task}
	 */
	@NonNull public boolean addPredecessor(@NonNull TaskBean task)
	{
		if (task == this)
				throw new IllegalArgumentException("task can not be predecessor of itself");
		if (successorsContains(task))
				throw new IllegalArgumentException(
						"task can not be predecessor and successor for the same task at the same time");
		if (childrenContains(task))
				throw new IllegalArgumentException("a task's child can not be predecessor for it's parent");

		if (predecessorsContains(task)) return false; // no-op

		// update bidirectional relation
		if (task.nonNullSuccessors().add(this))
		{
			nonNullPredecessors().add(task);
			return true;
		}

		throw new IllegalStateException("could not add this to successors of task");
	}

	/**
	 * @param task the {@link Task} to be added as predecessor
	 * @return {@code true} if operation succeeded, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code task} is identical to {@code this} task
	 * @throws IllegalArgumentException if {@code task} is already predecessor of {@code this} task
	 * @throws IllegalArgumentException if {@code task} is a child of {@code this} task
	 * @throws IllegalStateException    if {@code this} could not be added to {@link #predecessors()} of {@code task}
	 */
	@NonNull public boolean addSuccessor(@NonNull TaskBean task)
	{
		if (task == this)
				throw new IllegalArgumentException("task can not be successor of itself");
		if (predecessorsContains(task))
				throw new IllegalArgumentException(
						"task can not be predecessor and successor for the same task at the same time");
		if (childrenContains(task))
				throw new IllegalArgumentException("a task's child can not be successor for it's parent");

		if (successorsContains(task)) return false; // no-op

		// update bidirectional relation
		if (task.nonNullPredecessors().add(this))
		{
			nonNullSuccessors().add(task);
			return true;
		}

		throw new IllegalStateException("could not add this to predecessors of task");
	}

	public boolean removePredecessor(@NonNull TaskBean task)
	{
		if (nonNull(task.successors) && nonNull(predecessors))
		{
			if (task.successors.remove(this))
					return predecessors.remove(task);
		}

		throw new IllegalStateException("could not remove this from successors of task");
	}

	public boolean removeSuccessor(@NonNull TaskBean task)
	{
		if (nonNull(task.predecessors) && nonNull(successors))
		{
			if (task.predecessors.remove(this))
					return successors.remove(task);
		}

		throw new IllegalStateException("could not remove this from predecessors of task");
	}

	public boolean removeChild(@NonNull TaskBean child)
	{
		if (nonNull(children))
		{
			// TODO should not parent == this?
			TaskBean parent = child.parent; // remember parent in case removal has to be rolled back

			child.parent = null;                        // remove parent in child
			if (children.remove(child)) return true;
			child.parent = parent;              // rollback removal (reset parent in child)
		}

		throw new IllegalStateException("could not remove child from parent");
	}

	/** {@code null} safe check for containment */
	private boolean predecessorsContains(TaskBean entity)
	{
		if (isNull(predecessors)) return false;
		return predecessors.contains(entity);
	}

	/** {@code null} safe check for containment */
	private boolean successorsContains(TaskBean entity)
	{
		if (isNull(successors)) return false;
		return successors.contains(entity);
	}

	/** {@code null} safe check for containment */
	private boolean childrenContains(TaskBean entity)
	{
		if (isNull(children)) return false;
		return children.contains(entity);
	}

	private Set<TaskBean> nonNullChildren()
	{
		if (isNull(children)) children = new HashSet<>();
		return children;
	}

	private Set<TaskBean> nonNullPredecessors()
	{
		if (isNull(predecessors)) predecessors = new HashSet<>();
		return predecessors;
	}

	private Set<TaskBean> nonNullSuccessors()
	{
		if (isNull(successors)) successors = new HashSet<>();
		return successors;
	}
}