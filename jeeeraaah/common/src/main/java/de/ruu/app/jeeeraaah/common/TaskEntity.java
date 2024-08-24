package de.ruu.app.jeeeraaah.common;

import de.ruu.lib.jpa.core.mapstruct.AbstractMappedEntity;
import de.ruu.lib.util.Strings;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

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
public class TaskEntity extends AbstractMappedEntity<TaskDTO> implements Task
{
	@NonNull
	@Setter(AccessLevel.NONE)
	private String                 name;
	@NonNull
	private String                 description;
	@NonNull
	private Optional<LocalDate>    startEstimated;
	@NonNull
	private Optional<LocalDate>    finishEstimated;
	@NonNull
	private Optional<Duration>     effortEstimated;
	@NonNull
	private Optional<LocalDate>    startActual;
	@NonNull
	private Optional<LocalDate>    finishActual;
	@NonNull
	private Optional<Duration>     effortActual;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull
	private Collection<TaskEntity> predecessors;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull
	private Collection<TaskEntity> successors;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull
	private Optional<TaskEntity>   parent;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull
	private Collection<TaskEntity> children;

	@NonNull
	public String getName()                     { return name(); }
	public void   setName(@NonNull String name) { name  (name) ; }

	@NonNull
	public String getDescription()                            {      return description(); }
	public void   setDescription(@NonNull String description) { description(description) ; }

	@NonNull
	public Optional<LocalDate> getStartEstimated()
			{                                                     return startEstimated(); }
	public void                setStartEstimated(          LocalDate startEstimated)
			{                         startEstimated(Optional.ofNullable(startEstimated)); }

	@NonNull
	public Optional<LocalDate> getFinishEstimated()
			{                                                      return finishEstimated(); }
	public void                setFinishEstimated(          LocalDate finishEstimated)
			{                         finishEstimated(Optional.ofNullable(finishEstimated)); }

	@NonNull
	public Optional<Duration> getEffortEstimated()
			{                                                     return effortEstimated(); }
	public void               setEffortEstimated(           Duration effortEstimated)
			{                        effortEstimated(Optional.ofNullable(effortEstimated)); }

	@NonNull
	public Optional<LocalDate> getStartActual()
			{                                                  return startActual(); }
	public void                setStartActual(          LocalDate startActual)
			{                         startActual(Optional.ofNullable(startActual)); }

	@NonNull
	public Optional<LocalDate> getFinishActual()
			{                                                   return finishActual(); }
	public void                setFinishActual(          LocalDate finishActual)
			{                         finishActual(Optional.ofNullable(finishActual)); }

	@NonNull
	public Optional<Duration>  getEffortActual()
			{                                                   return effortActual(); }
	public void                setEffortActual(           Duration effortActual)
			{                         effortActual(Optional.ofNullable(effortActual)); }

	@NonNull
	public Collection<TaskEntity> getPredecessors()
			{                                                                  return predecessors(); }
	public void                   setPredecessors(@NonNull Collection<TaskEntity> predecessors)
			{                            predecessors(                                predecessors); }

	@NonNull
	public Collection<TaskEntity> getSuccessors()
			{                                                             return successors(); }
	public void                setSuccessors(@NonNull Collection<TaskEntity> successors)
			{                         successors(                                successors); }

	public Optional<TaskEntity> getParent()
			{                                              return parent(); }
	public void                 setParent(         TaskEntity parent)
			{                          parent(Optional.ofNullable(parent)); }

	@NonNull
	public Collection<TaskEntity> getChildren()
			{                                                           return children(); }
	public void                setChildren(@NonNull Collection<TaskEntity> children)
			{                         children(                                children); }

	public @NonNull TaskEntity name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	@Override public void afterMapping(@NonNull TaskDTO input)
	{
		log.debug("after input {}, output  {}", input, this);
	}

	@Override public @NonNull TaskDTO toTarget() { return Mapper.INSTANCE.map(this); }
}