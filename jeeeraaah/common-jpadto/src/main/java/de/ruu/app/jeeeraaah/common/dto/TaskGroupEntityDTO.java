package de.ruu.app.jeeeraaah.common.dto;

import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.jpa.TaskEntityJPA;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntityJPA;
import de.ruu.app.jeeeraaah.common.jpadto.Map_TaskGroup_JPA_DTO;
import de.ruu.app.jeeeraaah.common.jpadto.Map_Task_JPA_DTO;
import de.ruu.app.jeeeraaah.common.jpadto.TaskGroupEntity;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedDTO;
import de.ruu.lib.util.Strings;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

/** data transfer object */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
@Setter                   // generate setter methods for all fields using lombok unless configured otherwise ({@code
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true) // generate no args constructor for jsonb, jaxb, mapstruct, ...
public class TaskGroupEntityDTO
		extends AbstractMappedDTO<TaskGroupEntityJPA>
		implements
				TaskGroupEntity<TaskEntityDTO>
{
	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@Setter(AccessLevel.NONE)
	@NonNull  private String name;

	/** mutable nullable */
	@Nullable private String description;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addTask(TaskEntityDTO)} and
	 * {@link #removeTask(TaskEntityDTO)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all
	@Nullable private Set<TaskEntityDTO> tasks;

	///////////////
	// constructors
	///////////////

	/**
	 * provide hand-made required args constructor to guarantee usage of hand made accessors.
	 * @param name non-empty name
	 */
	public TaskGroupEntityDTO(@NonNull String name) { name(name); }

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
	@Override @NonNull public TaskGroupEntityDTO name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	@Override @NonNull public Optional<String> description() { return Optional.ofNullable(description); }

	/** @return optional unmodifiable */
	@Override public @NonNull Optional<Set<TaskEntityDTO>> tasks()
	{
		if (Objects.isNull(tasks)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(tasks));
	}

	///////////////////////
	// additional accessors
	///////////////////////

	// --- none ---

	////////////////////////
	// relationship handling
	////////////////////////
	/**
	 * @param dto the {@link Task} to be added as predecessor
	 * @return {@code this}
	 */
	@Override public boolean addTask(@NonNull TaskEntityDTO dto)
	{
		if (nonNullTasks().add(dto))
		{
			// update bidirectional relation
			dto.taskGroup(this);
			return true;
		}
		return false;
	}

	@Override public boolean removeTask(@NonNull TaskEntityDTO dto)
	{
		return tasks().map(ts -> ts.remove(dto)).orElse(false);
	}

	//////////////////////
	// mapstruct callbacks
	//////////////////////

	public void beforeMapping(@NonNull TaskGroupEntityJPA source)
	{
		super.beforeMapping(source); // maps id and version
		if (source.tasks().isPresent())
		{
			for (TaskEntityJPA task : source.tasks().get())
			{
				addTask(Map_Task_JPA_DTO.INSTANCE.map(task));
			}
		}
		// mapping of other fields is done via mapstruct using java-beans accessors
	}

//	public void beforeMapping(@NonNull TaskGroupEntity<TaskEntityDTO> source)
//	{
//		super.beforeMapping(source); // maps id and version
//		if (source.tasks().isPresent())
//		{
//			for (TaskEntityDTO task : source.tasks().get())
//			{
//				addTask(Map_Task_JPA_DTO.INSTANCE.map(task));
//			}
//		}
//		// mapping of other fields is done via mapstruct using java-beans accessors
//	}

	public void afterMapping (@NonNull TaskGroupEntityJPA source) { }

	@Override public @NonNull TaskGroupEntityJPA toSource() { return Map_TaskGroup_JPA_DTO.INSTANCE.map(this); }

	//////////////////////////////////////////////////////////////////////////////////////////////
	// java bean style accessors for those who do not work with fluent style accessors (mapstruct)
	//////////////////////////////////////////////////////////////////////////////////////////////

	@NonNull
	public String getName()                     { return name(); }
	public void   setName(@NonNull String name) { name(name); }

	@Nullable
	public String getDescription()                             { return description().orElse(null); }
	public void   setDescription(@Nullable String description) { description(description); }
	// do _NOT_ define getter for tasks to avoid handling of tasks by mapstruct automatism

	private @NonNull Set<TaskEntityDTO> nonNullTasks()
	{
		if (isNull(tasks)) tasks = new HashSet<>();
		return tasks;
	}
}