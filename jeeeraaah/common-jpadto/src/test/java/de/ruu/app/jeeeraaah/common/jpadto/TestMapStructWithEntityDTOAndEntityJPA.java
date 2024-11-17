package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.app.jeeeraaah.common.jpa.TaskEntityJPA;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntityJPA;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestMapStructWithEntityDTOAndEntityJPA
{
	@Test void testStandaloneDTO()
	{
		TaskEntityDTO taskEntityDTO = createTaskDTO(createTaskGroupDTO(), "task name");
		TaskEntityJPA taskEntityJPA = taskEntityDTO.toSource();           //    mapping
		TaskEntityDTO remapped      = Mapper.INSTANCE.map(taskEntityJPA); // re-mapping

		assertIs(taskEntityDTO, taskEntityJPA);
		assertThat(taskEntityDTO, is(remapped));
	}

	@Test void testStandaloneJPA()
	{
		TaskEntityJPA taskEntityJPA = createTaskJPA(createTaskGroupJPA(), "task name");
		TaskEntityDTO taskEntityDTO = taskEntityJPA.toTarget();           //    mapping
		TaskEntityJPA remapped      = Mapper.INSTANCE.map(taskEntityDTO); // re-mapping

		assertIs(taskEntityDTO, taskEntityJPA);
		assertThat(taskEntityJPA, is(remapped));
	}

	@Test void testDTOWithSelfParent()
	{
		TaskGroupEntityDTO taskGroupDTO = new TaskGroupEntityDTO("task group name");
		TaskEntityDTO      taskDTO      = new TaskEntityDTO(taskGroupDTO, "parent and child");

		assertThrows(IllegalArgumentException.class, () -> taskDTO.parent(taskDTO));
	}

	@Test void testEntityWithSelfParent()
	{
		TaskGroupEntityJPA taskGroupEntity = new TaskGroupEntityJPA("task group name");
		TaskEntityJPA      taskEntity      = new TaskEntityJPA(taskGroupEntity, "parent and child");

		assertThrows(IllegalArgumentException.class, () -> taskEntity.parent(taskEntity));
	}

	@Test void testDTOWithNonSelfParent()
	{
		TaskGroupEntityDTO taskGroupDTO  = new TaskGroupEntityDTO("task group");
		TaskEntityDTO      taskDTOParent = new TaskEntityDTO(taskGroupDTO, "parent");
		TaskEntityDTO      taskDTOChild  = new TaskEntityDTO(taskGroupDTO, "child");

		taskDTOChild.parent(taskDTOParent);

		TaskEntityJPA taskEntityChild = taskDTOChild.toSource();

		assertThat(taskEntityChild.parent().isPresent(), is(true));
		assertThat(taskEntityChild.parent().get().children().isPresent(), is(true));
		assertThat(taskEntityChild.parent().get().children().get().contains(taskEntityChild), is(true));
	}

	@Test void testEntityWithNonSelfParent()
	{
		TaskGroupEntityJPA taskGroupEntity  = new TaskGroupEntityJPA("task group");
		TaskEntityJPA      taskEntityParent = new TaskEntityJPA(taskGroupEntity, "parent");
		TaskEntityJPA      taskEntityChild  = new TaskEntityJPA(taskGroupEntity, "child");

		taskEntityChild.parent(taskEntityParent);

		TaskEntityDTO dtoChild = taskEntityChild.toTarget();

		assertThat(dtoChild.parent().isPresent(), is(true));
		assertThat(dtoChild.parent().get().children().isPresent(), is(true));
		assertThat(dtoChild.parent().get().children().get().contains(dtoChild), is(true));
	}

	private void assertIs(TaskEntityDTO taskEntityDTO, TaskEntityJPA taskEntityJPA)
	{
		assertThat(taskEntityDTO.name           (), is(taskEntityJPA.name           ()));
		assertThat(taskEntityDTO.description    (), is(taskEntityJPA.description    ()));
		assertThat(taskEntityDTO.startEstimated (), is(taskEntityJPA.startEstimated ()));
		assertThat(taskEntityDTO.finishEstimated(), is(taskEntityJPA.finishEstimated()));
		assertThat(taskEntityDTO.startActual    (), is(taskEntityJPA.startActual    ()));
		assertThat(taskEntityDTO.finishActual   (), is(taskEntityJPA.finishActual   ()));
		assertThat(taskEntityDTO.effortEstimated(), is(taskEntityJPA.effortEstimated()));
		assertThat(taskEntityDTO.effortActual   (), is(taskEntityJPA.effortActual   ()));

		assertThat(taskEntityJPA.parent      ().isPresent(), is(taskEntityDTO.parent      ().isPresent()));
		assertThat(taskEntityJPA.children    ().isPresent(), is(taskEntityDTO.children    ().isPresent()));
		assertThat(taskEntityJPA.predecessors().isPresent(), is(taskEntityDTO.predecessors().isPresent()));
		assertThat(taskEntityJPA.successors  ().isPresent(), is(taskEntityDTO.successors  ().isPresent()));

		taskEntityJPA.children    ().ifPresent(ts -> assertThat(ts.size(), is(taskEntityDTO.children    ().get().size())));
		taskEntityJPA.predecessors().ifPresent(ts -> assertThat(ts.size(), is(taskEntityDTO.predecessors().get().size())));
		taskEntityJPA.successors  ().ifPresent(ts -> assertThat(ts.size(), is(taskEntityDTO.successors  ().get().size())));
	}

	private TaskGroupEntityDTO createTaskGroupDTO() { return new TaskGroupEntityDTO("task group name"); }
	private TaskEntityDTO      createTaskDTO(TaskGroupEntityDTO taskGroup, String taskName)
	{
		TaskEntityDTO result = new TaskEntityDTO(taskGroup, taskName);
		result
				.description    ("description")
				.startEstimated (LocalDate.now())
				.startActual    (LocalDate.now())
				.finishEstimated(LocalDate.now())
				.finishActual   (LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.effortActual   (Duration.ZERO);
		return result;
	}

	private TaskGroupEntityJPA createTaskGroupJPA() { return new TaskGroupEntityJPA("task group name"); }
	private TaskEntityJPA      createTaskJPA(TaskGroupEntityJPA taskGroup, String taskName)
	{
		TaskEntityJPA result = new TaskEntityJPA(taskGroup, taskName);
		result
				.description    ("description")
				.startEstimated (LocalDate.now())
				.startActual    (LocalDate.now())
				.finishEstimated(LocalDate.now())
				.finishActual   (LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.effortActual   (Duration.ZERO);
		return result;
	}
}