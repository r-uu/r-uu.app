package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.app.jeeeraaah.common.jpa.TaskEntityJPA;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntityJPA;
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
		TaskGroupEntityDTO taskGroupDTO = new TaskGroupEntityDTO("task group name");
		TaskEntityDTO      taskDTO      = new TaskEntityDTO(taskGroupDTO, "task name");

		taskDTO
				.description    ("description")
				.startEstimated (LocalDate.now())
				.startActual    (LocalDate.now())
				.finishEstimated(LocalDate.now())
				.finishActual   (LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.effortActual   (Duration.ZERO)
		;
//		log.debug("taskDTO\n{}", taskDTO);
		TaskEntityJPA entity = taskDTO.toSource();
//		log.debug("entity\n{}", entity);
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
		TaskGroupEntityJPA taskGroupJPA = new TaskGroupEntityJPA("task group name");
		TaskEntityJPA      taskJPA      = new TaskEntityJPA(taskGroupJPA, "task name");

		taskJPA
				.description("description")
				.startEstimated(LocalDate.now())
				.finishEstimated(LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.startActual(LocalDate.now())
				.finishActual(LocalDate.now())
				.effortActual(Duration.ZERO)
		;
//		log.debug("taskJPA\n{}", taskJPA);
		TaskEntityDTO dto = taskJPA.toTarget();
//		log.debug("dto\n{}", dto);
		assertThat(taskJPA.name           (), is(dto.name           ()));
		assertThat(taskJPA.description    (), is(dto.description    ()));
		assertThat(taskJPA.startEstimated (), is(dto.startEstimated ()));
		assertThat(taskJPA.finishEstimated(), is(dto.finishEstimated()));
		assertThat(taskJPA.effortEstimated(), is(dto.effortEstimated()));
		assertThat(taskJPA.startActual    (), is(dto.startActual    ()));
		assertThat(taskJPA.finishActual   (), is(dto.finishActual   ()));
		assertThat(taskJPA.effortActual   (), is(dto.effortActual   ()));
	}

	@Test void testDTOWithSelfParent()
	{
		TaskGroupEntityDTO taskGroupDTO = new TaskGroupEntityDTO("task group name");
		TaskEntityDTO taskDTO      = new TaskEntityDTO(taskGroupDTO, "parent and child");

		assertThrows(IllegalArgumentException.class, () -> taskDTO.parent(taskDTO));
	}

	@Test void testEntityWithSelfParent()
	{
		TaskGroupEntityJPA taskGroupEntity = new TaskGroupEntityJPA("task group name");
		TaskEntityJPA taskEntity      = new TaskEntityJPA(taskGroupEntity, "parent and child");

		assertThrows(IllegalArgumentException.class, () -> taskEntity.parent(taskEntity));
	}

	@Test void testDTOWithNonSelfParent()
	{
		TaskGroupEntityDTO taskGroupDTO  = new TaskGroupEntityDTO("task group");
		TaskEntityDTO taskDTOParent = new TaskEntityDTO(taskGroupDTO, "parent");
		TaskEntityDTO taskDTOChild  = new TaskEntityDTO(taskGroupDTO, "child");

		taskDTOChild.parent(taskDTOParent);

		TaskEntityJPA taskEntityChild = taskDTOChild.toSource();

		assertThat(taskEntityChild.parent().isPresent(), is(true));
		assertThat(taskEntityChild.parent().get().children().isPresent(), is(true));
		assertThat(taskEntityChild.parent().get().children().get().contains(taskEntityChild), is(true));
	}

	@Test void testEntityWithNonSelfParent()
	{
		TaskGroupEntityJPA taskGroupEntity  = new TaskGroupEntityJPA("task group");
		TaskEntityJPA taskEntityParent = new TaskEntityJPA(taskGroupEntity, "parent");
		TaskEntityJPA taskEntityChild  = new TaskEntityJPA(taskGroupEntity, "child");

		taskEntityChild.parent(taskEntityParent);

		TaskEntityDTO dtoChild = taskEntityChild.toTarget();

		assertThat(dtoChild.parent().isPresent(), is(true));
		assertThat(dtoChild.parent().get().children().isPresent(), is(true));
		assertThat(dtoChild.parent().get().children().get().contains(dtoChild), is(true));
	}
}