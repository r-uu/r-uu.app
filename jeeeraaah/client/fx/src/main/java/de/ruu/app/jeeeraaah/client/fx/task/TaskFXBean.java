package de.ruu.app.jeeeraaah.client.fx.task;

import de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.BiMappedFXTarget;
import de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.MapperFX;
import de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean;
import de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean;
import de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean;
import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.app.jeeeraaah.common.jpadto.TaskEntity;
import de.ruu.lib.jpa.core.Entity;
import jakarta.annotation.Nullable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@Getter
@Accessors(fluent = true)
public class TaskFXBean
		implements
				TaskEntity<TaskGroupFXBean, TaskFXBean>,
				BiMappedFXTarget<TaskBean>
{
	@Setter(AccessLevel.NONE) @Nullable private Long  id     ;
	@Setter(AccessLevel.NONE) @Nullable private Short version;

	private final ObjectProperty<TaskGroupFXBean> taskGroupProperty       = new SimpleObjectProperty<>();
	private final StringProperty                  nameProperty            = new SimpleStringProperty  ();
	private final StringProperty                  descriptionProperty     = new SimpleStringProperty  ();
	private final ObjectProperty<LocalDate>       startEstimatedProperty  = new SimpleObjectProperty<>();
	private final ObjectProperty<LocalDate>       startActualProperty     = new SimpleObjectProperty<>();
	private final ObjectProperty<LocalDate>       finishEstimatedProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<LocalDate>       finishActualProperty    = new SimpleObjectProperty<>();
	private final ObjectProperty<Duration>        effortEstimatedProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Duration>        effortActualProperty    = new SimpleObjectProperty<>();

	@Nullable private ObjectProperty<TaskFXBean>  parentProperty;

	@Nullable private SetProperty<TaskFXBean> childrenProperty;
	@Nullable private SetProperty<TaskFXBean> predecessorsProperty;
	@Nullable private SetProperty<TaskFXBean> successorsProperty;

	/** necessary for mapstruct, ... */
	protected TaskFXBean(@NonNull Entity<Long> task)
	{
		id      = task.id     ();
		version = task.version();
	}

	public TaskFXBean(@NonNull TaskBean task)
	{
		this((Entity<Long>) task);

		// task.taskGroup and task.name are @NonNullables
		taskGroupProperty.setValue(new TaskGroupFXBean((TaskGroupBean) task.taskGroup()));
		nameProperty     .setValue(                                         task.name());

		task.description    ().ifPresent(descriptionProperty    ::setValue);
		task.startEstimated ().ifPresent(startEstimatedProperty ::setValue);
		task.startActual    ().ifPresent(startActualProperty    ::setValue);
		task.finishEstimated().ifPresent(finishEstimatedProperty::setValue);
		task.finishActual   ().ifPresent(finishActualProperty   ::setValue);
		task.effortEstimated().ifPresent(effortEstimatedProperty::setValue);
		task.effortActual   ().ifPresent(effortActualProperty   ::setValue);

		task.parent      ().ifPresent
		(
				v ->
				{
					parentProperty = new SimpleObjectProperty<>();
					parentProperty.setValue(new TaskFXBean((TaskBean) v));
				}
		);
		task.children    ().ifPresent
		(
				vs ->
				{
					childrenProperty = new SimpleSetProperty<>();
					vs.forEach(v -> childrenProperty    .add(new TaskFXBean((TaskBean) v)));
				}
		);
		task.predecessors().ifPresent
		(
				vs ->
				{
					predecessorsProperty = new SimpleSetProperty<>();
					vs.forEach(v -> predecessorsProperty.add(new TaskFXBean((TaskBean) v)));
				}
		);
		task.successors  ().ifPresent
		(
				vs ->
				{
					successorsProperty = new SimpleSetProperty<>();
					vs.forEach(v -> successorsProperty  .add(new TaskFXBean((TaskBean) v)));
				}
		);
	}

	@Override public void beforeMapping(TaskBean source) { }
	@Override public void afterMapping (TaskBean source) { }

	@Override @NonNull public TaskBean toFXSource() { return MapperFX.INSTANCE.map(this); }

	@Override public @NonNull TaskGroup<TaskFXBean> taskGroup() { return taskGroupProperty.getValue(); }

	@Override @NonNull public String name() { return nameProperty.getValue(); }

	@Override @NonNull public Optional<String   > description    () { return Optional.ofNullable(descriptionProperty    .getValue()); }
	@Override @NonNull public Optional<LocalDate> startEstimated () { return Optional.ofNullable(startEstimatedProperty .getValue()); }
	@Override @NonNull public Optional<LocalDate> startActual    () { return Optional.ofNullable(startActualProperty    .getValue()); }
	@Override @NonNull public Optional<LocalDate> finishEstimated() { return Optional.ofNullable(finishEstimatedProperty.getValue()); }
	@Override @NonNull public Optional<LocalDate> finishActual   () { return Optional.ofNullable(finishActualProperty   .getValue()); }
	@Override @NonNull public Optional<Duration > effortEstimated() { return Optional.ofNullable(effortEstimatedProperty.getValue()); }
	@Override @NonNull public Optional<Duration > effortActual   () { return Optional.ofNullable(effortActualProperty   .getValue()); }

	@Override @NonNull public TaskFXBean description(String description)
	{
		descriptionProperty.setValue(description);
		return this;
	}

	@Override @NonNull public TaskFXBean startEstimated(LocalDate startEstimated)
	{
		startEstimatedProperty.setValue(startEstimated);
		return this;
	}

	@Override @NonNull public TaskFXBean startActual(LocalDate startActual)
	{
		startActualProperty.setValue(startActual);
		return this;
	}

	@Override public @NonNull TaskFXBean finishEstimated(LocalDate finishEstimated)
	{
		finishEstimatedProperty.setValue(finishEstimated);
		return this;
	}

	@Override @NonNull public TaskFXBean finishActual(LocalDate finishActual)
	{
		finishActualProperty.setValue(finishActual);
		return this;
	}

	@Override @NonNull public TaskFXBean effortEstimated(Duration effortEstimated)
	{
		effortEstimatedProperty.setValue(effortEstimated);
		return this;
	}

	@Override @NonNull public TaskFXBean effortActual(Duration effortActual)
	{
		effortActualProperty.setValue(effortActual);
		return this;
	}

	@Override @NonNull public Optional<TaskFXBean> parent(TaskFXBean parent)
	{
		if (isNull(parentProperty)) parentProperty = new SimpleObjectProperty<>(parent);
		return Optional.of(parentProperty.getValue());
	}

	@Override @NonNull public Optional<TaskFXBean> parent()
	{
		if (isNull(parentProperty)) return Optional.empty();
		return Optional.of(parentProperty.getValue());
	}

	@Override @NonNull public Optional<Set<TaskFXBean>> children()
	{
		if (isNull(childrenProperty)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(childrenProperty.getValue()));
	}

	@Override @NonNull public Optional<Set<TaskFXBean>> predecessors()
	{
		if (isNull(successorsProperty)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(successorsProperty.getValue()));
	}

	@Override @NonNull public Optional<Set<TaskFXBean>> successors()
	{
		if (isNull(successorsProperty)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(successorsProperty.getValue()));
	}

	@Override public boolean addChild(TaskFXBean child)
	{
		if (child.taskGroup() == taskGroup()) return nonNullChildren().add(child);
		return false;
	}

	@Override public boolean addPredecessor(TaskFXBean predecessor)
	{
		if (predecessor.taskGroup() == taskGroup()) return nonNullPredecessors().add(predecessor);
		return false;
	}

	@Override public boolean addSuccessor(TaskFXBean successor)
	{
		if (successor.taskGroup() == taskGroup()) return nonNullSuccessors().add(successor);
		return false;
	}

	@Override public boolean removeChild(TaskFXBean child)
	{
		return children().map(cs -> cs.remove(child)).orElse(false);
	}

	@Override
	public boolean removePredecessor(TaskFXBean predecessor)
	{
		return predecessors().map(cs -> cs.remove(predecessor)).orElse(false);
	}

	@Override public boolean removeSuccessor(TaskFXBean successor)
	{
		return successors().map(cs -> cs.remove(successor)).orElse(false);
	}

	private @NonNull Set<TaskFXBean> nonNullChildren()
	{
		if (isNull(childrenProperty)) childrenProperty = new SimpleSetProperty<>();
		return childrenProperty;
	}

	private @NonNull Set<TaskFXBean> nonNullPredecessors()
	{
		if (isNull(predecessorsProperty)) predecessorsProperty = new SimpleSetProperty<>();
		return predecessorsProperty;
	}

	private @NonNull Set<TaskFXBean> nonNullSuccessors()
	{
		if (isNull(successorsProperty)) successorsProperty = new SimpleSetProperty<>();
		return successorsProperty;
	}
}