package de.ruu.app.jeeeraaah.common.jpa;

import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.jpadto.Map_Task_JPA_DTO;
import de.ruu.app.jeeeraaah.common.jpadto.TaskEntity;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedEntity;
import de.ruu.lib.util.Strings;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
@Setter                   // generate setter methods for all fields using lombok unless configured otherwise ({@code
                          // @Setter(AccessLevel.NONE}))
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
@RequiredArgsConstructor// provide handmade required args constructor to properly handle relationships
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true) // generate no args constructor for jsonb, jaxb, jpa, mapstruct, ...
@Entity
@Table(schema = "app_jeeeraaah_test", name = "task")
public class TaskEntityJPA
		extends AbstractMappedEntity<TaskEntityDTO>
		implements
				TaskEntity<TaskGroupEntityJPA, TaskEntityJPA>
{
	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@Setter(AccessLevel.PROTECTED)
	@NonNull  private String    name;
	@Nullable private String    description;
	@Nullable private LocalDate startEstimated;
	@Nullable private LocalDate finishEstimated;
	@Nullable private Duration  effortEstimated;
	@Nullable private LocalDate startActual;
	@Nullable private LocalDate finishActual;
	@Nullable private Duration  effortActual;

	/** mutable non-null */
	// no java-bean-style getter here, mapstruct will ignore fields without bean-style-accessor so mapping can be
	// controlled in beforeMapping
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "idTaskGroup")
	@NonNull private TaskGroupEntityJPA taskGroup;

	/** mutable nullable */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns optional
	@Setter(AccessLevel.NONE) // provide handmade setter that handles bidirectional relation properly
	@ManyToOne
	@JoinColumn(name = "idParent")
	@Nullable private TaskEntityJPA     parent;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addChild(TaskEntityJPA)} and
	 * {@link #removeChild(TaskEntityJPA)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all, use add method instead
	@OneToMany
			(
					mappedBy = TaskEntityJPA_.PARENT,
					// do not use cascade REMOVE in to-many relations as this may result in cascading deletes that wipe out both sides
					// of the relation entirely
					cascade  = { CascadeType.PERSIST, CascadeType.MERGE }
			)
	@Nullable private Set<TaskEntityJPA> children;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addPredecessor(TaskEntityJPA)} and
	 * {@link #removePredecessor(TaskEntityJPA)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@Nullable
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all
	@ManyToMany
	(
//			mappedBy = TaskEntity_.SUCCESSORS,
			// do not use cascade REMOVE in to-many relations as this may result in cascading deletes that wipe out both sides
			// of the relation entirely
			cascade  = { CascadeType.PERSIST, CascadeType.MERGE }
	)
	@JoinTable
	(
			name               = "PREDECESSOR_SUCCESSOR",
			joinColumns        = { @JoinColumn(name = "idPredessor") },
			inverseJoinColumns = { @JoinColumn(name = "idSuccessor") }
	)
	private Set<TaskEntityJPA> predecessors;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addSuccessor(TaskEntityJPA)} and
	 * {@link #removeSuccessor(TaskEntityJPA)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all, use add method instead
	@ManyToMany
	(
			mappedBy = TaskEntityJPA_.PREDECESSORS,
			// do not use cascade REMOVE in to-many relations as this may result in cascading deletes that wipe out both sides
			// of the relation entirely
			cascade  = { CascadeType.PERSIST, CascadeType.MERGE }
	)
	@Nullable private Set<TaskEntityJPA> successors;

	///////////////
	// constructors
	///////////////

	/** provide handmade required args constructor to properly handle relationships */
	public TaskEntityJPA(@NonNull TaskGroupEntityJPA taskGroup, @NonNull String name)
	{
		this.taskGroup = taskGroup;
		name(name);
		taskGroup.addTask(this);
	}

	////////////////////////////////////////////////////////////////////////
	// fluent style accessors generated by lombok if not specified otherwise
	////////////////////////////////////////////////////////////////////////

	/**
	 * manually created fluent setter with extra parameter check (see throws documentation)
	 *
	 * @param name non-null, non-empty, non-blank
	 * @return {@code this}
	 * @throws IllegalArgumentException if {@code name} parameter is empty or blank
	 * @throws NullPointerException     if {@code name} parameter is {@code null}
	 */
	@NonNull public TaskEntityJPA name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	@Override @NonNull public TaskGroupEntityJPA      taskGroup      () { return taskGroup; }
	@Override @NonNull public String                  name           () { return name;      }

	@Override @NonNull public Optional<String>        description    () { return Optional.ofNullable(description);     }
	@Override @NonNull public Optional<LocalDate>     startEstimated () { return Optional.ofNullable(startEstimated);  }
	@Override @NonNull public Optional<LocalDate>     finishEstimated() { return Optional.ofNullable(finishEstimated); }
	@Override @NonNull public Optional<LocalDate>     startActual    () { return Optional.ofNullable(startActual);     }
	@Override @NonNull public Optional<LocalDate>     finishActual   () { return Optional.ofNullable(finishActual);    }
	@Override @NonNull public Optional<Duration>      effortEstimated() { return Optional.ofNullable(effortEstimated); }
	@Override @NonNull public Optional<Duration>      effortActual   () { return Optional.ofNullable(effortActual);    }

	@Override @NonNull public Optional<TaskEntityJPA> parent         () { return Optional.ofNullable(parent); }

	/** @return {@link #children wrapped in unmodifiable      */
	@Override @NonNull public Optional<Set<TaskEntityJPA>> children()
	{
		if (nonNull(children))
			return Optional.of(Collections.unmodifiableSet(children));
		return Optional.empty();
	}
	/** @return {@link #predecessors} wrapped in unmodifiable */
	@Override @NonNull public Optional<Set<TaskEntityJPA>> predecessors()
	{
		if (nonNull(predecessors))
			return Optional.of(Collections.unmodifiableSet(predecessors));
		return Optional.empty();
	}
	/** @return {@link #successors} wrapped in unmodifiable   */
	@Override @NonNull public Optional<Set<TaskEntityJPA>> successors()
	{
		if (nonNull(successors))
			return Optional.of(Collections.unmodifiableSet(successors));
		return Optional.empty();
	}

	////////////////////////
	// relationship handling
	////////////////////////

	/** @throws IllegalArgumentException if {@code parent} is {@code this} */
	@Override@NonNull public TaskEntityJPA parent(@Nullable TaskEntityJPA parent)
	{
		if (parent == this) throw new IllegalArgumentException("parent must not be this");
		this.parent = parent;
		if (nonNull(parent)) parent.nonNullChildren().add(this);
		return this;
	}

	/**
	 * @param entity the {@link Task} to be added as child
	 * @return {@code true} if operation succeeded, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code entity} is identical to {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is a predecessor of {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is a successor of {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is already child of {@code this} task
	 */
	@Override public boolean addChild(@NonNull TaskEntityJPA entity)
	{
		if (entity == this)
				throw new IllegalArgumentException("entity can not be child of itself");
		if (predecessorsContains(entity))
				throw new IllegalArgumentException(
						"entity can not be predecessor for the same task at the same time");
		if (successorsContains(entity))
				throw new IllegalArgumentException(
						"entity can not be successor for the same task at the same time");

		if (childrenContains(entity)) return false; // no-op

		// update bidirectional relation
		entity.parent = this;
		nonNullChildren().add(entity);

		return true;
	}

	/**
	 * @param entity the {@link Task} to be added as predecessor
	 * @return {@code true} if operation succeeded, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code entity} is identical to {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is already predecessor of {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is a child of {@code this} task
	 * @throws IllegalStateException    if {@code this} could not be added to {@link #successors} of {@code entity}
	 */
	@Override public boolean addPredecessor(@NonNull TaskEntityJPA entity)
	{
		if (entity == this)
				throw new IllegalArgumentException("entity can not be predecessor of itself");
		if (successorsContains(entity))
				throw new IllegalArgumentException(
						"entity can not be predecessor and successor for the same task at the same time");
		if (childrenContains(entity))
				throw new IllegalArgumentException("a task's child can not be predecessor for it's parent");

		if (predecessorsContains(entity)) return false; // no-op

		// update bidirectional relation
		if (entity.nonNullSuccessors().add(this))
		{
			nonNullPredecessors().add(entity);
			return true;
		}

		throw new IllegalStateException("could not add this to successors of entity");
	}

	/**
	 * @param entity the {@link Task} to be added as predecessor
	 * @return {@code true} if operation succeeded, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code entity} is identical to {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is already predecessor of {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is a child of {@code this} task
	 * @throws IllegalStateException    if {@code this} could not be added to {@link #predecessors()} of {@code entity}
	 */
	@Override public boolean addSuccessor(@NonNull TaskEntityJPA entity)
	{
		if (entity == this)
				throw new IllegalArgumentException("entity can not be successor of itself");
		if (predecessorsContains(entity))
				throw new IllegalArgumentException(
						"entity can not be predecessor and successor for the same task at the same time");
		if (childrenContains(entity))
				throw new IllegalArgumentException("a task's child can not be successor for it's parent");

		if (successorsContains(entity)) return false; // no-op

		// update bidirectional relation
		if (entity.nonNullPredecessors().add(this))
		{
			nonNullSuccessors().add(entity);
			return true;
		}

		throw new IllegalStateException("could not add this to predecessors of entity");
	}

	@Override public boolean removePredecessor(@NonNull TaskEntityJPA entity)
	{
		if (nonNull(entity.successors))
			if (entity.successors.remove(this))
				if (nonNull(predecessors))
					return predecessors.remove(entity);

		throw new IllegalStateException("could not remove this from successors of entity");
	}

	@Override public boolean removeSuccessor(@NonNull TaskEntityJPA entity)
	{
		if (nonNull(entity.predecessors))
			if (entity.predecessors.remove(this))
				if (nonNull(successors))
					return successors.remove(entity);

		throw new IllegalStateException("could not remove this from predecessors of entity");
	}

	@Override public boolean removeChild(@NonNull TaskEntityJPA entity)
	{
		TaskEntityJPA entityParent = entity.parent;

		if (nonNull(children))
		{
			entity.parent = null;
			boolean result = children.remove(entity);

			if (result == false)
			{
				// rollback changes
				entity.parent = entityParent;
			}
			return result;
		}

		throw new IllegalStateException("could not remove entity from children");
	}

	///////////////////////
	// additional accessors
	///////////////////////

	//////////////////////
	// mapstruct callbacks
	//////////////////////

	void beforeMapping(@NonNull TaskEntityDTO input)
	{
		super.beforeMapping(input);

		input.predecessors().ifPresent(ts -> ts.forEach(this::lookupOrMapPredecessor));
		input.successors  ().ifPresent(ts -> ts.forEach(this::lookupOrMapSuccessor));
		input.children    ().ifPresent(ts -> ts.forEach(this::lookupOrMapChild));
		input.parent      ().ifPresent(                 this::lookupOrMapParent);

		if (input.description    ().isPresent()) description    (input.description    ().get());
		if (input.startEstimated ().isPresent()) startEstimated (input.startEstimated ().get());
		if (input.startActual    ().isPresent()) startActual    (input.startActual    ().get());
		if (input.finishEstimated().isPresent()) finishEstimated(input.finishEstimated().get());
		if (input.finishActual   ().isPresent()) finishActual   (input.startActual    ().get());
		if (input.effortEstimated().isPresent()) effortEstimated(input.effortEstimated().get());
		if (input.effortActual   ().isPresent()) effortActual   (input.effortActual   ().get());
	}

	void afterMapping(@NonNull TaskEntityDTO input) { }

	@Override public @NonNull TaskEntityDTO toTarget() { return Map_Task_JPA_DTO.INSTANCE.map(this); }

	@NonNull private Set<TaskEntityJPA> nonNullPredecessors()
	{
		if (isNull(predecessors)) predecessors = new HashSet<>();
		return predecessors;
	}

	@NonNull private Set<TaskEntityJPA> nonNullSuccessors()
	{
		if (isNull(children)) successors = new HashSet<>();
		return successors;
	}

	@NonNull private Set<TaskEntityJPA> nonNullChildren()
	{
		if (isNull(children)) children = new HashSet<>();
		return children;
	}

	/** {@code null} safe check for containment */
	private boolean predecessorsContains(TaskEntityJPA entity)
	{
		if (isNull(predecessors)) return false;
		return predecessors.contains(entity);
	}

	/** {@code null} safe check for containment */
	private boolean successorsContains(TaskEntityJPA entity)
	{
		if (isNull(successors)) return false;
		return successors.contains(entity);
	}

	/** {@code null} safe check for containment */
	private boolean childrenContains(TaskEntityJPA entity)
	{
		if (isNull(children)) return false;
		return children.contains(entity);
	}

	private void lookupOrMapPredecessor(TaskEntityDTO predecessor)
	{
		Optional<TaskEntityJPA> optionalPredecessorEntity = Map_Task_JPA_DTO.INSTANCE.getFromContext(predecessor);

		if (optionalPredecessorEntity.isPresent())
		{
			addPredecessor(optionalPredecessorEntity.get());
		}
		else
		{
			addPredecessor(predecessor.toSource());
		}
	}

	private void lookupOrMapSuccessor(TaskEntityDTO successor)
	{
		Optional<TaskEntityJPA> optionalSuccessorEntity = Map_Task_JPA_DTO.INSTANCE.getFromContext(successor);

		if (optionalSuccessorEntity.isPresent())
		{
			addSuccessor(optionalSuccessorEntity.get());
		}
		else
		{
			addSuccessor(successor.toSource());
		}
	}

	private void lookupOrMapChild(TaskEntityDTO child)
	{
		Optional<TaskEntityJPA> optionalChildEntity = Map_Task_JPA_DTO.INSTANCE.getFromContext(child);

		if (optionalChildEntity.isPresent())
		{
			addChild(optionalChildEntity.get());
		}
		else
		{
			addChild(child.toSource());
		}
	}

	private void lookupOrMapParent(TaskEntityDTO parent)
	{
		Optional<TaskEntityJPA> optionalParentEntity = Map_Task_JPA_DTO.INSTANCE.getFromContext(parent);

		if (optionalParentEntity.isPresent())
		{
			parent(optionalParentEntity.get());
		}
		else
		{
			parent(parent.toSource());
		}
	}
}