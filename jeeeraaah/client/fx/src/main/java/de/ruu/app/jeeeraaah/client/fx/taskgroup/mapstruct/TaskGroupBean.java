package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.jpa.core.AbstractDTO;
import de.ruu.lib.jpa.core.AbstractEntity;
import de.ruu.lib.jpa.core.Entity;
import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;

/** JavaBean for implementing business logic */
public class TaskGroupBean extends TaskGroupEntityDTO
{
	public TaskGroupBean(@NonNull String name) { super(name); }

	//////////////////////
	// mapstruct callbacks
	//////////////////////

	@BeforeMapping void beforeMapping(@NonNull TaskGroupEntityDTO source)
	{
		mapIdAndVersion(new EntitySimple(source));
		source.tasks().ifPresent
		(
				vs -> vs.forEach(v -> addTask(Mapper.INSTANCE.lookupOrCreate(v)))
		);
	}

	@AfterMapping  void afterMapping (@NonNull TaskGroupEntityDTO source) { }

	@NonNull public TaskGroupEntityDTO toDTOSource() { return Mapper.INSTANCE.map(this); }

	@BeforeMapping void beforeMapping(@NonNull TaskGroupFXBean source)
	{
		mapIdAndVersion(new EntitySimple(source));

//		name(source.name());
		source.tasks().ifPresent(vs -> vs.forEach(v -> addTask(MapperFX.INSTANCE.lookupOrCreate(v))));
	}

	@AfterMapping void afterMapping (@NonNull TaskGroupFXBean source) { }

	@NonNull public TaskGroupFXBean toFXSource() { return MapperFX.INSTANCE.map(this); }

	private class AbstractEntitySimple extends AbstractEntity<AbstractDTOSimple> { }
	private class AbstractDTOSimple    extends AbstractDTO<AbstractEntitySimple> { }
	private class EntitySimple         extends AbstractEntity<AbstractDTOSimple>
	{
		public EntitySimple(Entity<Long> entity) { super(entity); }
	}
}