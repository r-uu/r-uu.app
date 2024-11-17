package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TestMapStructWithEntityDTOAndBean
{
	@Test void testStandaloneDTO()
	{
		TaskEntityDTO taskEntityDTO = createTaskDTO(createTaskGroupDTO(), "task name");
		TaskBean      taskBean      = Mapper.INSTANCE.map(taskEntityDTO); //    mapping
		TaskEntityDTO remapped      = Mapper.INSTANCE.map(taskBean);      // re-mapping

		assertIs  (taskEntityDTO, taskBean);
		assertThat(taskEntityDTO, is(remapped));
	}

	@Test void testStandaloneBean()
	{
		TaskBean      bean     = createTaskBean(createTaskGroupBean(), "task name");
		TaskEntityDTO dto      = bean.toSource();          //    mapping
		TaskBean      remapped = Mapper.INSTANCE.map(dto); // re-mapping

		assertIs(dto, bean);
		assertThat(bean, is(remapped));
	}

	@Test void testWithTasks()
	{
		int count = 3;
		TaskGroupBean taskGroupBean = createTaskGroupBean();

		TaskBean taskBean       = createTaskBean(taskGroupBean, "the task");
		TaskBean taskBeanParent = createTaskBean(taskGroupBean, "task's parent");

		taskBean.parent(taskBeanParent);

		createTaskBeans(taskGroupBean, count, "child "      ).forEach(taskBean::addChild);
		createTaskBeans(taskGroupBean, count, "predecessor ").forEach(taskBean::addPredecessor);
		createTaskBeans(taskGroupBean, count, "successor "  ).forEach(taskBean::addSuccessor);

		// mapping
		TaskEntityDTO dto = taskBean.toSource();

		assertIs(dto, taskBean);

		// re-mapping
		TaskBean taskBean1 = Mapper.INSTANCE.map(dto);

		assertThat(taskBean, is(taskBean1));
	}

	private void assertIs(TaskEntityDTO taskDTO, TaskBean taskBean)
	{
		assertThat(taskDTO.name           (), is(taskBean.name           ()));
		assertThat(taskDTO.description    (), is(taskBean.description    ()));
		assertThat(taskDTO.startEstimated (), is(taskBean.startEstimated ()));
		assertThat(taskDTO.finishEstimated(), is(taskBean.finishEstimated()));
		assertThat(taskDTO.startActual    (), is(taskBean.startActual    ()));
		assertThat(taskDTO.finishActual   (), is(taskBean.finishActual   ()));
		assertThat(taskDTO.effortEstimated(), is(taskBean.effortEstimated()));
		assertThat(taskDTO.effortActual   (), is(taskBean.effortActual   ()));

		assertThat(taskBean.parent      ().isPresent(), is(taskDTO.parent      ().isPresent()));
		assertThat(taskBean.children    ().isPresent(), is(taskDTO.children    ().isPresent()));
		assertThat(taskBean.predecessors().isPresent(), is(taskDTO.predecessors().isPresent()));
		assertThat(taskBean.successors  ().isPresent(), is(taskDTO.successors  ().isPresent()));

		taskBean.children    ().ifPresent(ts -> assertThat(ts.size(), is(taskDTO.children    ().get().size())));
		taskBean.predecessors().ifPresent(ts -> assertThat(ts.size(), is(taskDTO.predecessors().get().size())));
		taskBean.successors  ().ifPresent(ts -> assertThat(ts.size(), is(taskDTO.successors  ().get().size())));
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
	private List<TaskEntityDTO> createTaskDTOs(TaskGroupEntityDTO taskGroupBean, int count, String namePrefix)
	{
		List<TaskEntityDTO> result = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			result.add(createTaskDTO(taskGroupBean, namePrefix + i));
		}
		return result;
	}

	private TaskGroupBean createTaskGroupBean() { return new TaskGroupBean("task group name"); }
	private TaskBean createTaskBean(TaskGroupBean taskGroup, String name)
	{
		TaskBean result = new TaskBean(taskGroup, name);
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
	private List<TaskBean> createTaskBeans(TaskGroupBean taskGroupBean, int count, String namePrefix)
	{
		List<TaskBean> result = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			result.add(createTaskBean(taskGroupBean, namePrefix + i));
		}
		return result;
	}
}