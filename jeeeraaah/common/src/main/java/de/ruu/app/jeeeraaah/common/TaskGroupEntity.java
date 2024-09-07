package de.ruu.app.jeeeraaah.common;

import de.ruu.lib.jpa.core.mapstruct.AbstractMappedEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
// @Getter(AccessLevel.NONE}))
@Setter                   // generate setter methods for all fields using lombok unless configured otherwise ({@code
// @Setter(AccessLevel.NONE}))
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
// with ide, fluent accessors will (usually / by default) be ignored by mapstruct
@RequiredArgsConstructor
@NoArgsConstructor(access = PROTECTED, force = true) // generate no args constructor for jsonb, jaxb, jpa, mapstruct, ...
@Entity
@Table(schema = "app_test", name = "task_group")
public class TaskGroupEntity extends AbstractMappedEntity<TaskGroupDTO> implements TaskGroup
{
	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@NonNull
	@Setter(AccessLevel.NONE)
	@Column(unique=true, nullable=false)
	private String name;

	/**
	 * prevent direct access to this modifiable set from outside this class, use {@link #addTask(TaskEntity)} and
	 * {@link #removeTask(TaskEntity)} to modify the set
	 * <p>
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@Nullable
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE) // provide handmade getter that returns unmodifiable
	@Setter(AccessLevel.NONE) // no setter at all
	@OneToMany
	(
			mappedBy = TaskEntity_.TASK_GROUP,
			// do not use cascade REMOVE in to-many relations as this may result in cascading deletes that wipe out both sides
			// of the relation entirely
			cascade  = { CascadeType.PERSIST, CascadeType.MERGE }
	)
	private Set<TaskEntity> tasks;

	@Override
	public @NonNull String name() { return name; }

	@Override
	public @NonNull Set<Task> tasks()
	{
		return Set.of();
	}

	@Override
	public void afterMapping(@NonNull TaskGroupDTO input)
	{

	}

	@Override
	public @NonNull TaskGroupDTO toTarget()
	{
		return null;
	}
}
