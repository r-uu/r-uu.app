package de.ruu.app.jeeeraaah.common;

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
		TaskDTO      taskDTO      = new TaskDTO     (taskGroupDTO, "task name");

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

		TaskEntity entity = taskDTO.toSource();

		log.debug("entity\n{}", entity);

		assertThat(taskDTO.name           (), is(entity.name           ()));
		assertThat(taskDTO.description    (), is(entity.description    ()));
		assertThat(taskDTO.startEstimated (), is(entity.startEstimated ()));
		assertThat(taskDTO.finishEstimated(), is(entity.finishEstimated()));
		assertThat(taskDTO.effortEstimated(), is(entity.effortEstimated()));
		assertThat(taskDTO.startActual    (), is(entity.startActual    ()));
		assertThat(taskDTO.finishActual   (), is(entity.finishActual   ()));
		assertThat(taskDTO.effortActual   (), is(entity.effortActual   ()));
	}

	@Test void testDTOWithNonSelfParent()
	{
		TaskGroupDTO taskGroupDTO  = new TaskGroupDTO("task group name");
		TaskDTO      taskDTOParent = new TaskDTO     (taskGroupDTO, "task name optionalParent");
		TaskDTO      taskDTOChild  = new TaskDTO     (taskGroupDTO, "task name child");

		taskDTOChild.parent(taskDTOParent);

		TaskEntity taskEntityChild = taskDTOChild.toSource();

		assertThat(taskEntityChild.optionalParent().isPresent(), is(true));
		assertThat(taskEntityChild.optionalParent().get().children().isPresent(), is(true));
		assertThat(taskEntityChild.optionalParent().get().children().get().contains(taskEntityChild), is(true));
	}

	@Test void testDTOWithSelfParent()
	{
		TaskGroupDTO taskGroupDTO = new TaskGroupDTO("task group name");
		TaskDTO      taskDTO      = new TaskDTO(taskGroupDTO, "optionalParent and child");

		assertThrows(IllegalArgumentException.class, () -> taskDTO.parent(taskDTO));
	}
}