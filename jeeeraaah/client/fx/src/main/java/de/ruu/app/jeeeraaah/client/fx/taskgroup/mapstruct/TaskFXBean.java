package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.jpadto.TaskEntity;
import jakarta.annotation.Nullable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableSet;
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
				TaskEntity<TaskGroupFXBean, TaskFXBean>
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

	private final ObjectProperty<TaskFXBean>      parentProperty          = new SimpleObjectProperty<>();

	@Nullable private ObservableSet<TaskFXBean> children;
	@Nullable private ObservableSet<TaskFXBean> predecessors;
	@Nullable private ObservableSet<TaskFXBean> successors;

	///////////////
	// constructors
	///////////////

	/** provide handmade required args constructor to properly handle relationships */
	public TaskFXBean(@NonNull TaskGroupFXBean taskGroup, @NonNull String name)
	{
		taskGroupProperty.setValue(taskGroup);
		nameProperty.setValue(name);
		taskGroup.addTask(this);
	}

//	public TaskFXBean(@NonNull TaskBean task)
//	{
//		this((Entity<Long>) task);
//
//		// task.taskGroup and task.name are @NonNullables
//		taskGroupProperty.setValue(task.taskGroup().toFXTarget());
//		nameProperty     .setValue(task.name());
//
//		task.description    ().ifPresent(descriptionProperty    ::setValue);
//		task.startEstimated ().ifPresent(startEstimatedProperty ::setValue);
//		task.startActual    ().ifPresent(startActualProperty    ::setValue);
//		task.finishEstimated().ifPresent(finishEstimatedProperty::setValue);
//		task.finishActual   ().ifPresent(finishActualProperty   ::setValue);
//		task.effortEstimated().ifPresent(effortEstimatedProperty::setValue);
//		task.effortActual   ().ifPresent(effortActualProperty   ::setValue);
//
//		task.parent      ().ifPresent
//		(
//				v ->
//				{
//					parentProperty = new SimpleObjectProperty<>();
//					parentProperty.setValue(new TaskFXBean((TaskBean) v));
//				}
//		);
//		task.children    ().ifPresent
//		(
//				vs ->
//				{
//					children = new SimpleSetProperty<>();
//					vs.forEach(v -> children.add(new TaskFXBean((TaskBean) v)));
//				}
//		);
//		task.predecessors().ifPresent
//		(
//				vs ->
//				{
//					predecessors = new SimpleSetProperty<>();
//					vs.forEach(v -> predecessors.add(new TaskFXBean((TaskBean) v)));
//				}
//		);
//		task.successors  ().ifPresent
//		(
//				vs ->
//				{
//					successors = new SimpleSetProperty<>();
//					vs.forEach(v -> successors.add(new TaskFXBean((TaskBean) v)));
//				}
//		);
//	}

	public void beforeMapping(@NonNull TaskBean source)
	{
		id      = source.id     ();
		version = source.version();

		// source.taskGroup and source.name are @NonNull-ables
		taskGroupProperty.setValue(Mapper.INSTANCE.map(source.taskGroup()).toFXSource());
		nameProperty     .setValue(source.name());

		source.description    ().ifPresent(descriptionProperty    ::setValue);
		source.startEstimated ().ifPresent(startEstimatedProperty ::setValue);
		source.startActual    ().ifPresent(startActualProperty    ::setValue);
		source.finishEstimated().ifPresent(finishEstimatedProperty::setValue);
		source.finishActual   ().ifPresent(finishActualProperty   ::setValue);
		source.effortEstimated().ifPresent(effortEstimatedProperty::setValue);
		source.effortActual   ().ifPresent(effortActualProperty   ::setValue);

		source.parent         ().ifPresent(t -> parentProperty.setValue(Mapper.INSTANCE.map(t).toFXSource()));

		source.children       ().ifPresent(ts -> ts.forEach(t -> addChild      (Mapper.INSTANCE.map(t).toFXSource())));
		source.predecessors   ().ifPresent(ts -> ts.forEach(t -> addPredecessor(Mapper.INSTANCE.map(t).toFXSource())));
		source.successors     ().ifPresent(ts -> ts.forEach(t -> addSuccessor  (Mapper.INSTANCE.map(t).toFXSource())));
	}
	public void afterMapping (TaskBean source) { }

	@NonNull public TaskBean toFXSource() { return MapperFX.INSTANCE.map(this); }

	////////////////////////////////////////////////////////////////////////
	// fluent style accessors generated by lombok if not specified otherwise
	////////////////////////////////////////////////////////////////////////

	@Override public @NonNull TaskGroupFXBean taskGroup() { return taskGroupProperty.getValue(); }

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

	@Override @NonNull public TaskFXBean parent(TaskFXBean parent)
	{
		parentProperty.setValue(parent);
		return this;
	}

	@Override @NonNull public Optional<TaskFXBean> parent()
	{
		if (isNull(parentProperty.getValue())) return Optional.empty();
		return Optional.of(parentProperty.getValue());
	}

	@Override @NonNull public Optional<Set<TaskFXBean>> children()
	{
		if (isNull(children)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(children));
	}

	@Override @NonNull public Optional<Set<TaskFXBean>> predecessors()
	{
		if (isNull(predecessors)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(predecessors));
	}

	@Override @NonNull public Optional<Set<TaskFXBean>> successors()
	{
		if (isNull(successors)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(successors));
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

	@Override public boolean removePredecessor(TaskFXBean predecessor)
	{
		return predecessors().map(cs -> cs.remove(predecessor)).orElse(false);
	}

	@Override public boolean removeSuccessor(TaskFXBean successor)
	{
		return successors().map(cs -> cs.remove(successor)).orElse(false);
	}

	private @NonNull Set<TaskFXBean> nonNullChildren()
	{
		if (isNull(children)) children = new SimpleSetProperty<>();
		return children;
	}

	private @NonNull Set<TaskFXBean> nonNullPredecessors()
	{
		if (isNull(predecessors)) predecessors = new SimpleSetProperty<>();
		return predecessors;
	}

	private @NonNull Set<TaskFXBean> nonNullSuccessors()
	{
		if (isNull(successors)) successors = new SimpleSetProperty<>();
		return successors;
	}
}