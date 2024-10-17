package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.dto.TaskDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupDTO;
import de.ruu.app.jeeeraaah.common.jpa.TaskEntity;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class TestMapStruct
{
	@Test void testStandaloneDTO()
	{
		TaskGroupDTO taskGroupDTO = new TaskGroupDTO("task group name");
		TaskDTO      taskDTO      = new TaskDTO(taskGroupDTO, "task name");

		taskDTO
				.description("description")
				.startEstimated(LocalDate.now())
				.startActual(LocalDate.now())
				.finishEstimated(LocalDate.now())
				.finishActual(LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.effortActual(Duration.ZERO)
		;

		log.debug("taskDTO\n{}", taskDTO);

		TaskEntity entity = taskDTO.toSource();

		log.debug("entity\n{}", entity);

		assertThat(taskDTO.name           (), is(entity.name           ()));
		assertThat(taskDTO.description    (), is(entity.description    ()));
		assertThat(taskDTO.startEstimated (), is(entity.startEstimated ()));
		assertThat(taskDTO.finishEstimated(), is(entity.finishEstimated()));
		assertThat(taskDTO.startActual    (), is(entity.startActual    ()));
		assertThat(taskDTO.finishActual   (), is(entity.finishActual   ()));
		assertThat(taskDTO.effortEstimated(), is(entity.effortEstimated()));
		assertThat(taskDTO.effortActual   (), is(entity.effortActual   ()));
	}

	@Test void testStandaloneEntity()
	{
		TaskGroupEntity taskGroupEntity = new TaskGroupEntity("task group name");
		TaskEntity      taskDTO         = new TaskEntity     (taskGroupEntity, "task name");

		taskDTO
				.description("description")
				.startEstimated(LocalDate.now())
				.finishEstimated(LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.startActual(LocalDate.now())
				.finishActual(LocalDate.now())
				.effortActual(Duration.ZERO)
		;

		log.debug("taskDTO\n{}", taskDTO);

		TaskDTO dto = taskDTO.toTarget();

		log.debug("dto\n{}", dto);

		assertThat(taskDTO.name           (), is(dto.name           ()));
		assertThat(taskDTO.description    (), is(dto.description    ()));
		assertThat(taskDTO.startEstimated (), is(dto.startEstimated ()));
		assertThat(taskDTO.finishEstimated(), is(dto.finishEstimated()));
		assertThat(taskDTO.effortEstimated(), is(dto.effortEstimated()));
		assertThat(taskDTO.startActual    (), is(dto.startActual    ()));
		assertThat(taskDTO.finishActual   (), is(dto.finishActual   ()));
		assertThat(taskDTO.effortActual   (), is(dto.effortActual   ()));
	}

	@Test void testDTOWithSelfParent()
	{
		TaskGroupDTO taskGroupDTO = new TaskGroupDTO("task group name");
		TaskDTO      taskDTO      = new TaskDTO(taskGroupDTO, "parent and child");

		assertThrows(IllegalArgumentException.class, () -> taskDTO.parent(taskDTO));
	}

	@Test void testEntityWithSelfParent()
	{
		TaskGroupEntity taskGroupEntity = new TaskGroupEntity("task group name");
		TaskEntity      taskEntity      = new TaskEntity(taskGroupEntity, "parent and child");

		assertThrows(IllegalArgumentException.class, () -> taskEntity.parent(taskEntity));
	}

	@Test void testDTOWithNonSelfParent()
	{
		TaskGroupDTO taskGroupDTO  = new TaskGroupDTO("task group");
		TaskDTO      taskDTOParent = new TaskDTO(taskGroupDTO, "parent");
		TaskDTO      taskDTOChild  = new TaskDTO(taskGroupDTO, "child");

		taskDTOChild.parent(taskDTOParent);

		TaskEntity taskEntityChild = taskDTOChild.toSource();

		assertThat(taskEntityChild.parent().isPresent(), is(true));
		assertThat(taskEntityChild.parent().get().children().isPresent(), is(true));
		assertThat(taskEntityChild.parent().get().children().get().contains(taskEntityChild), is(true));
	}

	@Test void testEntityWithNonSelfParent()
	{
		TaskGroupEntity taskGroupEntity  = new TaskGroupEntity("task group");
		TaskEntity      taskEntityParent = new TaskEntity(taskGroupEntity, "parent");
		TaskEntity      taskEntityChild  = new TaskEntity(taskGroupEntity, "child");

		taskEntityChild.parent(taskEntityParent);

		TaskDTO dtoChild = taskEntityChild.toTarget();

		assertThat(dtoChild.parent().isPresent(), is(true));
		assertThat(dtoChild.parent().get().children().isPresent(), is(true));
		assertThat(dtoChild.parent().get().children().get().contains(dtoChild), is(true));
	}
}