package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.v2;

import de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.BiMappedFXSource;
import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.jpa.core.AbstractDTO;
import de.ruu.lib.jpa.core.AbstractEntity;
import de.ruu.lib.jpa.core.Entity;
import de.ruu.lib.util.Strings;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

/** JavaBean for implementing business logic */
public class TaskGroupBean extends TaskGroupEntityDTO
{
	public TaskGroupBean(@NonNull String name) { super(name); }

	//////////////////////
	// mapstruct callbacks
	//////////////////////

	@BeforeMapping  void               beforeMapping(@NonNull TaskGroupEntityDTO source)
	{
		mapIdAndVersion(new EntitySimple(source));
		source.tasks().ifPresent(vs -> vs.forEach(v -> addTask(MapperFX.INSTANCE.lookupOrCreate(v))));
	}
	@AfterMapping   void               afterMapping (@NonNull TaskGroupEntityDTO source) { }
	@NonNull public TaskGroupEntityDTO toDTOSource() { return Mapper.INSTANCE.map(this); }

	@BeforeMapping public void beforeMapping(@NonNull TaskGroupFXBean source)
	{
		mapIdAndVersion(new EntitySimple(source));

//		name(source.name());
		source.tasks().ifPresent(vs -> vs.forEach(v -> addTask(MapperFX.INSTANCE.lookupOrCreate(v))));
	}

	@AfterMapping public void afterMapping (@NonNull TaskGroupFXBean source) { }

	@NonNull public TaskGroupFXBean toFXSource() { return MapperFX.INSTANCE.map(this); }

	private class AbstractEntitySimple extends AbstractEntity<AbstractDTOSimple> { }
	private class AbstractDTOSimple    extends AbstractDTO<AbstractEntitySimple> { }
	private class EntitySimple         extends AbstractEntity<AbstractDTOSimple>
	{
		public EntitySimple(Entity<Long> entity) { super(entity); }
	}
}