package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TestMapStruct
{
	@Test void testStandaloneTaskDTO()
	{
		TaskDTO  taskDTO        = createTask(createTaskGroup());
		TaskDTO  taskDTOParent  = createTask(createTaskGroup());

		taskDTO.parent(taskDTOParent);

		// mapping
		TaskBean taskBean = taskDTO.toTarget();

		assertIs(taskDTO, taskBean);
	}

	@Test void testStandaloneTaskDTOTwoWayMapping()
	{
		TaskDTO  taskDTO  = createTask(createTaskGroup());

		// two way mapping
		TaskBean taskBean = taskDTO .toTarget();
		TaskDTO  taskDTO2 = taskBean.toSource();

		assertIs(taskDTO2, taskBean);
	}

	private void assertIs(TaskDTO taskDTO, TaskBean taskBean)
	{
		assertThat(taskDTO.name           (), is(taskBean.name           ()));
		assertThat(taskDTO.description    (), is(taskBean.description    ()));
		assertThat(taskDTO.startEstimated (), is(taskBean.startEstimated ()));
		assertThat(taskDTO.finishEstimated(), is(taskBean.finishEstimated()));
		assertThat(taskDTO.startActual    (), is(taskBean.startActual    ()));
		assertThat(taskDTO.finishActual   (), is(taskBean.finishActual   ()));
		assertThat(taskDTO.effortEstimated(), is(taskBean.effortEstimated()));
		assertThat(taskDTO.effortActual   (), is(taskBean.effortActual   ()));
	}

	private TaskDTO createTask(TaskGroupDTO taskGroup)
	{
//		log.debug("taskGroupDTO\n{}", taskGroup);
		TaskDTO result = new TaskDTO(taskGroup, "task name");
		result
				.description    ("description")
				.startEstimated (LocalDate.now())
				.startActual    (LocalDate.now())
				.finishEstimated(LocalDate.now())
				.finishActual   (LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.effortActual   (Duration.ZERO);
//		log.debug("taskDTO\n{}", taskDTO);
		return result;
	}

	private TaskGroupDTO createTaskGroup() { return new TaskGroupDTO("task group name"); }
}
