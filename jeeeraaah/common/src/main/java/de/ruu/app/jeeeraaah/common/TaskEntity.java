package de.ruu.app.jeeeraaah.common;

import de.ruu.lib.jpa.core.mapstruct.AbstractMappedEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

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
	private String           name;
	private String           description;
	private LocalDate        startEstimated;
	private LocalDate        finishEstimated;
	private Duration         effortEstimated;
	private LocalDate        startActual;
	private LocalDate        finishActual;
	private Duration         effortActual;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Task> predecessors;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Task> successors;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Task             parent;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Task> children;

	public String getName()                                    { return          name()           ; }
	public void   setName(String name)                         { name           (name)            ; }

	public String getDescription()                             { return          description()    ; }
	public void   setDescription(String description)           { description    (description)     ; }

	public LocalDate getStartEstimated()                       { return          startEstimated() ; }
	public void setStartEstimated(LocalDate startEstimated)    { startEstimated (startEstimated)  ; }

	public LocalDate getFinishEstimated()                      { return          finishEstimated(); }
	public void setFinishEstimated(LocalDate finishEstimated)  { finishEstimated(finishEstimated) ; }

	public Duration getEffortEstimated()                       { return          effortEstimated(); }
	public void setEffortEstimated(Duration effortEstimated)   { effortEstimated(effortEstimated) ; }

	public LocalDate getStartActual()                          { return          startActual()    ; }
	public void setStartActual(LocalDate startActual)          { startActual    (startActual)     ; }

	public LocalDate getFinishActual()                         { return          finishActual()   ; }
	public void setFinishActual(LocalDate finishActual)        { finishActual   (finishActual)    ; }

	public Duration getEffortActual()                          { return          effortActual()   ; }
	public void setEffortActual(Duration effortActual)         { effortActual   (effortActual)    ; }

	public Collection<Task> getPredecessors()                  { return          predecessors()   ; }
	public void setPredecessors(Collection<Task> predecessors) { predecessors   (predecessors)    ; }

	public Collection<Task> getSuccessors()                    { return          successors()     ; }
	public void setSuccessors(Collection<Task> successors)     { successors     (successors)      ; }

	public Task getParent()                                    { return          parent()         ; }
	public void setParent(Task parent)                         { parent         (parent)          ; }

	public Collection<Task> getChildren()                      { return          children()       ; }
	public void setChildren(Collection<Task> children)         { children       (children)        ; }

	@Override public void afterMapping(@NonNull TaskDTO input)
	{
		log.debug("after input {}, output  {}", input, this);
	}

	@Override public @NonNull TaskDTO toTarget() { return Mapper.INSTANCE.map(this); }
}