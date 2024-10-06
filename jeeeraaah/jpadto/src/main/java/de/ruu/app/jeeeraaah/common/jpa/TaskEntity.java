package de.ruu.app.jeeeraaah.common.jpa;

import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.dto.TaskDTO;
import de.ruu.app.jeeeraaah.common.jpadto.Mapper;
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
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
                          // @Getter(AccessLevel.NONE}))
@Setter                   // generate setter methods for all fields using lombok unless configured otherwise ({@code
                          // @Setter(AccessLevel.NONE}))
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
//@RequiredArgsConstructor// provide handmade required args constructor to properly handle relationships
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true) // generate no args constructor for jsonb, jaxb, jpa, mapstruct, ...
@Entity
@Table(schema = "app_jeeeraaah_test", name = "task")
public class TaskEntity extends AbstractMappedEntity<TaskDTO> implements Task
{
//	private UUID            uuid = new UUID();
	/** mutable, but not nullable */

	// no java-bean-style getter here, mapstruct will ignore fields without bean-style-accessor so mapping can be
	// controlled in beforeMapping
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull
	@Setter
	@ManyToOne
	@JoinColumn(name = "idTaskGroup")
	private TaskGroupEntity taskGroup;

	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@NonNull
	@Setter(AccessLevel.NONE)
	private String    name;
	@Nullable
	private String    description;
	@Nullable
	private LocalDate startEstimated;
	@Nullable
	private LocalDate finishEstimated;
	@Nullable
	private Duration  effortEstimated;
	@Nullable
	private LocalDate startActual;
	@Nullable
	private LocalDate finishActual;
	@Nullable
	private Duration  effortActual;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addPredecessor(TaskEntity)} and
	 * {@link #removePredecessor(TaskEntity)} to modify the set
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
	private Set<TaskEntity> predecessors;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addSuccessor(TaskEntity)} and
	 * {@link #removeSuccessor(TaskEntity)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@Nullable
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all, use add method instead
	@ManyToMany
	(
			mappedBy = TaskEntity_.PREDECESSORS,
			// do not use cascade REMOVE in to-many relations as this may result in cascading deletes that wipe out both sides
			// of the relation entirely
			cascade  = { CascadeType.PERSIST, CascadeType.MERGE }
	)
	private Set<TaskEntity> successors;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addChild(TaskEntity)} and
	 * {@link #removeChild(TaskEntity)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@Nullable
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all, use add method instead
	@OneToMany
	(
			mappedBy = TaskEntity_.PARENT,
			// do not use cascade REMOVE in to-many relations as this may result in cascading deletes that wipe out both sides
			// of the relation entirely
			cascade  = { CascadeType.PERSIST, CascadeType.MERGE }
	)
	private Set<TaskEntity> children;

	/** mutable nullable */
	@Nullable
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns optional
	@Setter(AccessLevel.NONE) // provide handmade setter that handles bidirectional relation properly
	@ManyToOne
	@JoinColumn(name = "idParent")
	private TaskEntity      parent;

	/** provide handmade required args constructor to properly handle relationships */
	public TaskEntity(@NonNull TaskGroupEntity taskGroup, @NonNull String name)
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
	 * @param name non-null, non-empty, non-blank
	 * @return {@code this}
	 * @throws IllegalArgumentException if {@code name} parameter is empty or blank
	 * @throws NullPointerException     if {@code name} parameter is {@code null}
	 */
	@NonNull
	public TaskEntity name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	@NonNull
	public TaskEntity parent(@Nullable TaskEntity parent)
	{
		if (parent == this) throw new IllegalArgumentException("parent must not be this");

		this.parent = parent;

		if (parent != null)
				parent.nonNullChildren().add(this);

		return this;
	}

	/** @return {@link #predecessors} wrapped in unmodifiable */
	@Override
	@NonNull
	public Optional<Set<Task>> predecessors()
	{
		Optional<Set<TaskEntity>> predecessors = optionalPredecessors();
		if (predecessors.isPresent())
				return Optional.of(Collections.unmodifiableSet(predecessors.get()));
		return Optional.empty();
	}

	/** @return {@link #predecessors} wrapped in unmodifiable */
	@Override
	@NonNull
	public Optional<Set<Task>> successors()
	{
		Optional<Set<TaskEntity>> successors = optionalSuccessors();
		if (successors.isPresent())
				return Optional.of(Collections.unmodifiableSet(successors.get()));
		return Optional.empty();
	}

	@Override
	@NonNull
	public Optional<Task> parent() { return Optional.ofNullable(parent); }

	/** @return {@link #predecessors} wrapped in unmodifiable */
	@Override
	@NonNull
	public Optional<Set<Task>> children()
	{
		Optional<Set<TaskEntity>> children = optionalChildren();
		if (children.isPresent())
				return Optional.of(Collections.unmodifiableSet(children.get()));
		return Optional.empty();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// java bean style accessors for those who do not work with fluent style accessors (mapstruct)
	//////////////////////////////////////////////////////////////////////////////////////////////
	@NonNull
	public String     getName()                                      { return          name();            }
	public void       setName(  @NonNull String      name)           { name           (name);             }
	@Nullable
	public String     getDescription()                               { return          description();     }
	public void       setDescription(    String      description)    { description    (description);      }
	@Nullable
	public LocalDate  getStartEstimated()                            { return          startEstimated();  }
	public void       setStartEstimated( LocalDate   startEstimated) { startEstimated (startEstimated);   }
	@Nullable
	public LocalDate  getFinishEstimated()                           { return          finishEstimated(); }
	public void       setFinishEstimated(LocalDate  finishEstimated) { finishEstimated(finishEstimated);  }
	@Nullable
	public Duration   getEffortEstimated()                           { return          effortEstimated(); }
	public void       setEffortEstimated(Duration   effortEstimated) { effortEstimated(effortEstimated);  }
	@Nullable
	public LocalDate  getStartActual()                               { return          startActual();     }
	public void       setStartActual(    LocalDate  startActual)     { startActual    (startActual);      }
	@Nullable
	public LocalDate  getFinishActual()                              { return          finishActual();    }
	public void       setFinishActual(   LocalDate  finishActual)    { finishActual   (finishActual);     }
	@Nullable
	public Duration   getEffortActual()                              { return          effortActual();    }
	public void       setEffortActual(   Duration   effortActual)    { effortActual   (effortActual);     }
//	@Nullable
//	public TaskEntity getParent()                                    { return          parent;            }
//	public void       setParent(         TaskEntity parent)          { parent         (parent);           }

	///////////////////////
	// additional accessors
	///////////////////////
	/** @return optional unmodifiable */
	public @NonNull Optional<Set<TaskEntity>> optionalPredecessors()
	{
		if (Objects.isNull(predecessors)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(predecessors));
	}

	/** @return optional unmodifiable */
	public @NonNull Optional<Set<TaskEntity>> optionalSuccessors()
	{
		if (Objects.isNull(successors)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(successors));
	}

	/** @return optional unmodifiable */
	public @NonNull Optional<Set<TaskEntity>> optionalChildren()
	{
		if (Objects.isNull(children)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(children));
	}

	public @NonNull Optional<TaskEntity> optionalParent() { return Optional.ofNullable(parent); }

	////////////////////////
	// relationship handling
	////////////////////////
	/**
	 * @param entity the {@link Task} to be added as predecessor
	 * @return {@code true} if operation succeeded, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code entity} is identical to {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is already predecessor of {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is a child of {@code this} task
	 * @throws IllegalStateException    if {@code this} could not be added to {@link #successors} of {@code entity}
	 */
	@NonNull
	public TaskEntity addPredecessor(@NonNull TaskEntity entity)
	{
		if (entity == this)
				throw new IllegalArgumentException("entity can not be predecessor of itself");
		if (successorsContains(entity))
				throw new IllegalArgumentException(
						"entity can not be predecessor and successor for the same task at the same time");
		if (childrenContains(entity))
				throw new IllegalArgumentException("a task's child can not be predecessor for it's parent");

		if (predecessorsContains(entity)) return this; // no-op

		// update bidirectional relation
		if (entity.nonNullSuccessors().add(this))
		{
			nonNullPredecessors().add(entity);
			return this;
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
	@NonNull
	public TaskEntity addSuccessor(@NonNull TaskEntity entity)
	{
		if (entity == this)
				throw new IllegalArgumentException("entity can not be successor of itself");
		if (predecessorsContains(entity))
				throw new IllegalArgumentException(
						"entity can not be predecessor and successor for the same task at the same time");
		if (childrenContains(entity))
				throw new IllegalArgumentException("a task's child can not be successor for it's parent");

		if (successorsContains(entity)) return this; // no-op

		// update bidirectional relation
		if (entity.nonNullPredecessors().add(this))
		{
			nonNullSuccessors().add(entity);
			return this;
		}

		throw new IllegalStateException("could not add this to predecessors of entity");
	}

	/**
	 * @param entity the {@link Task} to be added as child
	 * @return {@code true} if operation succeeded, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code entity} is identical to {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is a predecessor of {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is a successor of {@code this} task
	 * @throws IllegalArgumentException if {@code entity} is already child of {@code this} task
	 */
	@NonNull
	public TaskEntity addChild(@NonNull TaskEntity entity)
	{
		if (entity == this)
				throw new IllegalArgumentException("entity can not be child of itself");
		if (predecessorsContains(entity))
				throw new IllegalArgumentException(
						"entity can not be predecessor for the same task at the same time");
		if (successorsContains(entity))
				throw new IllegalArgumentException(
						"entity can not be successor for the same task at the same time");

		if (childrenContains(entity)) return this; // no-op

		// update bidirectional relation
		entity.parent = this;
		nonNullChildren().add(entity);

		return this;
	}

	public boolean removePredecessor(@NonNull TaskEntity entity)
	{
		if (entity.successors.remove(this))
				return predecessors.remove(entity);

		throw new IllegalStateException("could not remove this from successors of entity");
	}

	public boolean removeSuccessor(@NonNull TaskEntity entity)
	{
		if (entity.predecessors.remove(this))
				return successors.remove(entity);

		throw new IllegalStateException("could not remove this from predecessors of entity");
	}

	public boolean removeChild(@NonNull TaskEntity entity)
	{
		TaskEntity entityParent = entity.parent;

		entity.parent = null;
		boolean result = children.remove(entity);

		if (result == false)
		{
			// rollback changes
			entity.parent = entityParent;
		}
		return result;
	}

	@Override public void beforeMapping(@NonNull TaskDTO input)
	{
		super.beforeMapping(input);

		input.optionalPredecessors().ifPresent(ts -> ts.forEach(p -> lookupOrMapPredecessor(p)));
		input.optionalSuccessors  ().ifPresent(ts -> ts.forEach(s -> lookupOrMapSuccessor  (s)));
		input.optionalChildren    ().ifPresent(ts -> ts.forEach(c -> lookupOrMapChild      (c)));
		input.optionalParent      ().ifPresent(                 p -> lookupOrMapParent     (p));
	}

	@Override public void afterMapping(@NonNull TaskDTO input) { }

	@Override public @NonNull TaskDTO toTarget() { return Mapper.INSTANCE.map(this); }

	private Set<TaskEntity> nonNullPredecessors()
	{
		if (isNull(predecessors)) predecessors = new HashSet<>();
		return predecessors;
	}

	private Set<TaskEntity> nonNullSuccessors()
	{
		if (isNull(children)) successors = new HashSet<>();
		return successors;
	}

	private Set<TaskEntity> nonNullChildren()
	{
		if (isNull(children)) children = new HashSet<>();
		return children;
	}

	/** {@code null} safe check for containment */
	private boolean predecessorsContains(TaskEntity entity)
	{
		if (isNull(predecessors)) return false;
		return predecessors.contains(entity);
	}

	/** {@code null} safe check for containment */
	private boolean successorsContains(TaskEntity entity)
	{
		if (isNull(successors)) return false;
		return successors.contains(entity);
	}

	/** {@code null} safe check for containment */
	private boolean childrenContains(TaskEntity entity)
	{
		if (isNull(children)) return false;
		return children.contains(entity);
	}

	private void lookupOrMapPredecessor(TaskDTO predecessor)
	{
		Optional<TaskEntity> optionalPredecessorEntity = Mapper.INSTANCE.getFromContext(predecessor);

		if (optionalPredecessorEntity.isPresent())
		{
			addPredecessor(optionalPredecessorEntity.get());
		}
		else
		{
			addPredecessor(predecessor.toSource());
		}
	}

	private void lookupOrMapSuccessor(TaskDTO successor)
	{
		Optional<TaskEntity> optionalSuccessorEntity = Mapper.INSTANCE.getFromContext(successor);

		if (optionalSuccessorEntity.isPresent())
		{
			addSuccessor(optionalSuccessorEntity.get());
		}
		else
		{
			addSuccessor(successor.toSource());
		}
	}

	private void lookupOrMapChild(TaskDTO child)
	{
		Optional<TaskEntity> optionalChildEntity = Mapper.INSTANCE.getFromContext(child);

		if (optionalChildEntity.isPresent())
		{
			addChild(optionalChildEntity.get());
		}
		else
		{
			addChild(child.toSource());
		}
	}

	private void lookupOrMapParent(TaskDTO parent)
	{
		Optional<TaskEntity> optionalParentEntity = Mapper.INSTANCE.getFromContext(parent);

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