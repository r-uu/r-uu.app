package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

/** {@link TaskDTO} <-> {@link TaskBean} */
@Slf4j
@Mapper
public abstract class Map_Task_DTO_Bean
{
	public final static Map_Task_DTO_Bean INSTANCE = Mappers.getMapper(Map_Task_DTO_Bean.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskBean map(@NonNull TaskDTO  source);
	public abstract @NonNull TaskDTO  map(@NonNull TaskBean source);

	Optional<TaskBean> getFromContext(TaskDTO  dto ) { return Optional.ofNullable(CONTEXT.get(dto , TaskBean.class)); }
	Optional<TaskDTO>  getFromContext(TaskBean bean) { return Optional.ofNullable(CONTEXT.get(bean, TaskDTO .class)); }

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskDTO source, @MappingTarget TaskBean target)
	{
		target.beforeMappingDTO(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskDTO source, @MappingTarget TaskBean target)
	{
		target.afterMappingDTO(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskBean source, @MappingTarget TaskDTO target)
	{
		target.beforeMapping(source);
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskBean source, @MappingTarget TaskDTO target)
	{
		target.afterMapping(source);
	}
}